package com.example.mandaleeyp.teamrawrapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mandaleeyp on 7/12/17.
 */

public class Flight {

    private String airlineName;
    private String airlineCode;  // 2 or 3 letters like "AA" for American Airlines, recognized by API
    private String flightNumber; // number portion of an full flight number, like the 0264 in "AA 0264"
    private String departureAirportCode;
    private String arrivalAirportCode;
    private int departDay; // day of the month 1-31
    private int departMonth; // month of the year 1-12
    private int departYear; // four-digit year
    private String departureTime;
    private String departFullDate;


    public static Flight fromJSON(JSONObject jsonObject) throws JSONException {
        Flight flight = new Flight();

        // Parsing flight number
        JSONObject request = jsonObject.getJSONObject("request");
        JSONObject flightNo = request.getJSONObject("flightNumber");
        flight.flightNumber = flightNo.getString("interpreted");

        // Parsing carrier name
        JSONObject appendix = jsonObject.getJSONObject("appendix");
        JSONArray airlines = appendix.getJSONArray("airlines");
        JSONObject airline = airlines.getJSONObject(0);
        flight.airlineName = airline.getString("name");

        // Parsing carrier code
        flight.airlineCode = airline.getString("iata");

        // Parsing departure airport code
        JSONArray scheduledFlights = jsonObject.getJSONArray("scheduledFlights");
        JSONObject scheduledFlight = scheduledFlights.getJSONObject(0);
        flight.departureAirportCode = scheduledFlight.getString("departureAirportFsCode");

        // Parsing arrival airport code
        flight.arrivalAirportCode = scheduledFlight.getString("arrivalAirportFsCode");

        // Parsing departure date
        String departDateAndTime = scheduledFlight.getString("departureTime");
        flight.departYear = Integer.parseInt(departDateAndTime.substring(0, 4));
        flight.departMonth = Integer.parseInt(departDateAndTime.substring(5, 7));
        flight.departDay = Integer.parseInt(departDateAndTime.substring(8, 10));
        flight.departFullDate = flight.departMonth + "/" + flight.departDay + "/" + flight.departYear;

        // Parsing time
        flight.departureTime = departDateAndTime.substring(11, 16);

        // TODO - Add stuff to flight
        // flight.flightNumber = jsonObject.getString("flig");
        return flight;
    }





    public String getAirlineCode() {
        return airlineCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirlineName() {
        return airlineName;
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

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getDepartFullDate() {
        return departFullDate;
    }
}
