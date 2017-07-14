package com.example.mandaleeyp.teamrawrapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by robertvunabandi on 7/13/17.
 */

public class TravelNotice {
    public final String[] itemTypes = {"envelope", "smbox", "lgbox", "clothing", "other"};
    // required: 3
    public String tuid, airline, flight_num;
    // optional: 7
    public Boolean item_envelope, item_smbox, item_lgbox, item_clothing, item_other;
    public String item_other_name, drop_off_flexibility, pick_up_flexibility;
    // required: 12
    public String dep_iata, dep_city;
    public int dep_min, dep_hour, dep_day, dep_month, dep_year;
    public String arr_iata, arr_city;
    public int arr_min, arr_hour, arr_day, arr_month, arr_year;

    // empty method for parcel just in case
    public TravelNotice() {}

    public static TravelNotice fromJSON(JSONObject response, String tuid, Boolean[] itemBools, String itemOtherName, String[] flexibilities) throws JSONException{
        // initiate an empty travelNotice
        TravelNotice tvl = new TravelNotice();

        // create variables that will be to make code clearer
        JSONObject scheduledFlights = response.getJSONArray("scheduledFlights").getJSONObject(0);
        JSONArray airports = response.getJSONObject("appendix").getJSONArray("airports");

        // get the important required informations into tvl
        tvl.tuid = tuid;
        tvl.airline = scheduledFlights.getString("carrierFsCode");
        tvl.flight_num = scheduledFlights.getString("flightNumber");

        // add the informations for filtering, a lot of which passed onto in arguments
        if (itemBools != null) {
            // if the booleans of items are not null then we populate the travel with these values in order
            tvl.item_envelope = itemBools[0];
            tvl.item_smbox = itemBools[1];
            tvl.item_lgbox = itemBools[2];
            tvl.item_clothing = itemBools[3];
            tvl.item_other = itemBools[4];
            tvl.item_other_name = itemOtherName;
        } else {
            // set everything to true (assume traveler is okay with carrying anything) and set item_other to false and item_other_name to null
            tvl.item_envelope = true; tvl.item_smbox = true; tvl.item_lgbox = true; tvl.item_clothing = true; tvl.item_other = false; tvl.item_other_name = null;
        }
        if (flexibilities != null){
            // if the flexibilities are put, update flexibilities
            tvl.drop_off_flexibility = flexibilities[0];
            tvl.pick_up_flexibility = flexibilities[1];
        } else {
            // otherwise set them to null
            tvl.drop_off_flexibility = null; tvl.pick_up_flexibility = null;
        }

        // add the departure informations
        tvl.dep_iata = scheduledFlights.getString("departureAirportFsCode");

        for (int i = 0; i < airports.length(); i++){
            // check if this airport's iata matches that of the depature and then get the name
            if (airports.getJSONObject(i).getString("fs").equals(tvl.dep_iata)){
                tvl.dep_city = airports.getJSONObject(i).getString("city"); // GET THE CITY
            }
        }
        tvl.dep_hour = Integer.parseInt(scheduledFlights.getString("departureTime").substring(11, 13));
        tvl.dep_min = Integer.parseInt(scheduledFlights.getString("departureTime").substring(14, 16));
        tvl.dep_day = Integer.parseInt(scheduledFlights.getString("departureTime").substring(8, 10));
        tvl.dep_month = Integer.parseInt(scheduledFlights.getString("departureTime").substring(5, 7));
        tvl.dep_year = Integer.parseInt(scheduledFlights.getString("departureTime").substring(0, 4));

        // add the arrival information
        tvl.arr_iata = scheduledFlights.getString("arrivalAirportFsCode");
        for (int i = 0; i < airports.length(); i++){
            // check if this airport's iata matches that of the depature and then get the name
            if (airports.getJSONObject(i).getString("fs").equals(tvl.arr_iata)){
                tvl.arr_city = airports.getJSONObject(i).getString("city"); // GET THE CITY
            }
        }
        tvl.arr_hour = Integer.parseInt(scheduledFlights.getString("arrivalTime").substring(11, 13));
        tvl.arr_min = Integer.parseInt(scheduledFlights.getString("arrivalTime").substring(14, 16));
        tvl.arr_day = Integer.parseInt(scheduledFlights.getString("arrivalTime").substring(8, 10));
        tvl.arr_month = Integer.parseInt(scheduledFlights.getString("arrivalTime").substring(5, 7));
        tvl.arr_year = Integer.parseInt(scheduledFlights.getString("arrivalTime").substring(0, 4));

        // return the tvl
        return tvl;
    }
}
