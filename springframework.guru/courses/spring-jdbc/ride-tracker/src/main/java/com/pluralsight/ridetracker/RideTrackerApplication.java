package com.pluralsight.ridetracker;

import com.pluralsight.ridetracker.model.Ride;
import com.pluralsight.ridetracker.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RideTrackerApplication {

	@Autowired
	private RideService rideService;

	public static void main(String[] args) {
		SpringApplication.run(RideTrackerApplication.class, args);
	}

	private void insertDemo() {
		rideService.createRide(new Ride("Trail Ride", 35));
	}

}

