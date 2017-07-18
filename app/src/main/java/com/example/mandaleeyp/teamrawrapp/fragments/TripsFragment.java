package com.example.mandaleeyp.teamrawrapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mandaleeyp.teamrawrapp.R;
import com.example.mandaleeyp.teamrawrapp.UpcomingTripAdapter;
import com.example.mandaleeyp.teamrawrapp.models.TravelNotice;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TripsFragment extends Fragment {
//    public final static String TAG = "TravelFragment";


    // Database url
    public final static String DB_HEROKU_URL = "http://mysterious-headland-54722.herokuapp.com";
    public final static String DB_LOCAL_URL = "http://172.22.8.106:3000";
    public final static String[] DB_URLS = {DB_HEROKU_URL, DB_LOCAL_URL};

    // Declaring client
    AsyncHttpClient client;

    UpcomingTripAdapter upcomingTripAdapter;
    ArrayList<TravelNotice> mTrips;
    RecyclerView rvTrips;

    public TripsFragment() {
    }

    public static TripsFragment newInstance(String param1, String param2) {
        TripsFragment fragment = new TripsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new AsyncHttpClient();
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trips, container, false);

        // find the RecyclerView
        mTrips = new ArrayList<>();
        upcomingTripAdapter = new UpcomingTripAdapter(mTrips);
        rvTrips = (RecyclerView) v.findViewById(R.id.rvTrips);
        rvTrips.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTrips.setAdapter(upcomingTripAdapter);

        return v;
    }

    private void populateList(JSONArray travelNoticeList) {
        for (int i = 0; i < travelNoticeList.length(); i++) {
            try {
                TravelNotice travelNotice = TravelNotice.fromJSONServer(travelNoticeList.getJSONObject(i));
                mTrips.add(travelNotice);
                upcomingTripAdapter.notifyItemInserted(mTrips.size() - 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getData() {

        // Temporary tuid
        String traveler_id = "596d0b5626bffc280b32187e";

        // Set the request parameters
        RequestParams params = new RequestParams();

        client.get(DB_URLS[0] + "/travel_notice_all", params, new JsonHttpResponseHandler() {

            // implement endpoint here
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    populateList(response.getJSONArray("data"));
                    Toast.makeText(getContext(), String.format("%s", response), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
            }
        }

        @Override
        public void onFailure ( int statusCode, Header[] headers, Throwable throwable, JSONObject
        errorResponse){
            Toast.makeText(getContext(), String.format("error 1 %s", errorResponse), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure ( int statusCode, Header[] headers, Throwable throwable, JSONArray
        errorResponse){
            Toast.makeText(getContext(), String.format("error 2 %s", errorResponse), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure ( int statusCode, Header[] headers, String responseString, Throwable
        throwable){
            Toast.makeText(getContext(), String.format("error 3"), Toast.LENGTH_SHORT).show();
        }
    });

}

public interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri uri);
}
}
