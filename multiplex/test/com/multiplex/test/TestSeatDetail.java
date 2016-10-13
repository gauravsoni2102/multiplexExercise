package com.multiplex.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.multiplex.auditorium.Seat;
import com.multiplex.ticketcounter.ticket.SeatDetail;

public class TestSeatDetail {
	
	
	@Before
	public void setup(){
		
	}

	@Test
	public void testSeatNumberSmall() {
		SeatDetail seatDetail=new SeatDetail(5, 7);
		assertTrue("Seat number incorrect", seatDetail.getSeatNumber().equals("F8"));
	}

	@Test
	public void testSeatNumberBig() {
		SeatDetail seatDetail=new SeatDetail(29, 9);
		assertTrue("Seat number incorrect", seatDetail.getSeatNumber().equals("AD10"));
	}
	
	
}
