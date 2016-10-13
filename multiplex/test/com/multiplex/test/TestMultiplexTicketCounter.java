package com.multiplex.test;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.multiplex.ticketcounter.MultiplexTicketCounter;
import com.multiplex.ticketcounter.ticket.ITicket;
import com.multiplex.ticketcounter.ticket.Ticket;
public class TestMultiplexTicketCounter {
	MultiplexTicketCounter multiplexTicketCounter;
	
	@Before
	public void setup(){
		multiplexTicketCounter=new MultiplexTicketCounter("testId1", "Test Counter",null);
	}
	
	@Test
	public void bookAnyNSeatsInAudi(){
		ITicket ticket=multiplexTicketCounter.book("MVH0001:SHSL003:4-1-1");
		assertTrue("Ticket not valid",((Ticket)ticket).getMovieName().equals("DDLJ")&&((Ticket)ticket).getMovieTime().equals("13:10 Mon 13th Nov 2106")&&((Ticket)ticket).getSeats().size()==4);
		
	}
	
	@Test
	public void bookAnyNSeatsInAudi2(){
		ITicket ticket=multiplexTicketCounter.book("MVH0001:SHSL003:17-1-1");
		assertTrue("Ticket not valid",((Ticket)ticket).getMovieName().equals("DDLJ")&&((Ticket)ticket).getMovieTime().equals("13:10 Mon 13th Nov 2106")&&((Ticket)ticket).getSeats().size()==17);
		
	}

}
