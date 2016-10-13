package com.multiplex.ticketcounter;

import java.util.Random;

import com.multiplex.auditorium.AuditoriumHelper;
import com.multiplex.auditorium.Seat;
import com.multiplex.ticketcounter.ticket.ITicketDetail;
import com.multiplex.ticketcounter.ticket.SeatDetail;
import com.multiplex.ticketcounter.ticket.Ticket;

public class TicketCounter extends TicketCounterBase{

	@Override
	public synchronized void book(String preferenceArgs) {
		Ticket ticket=null;
		Seat[][] seatstatus=AuditoriumHelper.getAuditorium("Audi1").getSeatStatus();
		String[]preferenceParameters=preferenceArgs.split("-");
		int noOfSeats=Integer.parseInt(preferenceParameters[0]);
		int row=seatstatus.length-1;
		if(!preferenceParameters[1].equals(""))
			row=Integer.parseInt(preferenceParameters[1]);

		boolean together=preferenceParameters[2].equals("1");
		try{	
			if(!together){
				if(preferenceParameters[1].equals("")){
					ticket=bookAnyNSeatsFromBack(noOfSeats,seatstatus);
				}
				else{
					ticket=bookNSeatsInRow(noOfSeats,row,seatstatus);
				}
			}

			else{
				if(!preferenceParameters[1].equals("")){
					ticket=bookAnyNSeatsInRowTogether(noOfSeats,row,seatstatus);
				}

			}
			td(ticket);
			System.out.println();
			//ss(seatstatus);
		}
		catch(Exception e){
			e.printStackTrace();//System.out.println(e);
		}
ss(seatstatus);
	}

	private Ticket bookAnyNSeatsFromBack(int noOfSeats,Seat[][] seatstatus) throws NotEnoughSeatsException{
		Random r=new Random();
		Ticket ticket=new Ticket();
		int counterId=r.nextInt();
		for(int row=seatstatus.length-1;row>=0&&noOfSeats>0;row--){
			for(int seat=seatstatus[row].length-1;seat>=0&&noOfSeats>0;seat--){
				synchronized(seatstatus[row][seat]){
					if(seatstatus[row][seat].getSetstatus()==0){
						seatstatus[row][seat].setSetstatus(counterId);

						//	if(seatstatus[row][seat].get()==counterId){
						//seatstatus[row][seat].set(-1);
						ticket.add(new SeatDetail(row, seat));
						noOfSeats--;
						//}
					}

				}
			}
		}
		if(noOfSeats==0){
			for(int row=seatstatus.length-1;row>=0;row--){
				for(int seat=seatstatus[row].length-1;seat>=0;seat--){
					if(seatstatus[row][seat].getSetstatus()==counterId){
						seatstatus[row][seat].setSetstatus(-1);
					}
				}
			}
		}

		if(noOfSeats>0){
			for(int row=seatstatus.length-1;row>=0;row--){
				for(int seat=seatstatus[row].length-1;seat>=0;seat--){
					if(seatstatus[row][seat].getSetstatus()==counterId){
						seatstatus[row][seat].setSetstatus(0);
					}


				}
			}
			throw new NotEnoughSeatsException(); 
		}


		return ticket;
	}

	Ticket bookNSeatsInRow(int noOfSeats,int row,Seat[][] seatstatus) throws NotEnoughSeatsException{
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

			throw new NotEnoughSeatsException();
		} 
		return ticket;
	}

	private Ticket bookAnyNSeatsInRowTogether(int noOfSeats,int row,Seat[][] seatstatus) throws NotEnoughSeatsException{
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
						//seat+=i;
								
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
			throw new NotEnoughSeatsException(); 
			}

		return ticket;
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