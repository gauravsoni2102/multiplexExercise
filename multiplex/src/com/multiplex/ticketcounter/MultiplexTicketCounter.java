package com.multiplex.ticketcounter;

import com.multiplex.auditorium.AuditoriumHelper;
import com.multiplex.auditorium.Seat;
import com.multiplex.db.DAO;
import com.multiplex.ticketcounter.ticket.IPrinter;
import com.multiplex.ticketcounter.ticket.ITicket;
import com.multiplex.ticketcounter.ticket.ITicketDetail;
import com.multiplex.ticketcounter.ticket.SeatDetail;
import com.multiplex.ticketcounter.ticket.Ticket;
/**
 * @author gaurav.soni
 *MultiplexTicketCounter consists of the logic that is used by a booking counter in the book method.
 *Whenever a user notifies the counter, the counter thread's book method is invoked.
 */
public class MultiplexTicketCounter extends TicketCounterBase{
	SeatBookingBusiness business;
	
	public MultiplexTicketCounter(String counterId,String counterName,IPrinter printer) {
		super(counterId,counterName,printer);
		business=new SeatBookingBusiness();
	}
	@Override
	public synchronized ITicket book(final String movieArgs) {
		Ticket ticket=null;
		String[] movieDetails=movieArgs.split(":");
		String movieCode=movieDetails[0];
		String movieTime=movieDetails[1];

		Seat[][] seatstatus=AuditoriumHelper.getAuditorium(movieCode,movieTime).getSeatStatus();
		String[]preferenceParameters=movieDetails[2].split("-");
		int noOfSeats=Integer.parseInt(preferenceParameters[0]);
		int row=seatstatus.length-1;
		if(!preferenceParameters[1].equals(""))
			row=Integer.parseInt(preferenceParameters[1]);

		boolean together=preferenceParameters[2].equals("1");
		try{	
			if(!together){
				if(preferenceParameters[1].equals("")){
					ticket=business.bookAnyNSeatsFromFront(noOfSeats,seatstatus);
				}
				else{
					ticket=business.bookNSeatsInRow(noOfSeats,row,seatstatus);
				}
			}
			else{
				if(!preferenceParameters[1].equals("")){
					ticket=business.bookAnyNSeatsInRowTogether(noOfSeats,row,seatstatus);
				}
				else{
					ticket=business.bookAnyNSeatsTogether(noOfSeats,seatstatus);
				}
			}
			ticket.setMovieName(DAO.getMovie(movieCode));
			ticket.setMovieTime(DAO.getTimings(movieTime));
			ticket.setcounterName(counterName);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		ss(seatstatus);
		return ticket;
	}


	void td(Ticket t){
		for(ITicketDetail x:t.getSeats()){
			System.out.println(((SeatDetail)x).getSeatNumber());
		}
	}
	void ss(Seat[][]s){
		for(int i=0;i<s.length;i++){
			for(int j=0;j<s[i].length;j++){
				System.out.print(s[i][j].getSetstatus()+" ");
			}
			System.out.println();
		}
	}
}
