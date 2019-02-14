package com.pluralsight.ridetracker;

import com.pluralsight.ridetracker.model.Ride;
import com.pluralsight.ridetracker.service.RideService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RideTrackerApplicationTests {

	@Autowired
	private RideService rideService;

	@Test
	public void contextLoads() {
	}


	@Test
	public void testCreateRide() {
		Ride ride = new Ride();
		ride.setName("Sagebrush Trail");
		ride.setDuration(33);


		ride = rideService.createRide(ride);

		System.out.println("Ride: " + ride);
	}

	@Test
	public void testGetRide() {
		RestTemplate restTemplate = new RestTemplate();

		Ride ride = restTemplate.getForObject("http://localhost:8080/ride/1", Ride.class);

		System.out.println("Ride name: " + ride.getName());
	}

	@Test(timeout = 3000)
	public void testUpdateRide() {
		RestTemplate restTemplate = new RestTemplate();

		Ride ride = restTemplate.getForObject("http://localhost:8080/ride/1", Ride.class);

		ride.setDuration(ride.getDuration() + 1);

		restTemplate.put("http://localhost:8080/ride", ride);

		System.out.println("Ride name: " + ride.getName());
	}

	@Test(timeout = 3000)
	public void testBatchUpdate() {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getForObject("http://localhost:8080/batch", Object.class);
	}

	@Test(timeout = 3000)
	public void testDelete() {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.delete("http://localhost:8080/delete/1");
	}

	@Test(timeout = 3000)
	public void testException() {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getForObject("http://localhost:8080/test", Ride.class);
	}

}

