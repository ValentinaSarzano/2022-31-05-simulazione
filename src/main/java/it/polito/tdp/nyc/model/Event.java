package it.polito.tdp.nyc.model;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		INIZIO_HS,
		FINE_HS,
		NEXT_HS
	}

	private int time;
	private EventType type;
	private int tecnico;
	
	public Event(int time, EventType type, int tecnico) {
		super();
		this.time = time;
		this.type = type;
		this.tecnico = tecnico;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public int getTecnico() {
		return tecnico;
	}

	public void setTecnico(int tecnico) {
		this.tecnico = tecnico;
	}

	@Override
	public int compareTo(Event o) {
		return this.time-o.getTime();
	}
	
	
	
}
