package com.pluralsight.ridetracker.repository;

import com.pluralsight.ridetracker.model.Ride;

import java.util.List;

public interface RideRepository {

	List<Ride> getRides();

	Ride createRide(Ride ride);
}