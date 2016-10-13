package com.multiplex.ticketcounter;

import java.util.Random;

import com.multiplex.auditorium.AuditoriumHelper;
import com.multiplex.auditorium.Seat;
import com.multiplex.ticketcounter.ticket.ITicketDetail;
import com.multiplex.ticketcounter.ticket.SeatDetail;
import com.multiplex.ticketcounter.ticket.Ticket;
/**
 * @author gaurav.soni
 *MultiplexTicketCounter consists of the logic that is used by a booking counter in the book method.
 *Whenever a user notifies the counter, the counter thread's book method is invoked.
 */
public class MultiplexTicketCounter extends TicketCounterBase{

	public MultiplexTicketCounter(String counterId,String counterName) {
		super(counterId,counterName);
	}
	@Override
	public synchronized void book(final String movieArgs) {
		Ticket ticket=null;

		String[] movieDetails=movieArgs.split(":");
		String movieCode=movieDetails[0];
		String movieTime=movieDetails[1];
		Seat[][] seatstatus=AuditoriumHelper.getAuditorium(movieDetails[0],movieDetails[1]).getSeatStatus();
		String[]preferenceParameters=movieDetails[2].split("-");
		int noOfSeats=Integer.parseInt(preferenceParameters[0]);
		int row=seatstatus.length-1;
		if(!preferenceParameters[1].equals(""))
			row=Integer.parseInt(preferenceParameters[1]);

		boolean together=preferenceParameters[2].equals("1");
		try{	
			if(!together){
				if(preferenceParameters[1].equals("")){
					ticket=bookAnyNSeatsFromFront(noOfSeats,seatstatus);
				}
				else{
					ticket=bookNSeatsInRow(noOfSeats,row,seatstatus);
				}
			}

			else{
				if(!preferenceParameters[1].equals("")){
					ticket=bookAnyNSeatsInRowTogether(noOfSeats,row,seatstatus);
				}
				else{
					ticket=bookAnyNSeatsTogether(noOfSeats,seatstatus);
				}
			}
			ticket.setMovieName(movieCode);
			ticket.setMovieTime(movieTime);
			td(ticket);
			System.out.println();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		ss(seatstatus);
	}
	private Ticket bookAnyNSeatsFromFront(final int noOfSeats,final Seat[][] seatstatus) throws NotEnoughSeatsException{
		int nos=noOfSeats;
		Random r=new Random();
		Ticket ticket=new Ticket();
		int counterId=r.nextInt();
		for(int row=seatstatus.length-1;row>=0&&nos>0;row--){
			for(int seat=seatstatus[row].length-1;seat>=0&&nos>0;seat--){
				synchronized(seatstatus[row][seat]){
					if(seatstatus[row][seat].getSetstatus()==0){
						seatstatus[row][seat].setSetstatus(counterId);
						ticket.add(new SeatDetail(row, seat));
						nos--;
					}
				}
			}
		}
		if(nos==0){
			for(int row=seatstatus.length-1;row>=0;row--){
				for(int seat=seatstatus[row].length-1;seat>=0;seat--){
					if(seatstatus[row][seat].getSetstatus()==counterId){
						seatstatus[row][seat].setSetstatus(-1);
					}
				}
			}
		}

		if(nos>0){
			for(int row=seatstatus.length-1;row>=0;row--){
				for(int seat=seatstatus[row].length-1;seat>=0;seat--){
					if(seatstatus[row][seat].getSetstatus()==counterId){
						seatstatus[row][seat].setSetstatus(0);
					}
				}
			}
			throw new NotEnoughSeatsException("We cant book "+noOfSeats+" seats in this auditorium as these many seats are not available."); 
		}
		return ticket;
	}

	Ticket bookNSeatsInRow(int noOfSeats,final int row,final Seat[][] seatstatus) throws NotEnoughSeatsException{
		Random r=new Random();
		Ticket ticket=new Ticket();
		int counterId=r.nextInt();

		for(int seat=seatstatus[row].length-1;seat>=0&&noOfSeats>0;seat--){
			synchronized(seatstatus[row][seat]){
				if(seatstatus[row][seat].getSetstatus()==0){
					seatstatus[row][seat].setSetstatus(counterId);
					//if(seatstatus[row][seat].get()==counterId){
					ticket.add(new SeatDetail(row, seat));
					noOfSeats--;//seatstatus[row][seat].set(-1);
				}
			}
		}		

		if(noOfSeats==0){
			for(int seat=seatstatus[row].length-1;seat>=0;seat--){
				if(seatstatus[row][seat].getSetstatus()==counterId){
					seatstatus[row][seat].setSetstatus(-1);
				}
			}
		}

		if(noOfSeats>0){
			for(int seat=seatstatus[row].length-1;seat>=0;seat--){
				if(seatstatus[row][seat].getSetstatus()==counterId){
					seatstatus[row][seat].setSetstatus(0);
				}
			}
			throw new NotEnoughSeatsException("We cant book "+noOfSeats+" seats in row "+row+" of this auditorium as these many seats are not available."); 	
		} 
		return ticket;
	}

	private Ticket bookAnyNSeatsInRowTogether(final int noOfSeats,final int row,final Seat[][] seatstatus) throws NotEnoughSeatsException{
		Random r=new Random();
		Ticket ticket=new Ticket();
		int tnos=noOfSeats;
		int counterId=r.nextInt();

		for(int seat=0;seat<=seatstatus[row].length-noOfSeats&&tnos>0;seat++){
			//tnos=noOfSeats;
			for(int i=0;i<noOfSeats;i++){
				synchronized(seatstatus[row][seat+i]){
					if(seatstatus[row][seat+i].getSetstatus()==0){
						seatstatus[row][seat+i].setSetstatus(counterId);
						System.out.println(seat+i+"--");
						tnos--;
					}
					else{
						tnos=noOfSeats;
						i=noOfSeats+1;
					}
				}
			}
		}
		if(tnos==0){
			for(int seat=seatstatus[row].length-1;seat>=0;seat--){
				if(seatstatus[row][seat].getSetstatus()==counterId){
					seatstatus[row][seat].setSetstatus(-1);
					ticket.add(new SeatDetail(row, seat));
				}
			}
		}
		else if(tnos>0){
			for(int seat=seatstatus[row].length-1;seat>=0;seat--){
				if(seatstatus[row][seat].getSetstatus()==counterId){
					seatstatus[row][seat].setSetstatus(0);
				}
			}
			throw new NotEnoughSeatsException("We cant book "+noOfSeats+" seats together in row "+row+" of this auditorium as these many seats are not available."); 
		}
		return ticket;
	}

	private Ticket bookAnyNSeatsTogether(final int noOfSeats,final Seat[][] seatstatus) throws NotEnoughSeatsException{
		for(int row=seatstatus.length-1;row>=0;row--){
			try{
				return bookAnyNSeatsInRowTogether(noOfSeats, row, seatstatus);
			}
			catch(Exception e){
				continue;
			}
		}
		throw new NotEnoughSeatsException("We cant book "+noOfSeats+" seats together in this auditorium as these many seats are not available."); 
	}

	void td(Ticket t){
		for(ITicketDetail x:t.seats){
			System.out.println(((SeatDetail)x).row+"  "+((SeatDetail)x).seatno);
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
