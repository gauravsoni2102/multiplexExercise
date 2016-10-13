package com.multiplex.ticketcounter.ticket;

import java.util.ArrayList;
import java.util.List;

public class Ticket implements ITicket {

	public String movieName;
	public String movieTime;
	public List<ITicketDetail> seats=new ArrayList<ITicketDetail>();
	public void add(ITicketDetail ticketDetail){
		seats.add(ticketDetail);
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public List<ITicketDetail> getSeats() {
		return seats;
	}
	public void setSeats(List<ITicketDetail> seats) {
		this.seats = seats;
	}
	public String getMovieTime() {
		return movieTime;
	}
	public void setMovieTime(String movieTime) {
		this.movieTime = movieTime;
	}


}
