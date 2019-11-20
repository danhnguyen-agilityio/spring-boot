package com.pluralsight.ridetracker.controller;

import com.pluralsight.ridetracker.model.Ride;
import com.pluralsight.ridetracker.service.RideService;
import com.pluralsight.ridetracker.util.ServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RideController {

	@Autowired
	private RideService rideService;

	@RequestMapping(value = "/ride", method = RequestMethod.POST)
	public @ResponseBody Ride createRide(@RequestBody Ride ride) {
		return rideService.createRide(ride);
	}
	
	@RequestMapping(value = "/rides", method = RequestMethod.GET)
	public @ResponseBody
    List<Ride> getRides() {
		return rideService.getRides();
	}

	@RequestMapping(value = "/ride/{id}", method = RequestMethod.GET)
	public @ResponseBody Ride getRide(@PathVariable(value = "id") Integer id) {
		return rideService.getRide(id);
	}

	@RequestMapping(value = "/ride", method = RequestMethod.PUT)
	public @ResponseBody Ride updateRide(@RequestBody Ride ride) {
		return rideService.updateRide(ride);
	}

	@RequestMapping(value = "/batch", method = RequestMethod.GET)
	public @ResponseBody Object batch() {
		rideService.batch();
		return null;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Object delete (@PathVariable(value = "id") Integer id) {
		rideService.deleteRide(id);
		return null;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody Object test() {
		throw new DataAccessException("Testing exception thrown") {};
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ServiceError> handle(RuntimeException ex) {
		ServiceError error = new ServiceError(HttpStatus.OK.value(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.OK);
	}

}
