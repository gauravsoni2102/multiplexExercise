package com.multiplex.ticketcounter.ticket;

import java.util.ArrayList;
import java.util.List;

public class Ticket implements ITicket {
	public List<ITicketDetail> seats=new ArrayList<ITicketDetail>();
public void add(ITicketDetail ticketDetail){
	seats.add(ticketDetail);
}
}
