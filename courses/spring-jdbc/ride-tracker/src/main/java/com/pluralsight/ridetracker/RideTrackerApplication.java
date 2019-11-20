package com.pluralsight.ridetracker;

import com.pluralsight.ridetracker.model.Ride;
import com.pluralsight.ridetracker.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RideTrackerApplication implements CommandLineRunner {

	@Autowired
	private RideService rideService;

	public static void main(String[] args) {
		SpringApplication.run(RideTrackerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		insertDemo();
		getAllRides();
	}

	private void insertDemo() {
		rideService.createRide(new Ride("Trail Ride", 35));
	}

	private void getAllRides() {
		rideService.getRides();
	}

}

