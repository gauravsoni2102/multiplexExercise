package com.multiplex.ticketcounter.ticket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TicketPrinter implements IPrinter {
	private String filename;
	public TicketPrinter(String counterId){
		filename=counterId+".csv";
		try {
			new File(filename).createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
synchronized public void print(ITicket ticket){	
	FileWriter fw;
	try {
		fw = new FileWriter(filename,true);
		  fw.write(ticket.getCSVFormat()+",\n");
		    fw.close();
	}
	
	catch (IOException e) {
		e.printStackTrace();
	} 
  
}
}
