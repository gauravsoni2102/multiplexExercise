package com.multiplex.ticketcounter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.multiplex.ticketcounter.ticket.IPrinter;
import com.multiplex.ticketcounter.ticket.ITicket;
/**
 * @author gaurav.soni
 *The TicketCounterBase is a general base class for a ticket counter.
 *It consists of a thread that waits for a booking event. This thread 
 *then performs the specific counter's logic i.e. in book method. Similar to 
 *real world, only one ticket can be booked at the counter at a time.  
 */
public abstract class TicketCounterBase implements Runnable{
	protected String counterId;
	protected String counterName;
	private IPrinter printer;
	private ConcurrentLinkedQueue<String>movieDetails;
	AtomicBoolean hasCustomer;
	public TicketCounterBase(final String counterId,final String counterName,final IPrinter printer) {
		movieDetails=new ConcurrentLinkedQueue<String>();
		this.printer=printer;
		this.counterId=counterId;
		this.counterName=counterName;
		hasCustomer=new AtomicBoolean(false);
		new Thread(this).start();
	}
	@Override
	public void run(){
		bookAndWait();
	}
	private synchronized void bookAndWait(){
		while(true){	
			while(!hasCustomer.get()){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			while(!movieDetails.isEmpty()){
				String movieDetail=movieDetails.poll();
				ITicket ticket=book(movieDetail);
				printer.print(ticket);
			}
			hasCustomer.set(false);
		}

	}
	abstract public ITicket book(final String movieArgs);

	synchronized private void customerArrivalHandleEvent(final String movieArgs){
		hasCustomer.set(true);
		notify();
	}


	public void customerArrivalEvent(final String movieArgs){
		movieDetails.add(movieArgs);
		customerArrivalHandleEvent(movieArgs);	

	}


}
