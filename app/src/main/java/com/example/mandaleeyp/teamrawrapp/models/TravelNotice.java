package com.example.mandaleeyp.teamrawrapp.models;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by robertvunabandi on 7/13/17.
 */

@Parcel
public class TravelNotice {
    public final String[] itemTypes = {"envelope", "smbox", "lgbox", "clothing", "other"};
    // required: 3
    public String id, tuid, airline, flight_num;
    // optional: 6
    public Boolean item_envelopes, item_smbox, item_lgbox, item_clothing, item_other;
    public String drop_off_flexibility, pick_up_flexibility;
    // required: 12
    public String dep_iata, dep_city;
    public int dep_min, dep_hour, dep_day, dep_month, dep_year;
    public String arr_iata, arr_city;
    public int arr_min, arr_hour, arr_day, arr_month, arr_year;
    public JSONArray requests_ids;

    // empty method for parcel just in case
    public TravelNotice() {
    }

    public static TravelNotice fromJSON(JSONObject response, String tuid, Boolean[] itemBools, String[] flexibilities) throws JSONException {
        // initiate an empty travelNotice
        TravelNotice tvl = new TravelNotice();

        // create variables that will be to make code clearer
        JSONObject scheduledFlights = response.getJSONArray("scheduledFlights").getJSONObject(0);
        JSONArray airports = response.getJSONObject("appendix").getJSONArray("airports");

        // get the important required informations into tvl
        tvl.id = null;
        tvl.tuid = tuid;
        tvl.airline = scheduledFlights.getString("carrierFsCode");
        tvl.flight_num = scheduledFlights.getString("flightNumber");

        // add the informations for filtering, a lot of which passed onto in arguments
        if (itemBools != null) {
            // if the booleans of items are not null then we populate the travel with these values in order
            tvl.item_envelopes = itemBools[0];
            tvl.item_smbox = itemBools[1];
            tvl.item_lgbox = itemBools[2];
            tvl.item_clothing = itemBools[3];
            tvl.item_other = itemBools[4];
        } else {
            // set everything to true (assume traveler is okay with carrying anything) and set item_other to false and item_other_name to null
            tvl.item_envelopes = true;
            tvl.item_smbox = true;
            tvl.item_lgbox = true;
            tvl.item_clothing = true;
            tvl.item_other = true;
        }
        if (flexibilities != null) {
            // if the flexibilities are put, update flexibilities
            tvl.drop_off_flexibility = flexibilities[0];
            tvl.pick_up_flexibility = flexibilities[1];
        } else {
            // otherwise set them to null
            tvl.drop_off_flexibility = null;
            tvl.pick_up_flexibility = null;
        }

        // add the departure informations
        tvl.dep_iata = scheduledFlights.getString("departureAirportFsCode");

        for (int i = 0; i < airports.length(); i++) {
            // check if this airport's iata matches that of the depature and then get the name
            if (airports.getJSONObject(i).getString("fs").equals(tvl.dep_iata)) {
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
        for (int i = 0; i < airports.length(); i++) {
            // check if this airport's iata matches that of the depature and then get the name
            if (airports.getJSONObject(i).getString("fs").equals(tvl.arr_iata)) {
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

    /* Creates parameters for sending a request to our database directly
    from this travel notice using its own data, which is why the "this". */
    public RequestParams createParams() {
        /**
         * Creates parameter for endpoint call /travel_notice_add and /travel_notice_update */
        // TODO = be careful about _id being null
        RequestParams params = new RequestParams();
        // required, 3 parameters
        params.put("_id", this.id);
        params.put("tuid", this.tuid);
        params.put("airline", this.airline);
        params.put("flight_num", this.flight_num);
        // optional: 7 parameters
        params.put("item_envelopes", this.item_envelopes);
        params.put("item_smbox", this.item_smbox);
        params.put("item_lgbox", this.item_lgbox);
        params.put("item_clothing", this.item_clothing);
        params.put("item_other", this.item_other);
        params.put("drop_off_flexibility", this.drop_off_flexibility);
        params.put("pick_up_flexibility", this.pick_up_flexibility);
        // required: 7 departure informations
        params.put("dep_iata", this.dep_iata);
        params.put("dep_city", this.dep_city);
        params.put("dep_min", this.dep_min);
        params.put("dep_hour", this.dep_hour);
        params.put("dep_day", this.dep_day);
        params.put("dep_month", this.dep_month);
        params.put("dep_year", this.dep_year);
        // required: 7 arrival informations
        params.put("arr_iata", this.arr_iata);
        params.put("arr_city", this.arr_city);
        params.put("arr_min", this.arr_min);
        params.put("arr_hour", this.arr_hour);
        params.put("arr_day", this.arr_day);
        params.put("arr_month", this.arr_month);
        params.put("arr_year", this.arr_year);
        params.put("requests_ids", this.requests_ids);
        return params;
    }

    public static TravelNotice fromJSONServer(JSONObject response) throws JSONException {
        // initiate an empty travelNotice
        TravelNotice tvl = new TravelNotice();

        // get the important required informations into tvl
        tvl.id = response.getString("_id");
        tvl.tuid = response.getString("tuid");
        tvl.airline = response.getString("airline");
        tvl.flight_num = response.getString("flight_num");

        // add the informations for filtering, a lot of which passed onto in arguments

        tvl.item_envelopes = response.getBoolean("item_envelopes");
        tvl.item_smbox = response.getBoolean("item_smbox");
        tvl.item_lgbox = response.getBoolean("item_lgbox");
        tvl.item_clothing = response.getBoolean("item_clothing");
        tvl.item_other = response.getBoolean("item_other");

        // if the flexibilities are put, update flexibilities
        try {
            // we do a try catch since both these infor
            tvl.drop_off_flexibility = response.getString("drop_off_flexibility");
        } catch (JSONException e) { tvl.drop_off_flexibility = null; }
        try {
            tvl.pick_up_flexibility = response.getString("pick_up_flexibility");
        } catch (JSONException e) {tvl.pick_up_flexibility = null; }

        // add the departure informations
        tvl.dep_iata = response.getString("dep_iata");
        tvl.dep_city = response.getString("dep_city");
        tvl.dep_hour = response.getInt("dep_hour");
        tvl.dep_min = response.getInt("dep_min");
        tvl.dep_day = response.getInt("dep_day");
        tvl.dep_month = response.getInt("dep_month");
        tvl.dep_year = response.getInt("dep_year");

        // add the arrival information
        tvl.arr_iata = response.getString("arr_iata");
        tvl.arr_city = response.getString("arr_city");
        tvl.arr_hour = response.getInt("arr_hour");
        tvl.arr_min = response.getInt("arr_min");
        tvl.arr_day = response.getInt("arr_day");
        tvl.arr_month = response.getInt("arr_month");
        tvl.arr_year = response.getInt("arr_year");

        tvl.requests_ids = response.getJSONArray("requests_ids");

        // return the tvl
        return tvl;
    }
}
