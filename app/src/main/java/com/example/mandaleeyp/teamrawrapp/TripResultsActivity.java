package com.example.mandaleeyp.teamrawrapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.mandaleeyp.teamrawrapp.models.TravelNotice;

import java.util.ArrayList;

public class TripResultsActivity extends AppCompatActivity {

    TripResultAdapter tripAdapter;
    ArrayList<TravelNotice> tripResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_results);

        tripResults = new ArrayList<>();
        tripAdapter = new TripResultAdapter(tripResults);
        RecyclerView rvTripResults = (RecyclerView) findViewById(R.id.rvTripResults);


        rvTripResults.setAdapter(tripAdapter);
    }
}
