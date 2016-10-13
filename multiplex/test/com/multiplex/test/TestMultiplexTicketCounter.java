package com.multiplex.test;
import org.junit.Before;

import com.multiplex.ticketcounter.MultiplexTicketCounter;
public class TestMultiplexTicketCounter {
	MultiplexTicketCounter multiplexTicketCounter;
	@Before
	public void setup(){
		multiplexTicketCounter=new MultiplexTicketCounter("testId1", "Test Counter",null);
	}
	
	
	
	
	

}
