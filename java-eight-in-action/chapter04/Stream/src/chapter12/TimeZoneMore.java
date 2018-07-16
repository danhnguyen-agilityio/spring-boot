package chapter12;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneMore {
  public static void main(String[] args) {
    // Starting with an java.time.Instant value
    Instant timeStamp = Instant.now();
    System.out.println("machine time now: " + timeStamp);

    // timeStamp in zone - America/LosAngeles
    ZonedDateTime LAZone = timeStamp.atZone(ZoneId.of("America/Los_Angeles"));
    System.out.println("In Los Angeles Time Zone: " + LAZone);

    // timeStamp in zone - "GMT+01:00"
    ZonedDateTime timestampAtGMTPlus1 = timeStamp.atZone(ZoneId.of("GMT+01:00"));
    System.out.println("timestampAtGMTPlus1: " + timestampAtGMTPlus1);

    // timeStamp in zone - "Asia/Ho_Chi_Minh"
    ZonedDateTime HCMZone = timeStamp.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
    System.out.println("HCMZone : " + HCMZone);
  }
}
