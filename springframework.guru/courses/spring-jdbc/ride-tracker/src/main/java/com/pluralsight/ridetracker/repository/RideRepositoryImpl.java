package com.pluralsight.ridetracker.repository;

import com.pluralsight.ridetracker.model.Ride;
import com.pluralsight.ridetracker.model.RideRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Ride> getRides_InnerClassRowMapper() {
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
	public List<Ride> getRides() {
		List<Ride> rides = jdbcTemplate.query("select * from ride", new RideRowMapper());
		logger.info("Rides -> {}", rides);
		return rides;
	}

	public Ride createRide_NotReturn(Ride ride) {
		jdbcTemplate.update("insert into ride (name, duration) values (?,?)", ride.getName(), ride.getDuration());
		return null;
	}

	@Override
	public Ride createRide(Ride ride) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement("insert into ride (name, duration) values (?,?)",
						new String[]{"id"});
				ps.setString(1, ride.getName());
				ps.setInt(2, ride.getDuration());
				return ps;
			}
		}, keyHolder);

		Number id = keyHolder.getKey();
		return getRide(id.intValue());
	}

	@Override
	public Ride getRide(Integer id) {
		Ride ride = jdbcTemplate.queryForObject("select * from ride where id = ?", new RideRowMapper(), id);

		return ride;
	}

}
