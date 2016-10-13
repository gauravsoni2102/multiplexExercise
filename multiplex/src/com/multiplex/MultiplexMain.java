package com.multiplex;

public class MultiplexMain {

	public static void main(String[] args) {
	MultiplexApplication m=MultiplexApplication.Builder.build();
	
	m.counterA.customerArrivalEvent("14-1-1");
	m.counterA.customerArrivalEvent("2-1-1");
	m.counterA.customerArrivalEvent("4-1-1");

	m.counterA.customerArrivalEvent("3--0");


	
	
	
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
