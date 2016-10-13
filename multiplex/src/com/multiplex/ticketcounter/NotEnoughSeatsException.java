package com.multiplex.ticketcounter;

public class NotEnoughSeatsException extends Exception {

	public NotEnoughSeatsException(String message){
	super(message);
}
}
