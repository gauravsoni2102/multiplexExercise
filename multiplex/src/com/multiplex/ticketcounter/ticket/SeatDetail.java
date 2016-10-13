package com.multiplex.ticketcounter.ticket;

public class SeatDetail implements ITicketDetail{
	public int row;
	public int seatno;
	public SeatDetail(int row,int seatno){
		this.row=row;
		this.seatno=seatno;
	}

}
