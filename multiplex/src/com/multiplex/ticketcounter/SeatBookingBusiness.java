package com.multiplex.ticketcounter;

import java.util.Random;

import com.multiplex.auditorium.Seat;
import com.multiplex.ticketcounter.ticket.SeatDetail;
import com.multiplex.ticketcounter.ticket.Ticket;

public class SeatBookingBusiness {
	 Ticket bookAnyNSeatsFromFront(final int noOfSeats,final Seat[][] seatstatus) throws NotEnoughSeatsException{
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

	 Ticket bookAnyNSeatsInRowTogether(final int noOfSeats,final int row,final Seat[][] seatstatus) throws NotEnoughSeatsException{
		Random r=new Random();
		Ticket ticket=new Ticket();
		int tnos=noOfSeats;
		int counterId=r.nextInt();

		for(int seat=0;seat<=seatstatus[row].length-noOfSeats&&tnos>0;seat++){
			for(int i=0;i<noOfSeats;i++){
				synchronized(seatstatus[row][seat+i]){
					if(seatstatus[row][seat+i].getSetstatus()==0){
						seatstatus[row][seat+i].setSetstatus(counterId);
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

	 Ticket bookAnyNSeatsTogether(final int noOfSeats,final Seat[][] seatstatus) throws NotEnoughSeatsException{
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
}
