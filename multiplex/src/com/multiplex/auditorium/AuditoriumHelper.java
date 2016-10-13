package com.multiplex.auditorium;

import java.util.HashMap;
import java.util.Map;

public class AuditoriumHelper {
	static Map<String,Auditorium>auditoriams=new HashMap<String,Auditorium>();
	static{
		auditoriams.put("Audi1",new Auditorium(10, 20));
	}

	static public Auditorium getAuditorium(String auditoriumName){
		return auditoriams.get(auditoriumName);
	}
}
