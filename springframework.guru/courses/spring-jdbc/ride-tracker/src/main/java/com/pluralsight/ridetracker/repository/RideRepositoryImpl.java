package com.pluralsight.ridetracker.repository;

import com.pluralsight.ridetracker.model.Ride;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Ride> getRides() {
		List<Ride> rides = jdbcTemplate.query("select * from ride", new RowMapper<Ride>() {

			@Override
			public Ride mapRow(ResultSet rs, int i) throws SQLException {
				return new Ride(rs.getLong("id"), rs.getString("name"), rs.getInt("duration"));
			}
		});
		logger.info("Rides -> {}", rides);
		return rides;
	}

	@Override
	public Ride createRide(Ride ride) {
		jdbcTemplate.update("insert into ride (name, duration) values (?,?)", ride.getName(), ride.getDuration());
		return null;
	}
}
