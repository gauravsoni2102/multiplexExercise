package com.multiplex;

public class MultiplexMain {

	public static void main(String[] args) {
	MultiplexApplication m=MultiplexApplication.Builder.build("config.properties");
	
	m.counters.get("MX0001").customerArrivalEvent("MOV091:SHSL003:14-1-1");
	m.counters.get("MX0001").customerArrivalEvent("MOV091:SHSL003:2-1-1");
	m.counters.get("MX0001").customerArrivalEvent("MOV091:SHSL003:4-1-1");
//
	m.counters.get("MX0001").customerArrivalEvent("MOV091:SHSL003:3--0");
	m.counters.get("MX0001").customerArrivalEvent("MOV091:SHSL003:3--0");
//
	m.counters.get("MX0001").customerArrivalEvent("MOV091:SHSL003:3--0");
//
	m.counters.get("MX0002").customerArrivalEvent("MOV091:SHSL003:3--0");
	m.counters.get("MX0002").customerArrivalEvent("MOV091:SHSL003:3--0");
	m.counters.get("MX0002").customerArrivalEvent("MOV091:SHSL003:3--0");
	m.counters.get("MX0002").customerArrivalEvent("MOV091:SHSL003:3--0");	
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	
	m.counters.get("MX0001").customerArrivalEvent("MOV091:SHSL003:10--1");//	m.counterA.customerArrivalEvent("10--1");
	m.counters.get("MX0001").customerArrivalEvent("MOV091:SHSL003:10--1");//	m.counterA.customerArrivalEvent("10--1");
	m.counters.get("MX0001").customerArrivalEvent("MOV091:SHSL003:10--1");	//m.counterA.customerArrivalEvent("10--1");
//	m.counterA.customerArrivalEvent("10--1");	m.counterA.customerArrivalEvent("10--1");
//	m.counterA.customerArrivalEvent("10--1");	m.counterA.customerArrivalEvent("10--1");
//	m.counterA.customerArrivalEvent("10--1");	m.counterA.customerArrivalEvent("10--1");
//	m.counterA.customerArrivalEvent("10--1");	m.counterA.customerArrivalEvent("10--1");
	//m.counterA.customerArrivalEvent("10--1");
	
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
