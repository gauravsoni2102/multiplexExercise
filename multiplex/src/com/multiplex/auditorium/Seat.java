package com.multiplex.auditorium;

public class Seat {
private int setstatus;

synchronized public int getSetstatus() {
	return setstatus;
}

synchronized public void setSetstatus(int setstatus) {
	this.setstatus = setstatus;
}

}
