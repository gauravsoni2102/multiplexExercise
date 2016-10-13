package com.multiplex.ticketcounter.ticket;

public interface ITicket {
void add(ITicketDetail detail);

String getCSVFormat();
}
