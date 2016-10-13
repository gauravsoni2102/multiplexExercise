package com.multiplex.ticketcounter.ticket;

public class SeatDetail implements ITicketDetail{
	private String row;
	private int seatno;
	public SeatDetail(int row,int seatno){
		if(row<26)
		this.row=""+(char)(row+65);
		else
			this.row=(char)(((row/26)-1)+65)+""+(char)((row%26)+65);
		this.seatno=seatno+1;
	}
	
	public String getSeatNumber(){
	return row+""+seatno;	
	}

}
