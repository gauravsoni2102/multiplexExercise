package com.multiplex.ticketcounter.ticket;

import java.util.ArrayList;
import java.util.List;

public class Ticket implements ITicket {
	private String counterName;
	public String movieName;
	public String movieTime;
	public List<ITicketDetail> seats=new ArrayList<ITicketDetail>();

	public String getcounterName() {
		return counterName;
	}
	public void setcounterName(String counterName) {
		this.counterName = counterName;
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
	
	public void add(ITicketDetail ticketDetail){
		seats.add(ticketDetail);
	}
	@Override
	public String getCSVFormat() {
		return movieName+","+movieTime+","+seats.size();
	}
	
}
