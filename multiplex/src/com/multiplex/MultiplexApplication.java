package com.multiplex;

import com.multiplex.ticketcounter.TicketCounter;

public class MultiplexApplication {
public TicketCounter counterA;
private TicketCounter counterB;
private TicketCounter counterC;
	public static class Builder{
		public static MultiplexApplication build(){
			MultiplexApplication multiplexApplication=new MultiplexApplication();
			multiplexApplication.counterA=new TicketCounter();
			multiplexApplication.counterB=new TicketCounter();
			multiplexApplication.counterC=new TicketCounter();			
			return multiplexApplication;
		}
	}
	
}
