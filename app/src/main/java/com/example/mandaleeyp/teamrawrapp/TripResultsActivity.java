package com.example.mandaleeyp.teamrawrapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.mandaleeyp.teamrawrapp.models.TravelNotice;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TripResultsActivity extends AppCompatActivity {

    // Database url
    public final static String DB_HEROKU_URL = "http://mysterious-headland-54722.herokuapp.com";
    public final static String DB_LOCAL_URL = "http://172.22.8.106:3000";
    public final static String[] DB_URLS = {DB_HEROKU_URL, DB_LOCAL_URL};

    // Declaring client
    AsyncHttpClient client;

    RecyclerView rvTripResults;
    TripResultAdapter tripResultAdapter;
    ArrayList<TravelNotice> tripResults;



    public TripResultsActivity() {
    }



//    public static TripResultsActivity newInstance(String param1, String param2) {
//        TripResultsActivity activity = new TripResultsActivity();
//        Bundle args = new Bundle();
//        activity.setArguments(args);
//        return activity;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView (R.layout.activity_trip_results);

        super.onCreate(savedInstanceState);
        client = new AsyncHttpClient();

        // find the RecyclerView
        tripResults = new ArrayList<>();
        tripResultAdapter = new TripResultAdapter(tripResults);
        rvTripResults = (RecyclerView) findViewById(R.id.rvTripResults);
        rvTripResults.setLayoutManager(new LinearLayoutManager(this));
        rvTripResults.setAdapter(tripResultAdapter);

        getData();
    }



//    @Nullable
//    @Override
//    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        // Inflate the layout for this fragment
//        View v = getLayoutInflater().inflate(R.layout.activity_trip_results, new ViewGroup() {
//            @Override
//            protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//            }
//        }, false);
//
//        // find the RecyclerView
//        tripResults = new ArrayList<>();
//        tripResultAdapter = new TripResultAdapter(tripResults);
//        rvTripResults = (RecyclerView) v.findViewById(R.id.rvTripResults);
//        rvTripResults.setLayoutManager(new LinearLayoutManager(this));
//        rvTripResults.setAdapter(tripResultAdapter);
//
//        return v;
//    }




    private void populateList(JSONArray travelNoticeList) {
        for (int i = 0; i < travelNoticeList.length(); i++) {
            try {
                TravelNotice travelNotice = TravelNotice.fromJSONServer(travelNoticeList.getJSONObject(i));
                tripResults.add(travelNotice);
                tripResultAdapter.notifyItemInserted(tripResults.size() - 1);
                Toast.makeText(this, String.format("%s", travelNotice), Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                Log.e("E", String.format("Error occured in JSON parsing"));
                e.printStackTrace();
                // Toast.makeText(getContext(), String.format("%s", e), Toast.LENGTH_LONG).show();
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
                tripResultAdapter.clear();

                try {
                    populateList(response.getJSONArray("data"));
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure ( int statusCode, Header[] headers, Throwable throwable, JSONObject
                    errorResponse){
                // Toast.makeText(getContext(), String.format("error 1 %s", errorResponse), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure ( int statusCode, Header[] headers, Throwable throwable, JSONArray
                    errorResponse){
                // Toast.makeText(getContext(), String.format("error 2 %s", errorResponse), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure ( int statusCode, Header[] headers, String responseString, Throwable
                    throwable){
                // Toast.makeText(getContext(), String.format("error 3"), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
