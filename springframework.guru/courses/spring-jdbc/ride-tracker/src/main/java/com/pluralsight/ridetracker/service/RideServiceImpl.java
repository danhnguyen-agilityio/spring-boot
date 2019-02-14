package com.pluralsight.ridetracker.service;

import com.pluralsight.ridetracker.model.Ride;
import com.pluralsight.ridetracker.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("rideService")
public class RideServiceImpl implements RideService {

	@Autowired
	private RideRepository rideRepository;
	
	@Override
	public List<Ride> getRides() {
		return rideRepository.getRides();
	}

	@Override
	public Ride createRide(Ride ride) {
		return rideRepository.createRide(ride);
	}

	@Override
	public Ride getRide(Integer id) {
		return rideRepository.getRide(id);
	}
}
