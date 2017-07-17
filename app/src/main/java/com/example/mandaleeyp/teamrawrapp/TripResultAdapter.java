package com.example.mandaleeyp.teamrawrapp;

import android.content.Context;

import com.example.mandaleeyp.teamrawrapp.models.TravelNotice;

import java.util.List;

/**
 * Created by rdicker on 7/17/17.
 */

public class TripResultAdapter {

    private List<TravelNotice> trips;
    Context context;

    public TripResultAdapter(List<TravelNotice> theTrips) {
        trips = theTrips;
    }



}
