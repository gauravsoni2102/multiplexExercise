package com.multiplex.ticketcounter;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class TicketCounterBase implements Runnable{
	AtomicBoolean hasCustomer;
	String preferenceArgs;
	public TicketCounterBase() {
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
	abstract public void book(String preferenceArgs);

	synchronized private void customerArrivalHandleEvent(String preferenceArgs){
		this.preferenceArgs=preferenceArgs;
		hasCustomer.set(true);
		notify();
	}


	public void customerArrivalEvent(String preferenceArgs){
		while(hasCustomer.get()){}//So that if a customer is present, other doesn't enter
		customerArrivalHandleEvent(preferenceArgs);	
	}


}
