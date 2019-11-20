package com.pluralsight.ridetracker.model;

public class Ride {

	private Long id;
	private String name;
	private int duration;

	public Ride() {
	}

	public Ride(String name, int duration) {
		this.name = name;
		this.duration = duration;
	}

	public Ride(Long id, String name, int duration) {
		this.id = id;
		this.name = name;
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	public String getName() {
		return name;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("Id -> %s, Name -> %s, Duration -> %s",id, name, duration);
	}
}
