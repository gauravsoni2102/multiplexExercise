package com.multiplex;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.multiplex.ticketcounter.MultiplexTicketCounter;
import com.multiplex.ticketcounter.ticket.TicketPrinter;
/**
 * @author gaurav.soni
 *This Is the main application which takes in a config file as parameter.
 *From the config file, it creates instances of the TicketCounter controller.
 *Each Controller has its own thread which runs only when a new task arrives.
 */
public class MultiplexApplication {
	public Map<String,MultiplexTicketCounter>counters;

	public static class Builder{
		public static MultiplexApplication build(final String configFile){
			Properties properties;
			try {
				properties=new Builder().getConfig(configFile);
			} catch (IOException e) {
				System.out.println("couldnt find/read "+configFile);
				return null;
			}
			
			MultiplexApplication multiplexApplication=new MultiplexApplication();
			multiplexApplication.counters=new HashMap<String,MultiplexTicketCounter>();
			int counternumber=1;
			boolean counterExist;
			do{
				String counterId=(String)properties.get("COUNTER_"+counternumber+"_ID");
				String counterName=counterId;
				counterExist=counterId!=null&&!counterId.equals("");
				if(counterExist)counterName=(String)properties.get("COUNTER_"+counternumber+"_NAME");	
				else break;
				multiplexApplication.counters.put(counterId,new MultiplexTicketCounter(counterId,counterName,new TicketPrinter(counterId)));
				counternumber++;
			}while(counterExist);
			System.out.println(multiplexApplication.counters.size());
			return multiplexApplication;
		}

		private Properties getConfig(final String configFile) throws IOException{
			Properties properties=new Properties();
			InputStream inputStream=getClass().getClassLoader().getResourceAsStream(configFile);
			if(inputStream==null) throw new IOException();
			properties.load(inputStream);
			return properties;
		}
	}

}
