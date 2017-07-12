package com.example.mandaleeyp.teamrawrapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mandaleeyp on 7/12/17.
 */

public class Flight {

    private String airlineCode;  // 2 or 3 letters like "AA" for American Airlines, recognized by API
    private int flightNumber; // number portion of an full flight number, like the 0264 in "AA 0264"
    private int departDay; // day of the month 1-31
    private int departMonth; // month of the year 1-12
    private int departYear; // four-digit year

    public static Flight fromJSON(JSONObject jsonObject) throws JSONException {
        Flight flight = new Flight();
        // TODO - Add stuff to flight
        // flight.flightNumber = jsonObject.getString("flig");
        return flight;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public int getDepartDay() {
        return departDay;
    }

    public int getDepartMonth() {
        return departMonth;
    }

    public int getDepartYear() {
        return departYear;
    }
}
