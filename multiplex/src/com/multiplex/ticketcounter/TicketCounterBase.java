package com.multiplex.ticketcounter;

import java.util.concurrent.atomic.AtomicBoolean;
/**
 * @author gaurav.soni
 *The TicketCounterBase is a general base class for a ticket counter.
 *It consists of a thread that waits for a booking event. This thread 
 *then performs the specific counter's logic i.e. in book method. Similar to 
 *real world, only one ticket can be booked at the counter at a time.  
 */
public abstract class TicketCounterBase implements Runnable{
	private String counterId;
	private String counterName;
	AtomicBoolean hasCustomer;
	String preferenceArgs;
	public TicketCounterBase(String counterId,String counterName) {
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

			book(preferenceArgs);
			preferenceArgs="";
			hasCustomer.set(false);
		}

	}
	abstract public void book(final String preferenceArgs);

	synchronized private void customerArrivalHandleEvent(final String preferenceArgs){
		this.preferenceArgs=preferenceArgs;
		hasCustomer.set(true);
		notify();
	}


	public void customerArrivalEvent(final String preferenceArgs){
		while(hasCustomer.get()){}//So that if a customer is present, other doesn't enter
		customerArrivalHandleEvent(preferenceArgs);	
	}


}
