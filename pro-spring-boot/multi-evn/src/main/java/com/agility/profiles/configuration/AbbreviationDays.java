package com.agility.profiles.configuration;

public enum AbbreviationDays {
    SUNDAY("SUN"),
    MONDAY("MON"),
    TUESDAY("TUES"),
    WEDNESDAY("WED"),
    THURSDAY("THURS"),
    FRIDAY("FRI"),
    SATURDAY("SAT");

    String abbreviation;

    AbbreviationDays(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}

class Main {
    public static void main(String[] args) {
        AbbreviationDays[] abbreviationDays = AbbreviationDays.values();
        for (AbbreviationDays abbreviationDay : abbreviationDays) {
            System.out.println(abbreviationDay.getAbbreviation());
        }
    }
}
