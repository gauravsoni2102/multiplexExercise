package com.multiplex.auditorium;

import java.util.concurrent.atomic.AtomicInteger;

public class Auditorium {
	
	Seat[][]seats;
	public Auditorium(int a,int b){
		seats=new Seat[a][b];
		for(int i=0;i<a;i++){
			for(int j=0;j<b;j++)
				seats[i][j]=new Seat();
		}
		
	}
	
	public Seat[][] getSeatStatus(){
		return seats;
	}
}
