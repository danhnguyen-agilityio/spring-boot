package com.agility;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DateDemo {
    public static void main(String[] args) {
        Date mod_date = new Date();
        Timestamp timestamp = new java.sql.Timestamp(mod_date.getTime());

        String x = "2019-10-23T19:16:56Z";
        Instant instant = Instant.parse(x);
        System.out.println("dateinstant" + instant);
        Date dateinstant = Date.from(instant);

        System.out.println("convertDateToGMTString " + convertDateToGMTString(dateinstant));

        System.out.println("End");

        Map<String, String> map = new HashMap<>();
        map.put("a","a");
        map.put("b","b");
        System.out.println(map.get("a"));
        System.out.println(map.get("c"));
    }

    public static String convertDateToGMTString(Date dateInput) {
        DateFormat converter = new SimpleDateFormat("yyyy-MM-dd");
        converter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return converter.format(dateInput.getTime());
    }
}
