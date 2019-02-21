package com.pluralsight.ridetracker.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RideRowMapper implements RowMapper<Ride> {

    @Override
    public Ride mapRow(ResultSet rs, int i) throws SQLException {
        return new Ride(rs.getLong("id"), rs.getString("name"), rs.getInt("duration"));
    }
}
