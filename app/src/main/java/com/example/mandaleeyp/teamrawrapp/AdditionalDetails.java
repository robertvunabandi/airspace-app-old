package com.example.mandaleeyp.teamrawrapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mandaleeyp.teamrawrapp.models.TravelNotice;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AdditionalDetails extends AppCompatActivity {
    // views, TODO - Add the views for displaying the original travel notice
    public CheckBox cb_envelope, cb_smallBox, cb_largeBox, cb_clothing, cb_other;
    public Button addDetailsSubmit;
    public EditText dropoffFlexibility, pickupFlexibility;

    // details about the flight
    public TextView TIDeparture, TIDestination, TITimeDeparture, TITimeArrival, TIDate, TIAirline;
    public RelativeLayout TITTimeOvernightIndicator;

    // to get the travel notice from the database
    AsyncHttpClient client;
    public String[] DB_URLS;
    public String travelNoticeId, tuid;
    public TravelNotice tvl;

    // each index refer to one thing, so [envelope, smbox, lgbox, clothing, other]
    public Boolean[] itemBools = {false, false, false, false, false};

    // Tag for debugging, A stands for activity and parent layout for snackbar
    public final static String TAG = "A:AdditionalDetails";
    public View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_additional_details);

        // get the travel notice stuffs to make a call to the database
        travelNoticeId = getIntent().getStringExtra("travel_notice_id");
        tuid = getIntent().getStringExtra("tuid");
        DB_URLS = new String[] {getString(R.string.DB_HEROKU_URL), getString(R.string.DB_LOCAL_URL)};

        client = new AsyncHttpClient();
        getTravelNotice(travelNoticeId, tuid); // this will update the variable tvl, so populating the travel notice stuffs should be inside of that method

        // get the items in the XML
        cb_envelope = (CheckBox) findViewById(R.id.cb_envelope);
        cb_smallBox = (CheckBox) findViewById(R.id.cb_smallBox);
        cb_largeBox = (CheckBox) findViewById(R.id.cb_largeBox);
        cb_clothing = (CheckBox) findViewById(R.id.cb_clothing);
        cb_other = (CheckBox) findViewById(R.id.cb_other);
        addDetailsSubmit = (Button) findViewById(R.id.bt_addDetailsSubmit);
        dropoffFlexibility = (EditText) findViewById(R.id.et_pickup);
        pickupFlexibility = (EditText) findViewById(R.id.et_dropoff);
        // item XML for the travel notice itself
        TIDeparture = (TextView) findViewById(R.id.tv_TIDeparture);
        TIDestination = (TextView) findViewById(R.id.tv_TIDestination);
        TITimeDeparture = (TextView) findViewById(R.id.tv_TITimeDeparture);
        TITimeArrival = (TextView) findViewById(R.id.tv_TITimeArrival);
        TIDate = (TextView) findViewById(R.id.tv_TIDate);
        TIAirline = (TextView) findViewById(R.id.tv_TIAirline);
        TITTimeOvernightIndicator = (RelativeLayout) findViewById(R.id.rl_TITTimeOvernightIndicator);

        // item XML for debugging
        parentLayout = findViewById(R.id.rootAdditionalDetails);

        // update tvl dropoff flex when user changes that text
        dropoffFlexibility.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // If the user changes the text, this updates the tvl.drop_off_flexibility, which the params are taken from
                if (s.length() > 0) {
                    tvl.drop_off_flexibility = s.toString();
                }
                else {
                    tvl.drop_off_flexibility = null;
                }
            }
        });

        // update tvl picku[ flex when user changes that text
        pickupFlexibility.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // If the user changes the text, this updates the tvl.pick_up_flexibility, which the params are taken from
                if (s.length() > 0) {
                    tvl.pick_up_flexibility = s.toString();
                }
                else {
                    tvl.pick_up_flexibility = null;
                }
            }
        });

        // on click listener for when someone clicks the submit button
        addDetailsSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // see method below, which is supposed to start a new activity and take one to upcoming
                updateFlight();
            }
        });
    }

    public void onCheckboxClicked(View view) {

        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.cb_envelope: {
                itemBools[0] = checked;
                tvl.item_envelopes = checked;
                break;
            }
            case R.id.cb_smallBox: {
                itemBools[1] = checked;
                tvl.item_smbox = checked;
                break;
            }
            case R.id.cb_largeBox: {
                itemBools[2] = checked;
                tvl.item_lgbox = checked;
                break;
            }
            case R.id.cb_clothing: {
                itemBools[3] = checked;
                tvl.item_clothing = checked;
                break;
            }
            case R.id.cb_other: {
                tvl.item_other = checked;
                break;
            }
            default: {
                break;
            }
        }
    }

    public void populateTravelNoticeViews(){
        // this is called inside of getTravelNotice!!!
        TIDeparture.setText(tvl.dep_city + " (" + tvl.dep_iata +")");
        TIDestination.setText(tvl.arr_city + " (" + tvl.arr_iata +")");
        TITimeDeparture.setText(tvl.getDepartureTime());
        TITimeArrival.setText(tvl.getArrivalTime());
        TIDate.setText(tvl.getDepartureDayVerbose());
        TIAirline.setText(tvl.airline);
        if (tvl.isOvernight()) {
            TITTimeOvernightIndicator.setVisibility(View.VISIBLE);
        } else {
            TITTimeOvernightIndicator.setVisibility(View.GONE);
        }
    }

    public void getTravelNotice(String travelNoticeId_, String tuid_) {
        RequestParams params = new RequestParams();
        params.put("travel_notice_id", travelNoticeId_);
        params.put("tuid", tuid_);
        client.get(DB_URLS[0] + "/travel_notice_get", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    tvl = TravelNotice.fromJSONServer(response.getJSONObject("data"));
                    populateTravelNoticeViews();
                    Log.w(TAG, String.format("%s", response)); // debugging
                } catch (JSONException e) {
                    e.printStackTrace();
                    Snackbar.make(parentLayout, String.format("An error occured while parsing the JSON response to get the travel notice from server."), Snackbar.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response); // we don't expect an array
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG, String.format("Error (1) occured %s", errorResponse));
                Snackbar.make(parentLayout, String.format("Error (1) occurred"), Snackbar.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e(TAG, String.format("Error (2) occured %s", errorResponse));
                Snackbar.make(parentLayout, String.format("Error (2) occurred"), Snackbar.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, String.format("Error (3) occured %s", responseString));
                Snackbar.make(parentLayout, String.format("Error (3) occurred"), Snackbar.LENGTH_LONG).show();
                finish();
            }
        });
    }

    public void updateFlight() {
        RequestParams params = tvl.createParams();
        client.post(DB_URLS[0] + "/travel_notice_update", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // take the person to upcoming with this result
                setResult(RESULT_OK); finish();
                Snackbar.make(parentLayout, String.format("Your travel notice has been saved successfully!"), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG, String.format("Error (1) occured %s", errorResponse));
                setResult(RESULT_OK); finish();
                Snackbar.make(parentLayout, String.format("Error (1) occurred, However, your travel has been saved without additional details."), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e(TAG, String.format("Error (2) occured %s", errorResponse));
                Snackbar.make(parentLayout, String.format("Error (2) occurred, However, your travel has been saved without additional details."), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, String.format("Error (3) occured %s", responseString));
                Snackbar.make(parentLayout, String.format("Error (3) occurred, However, your travel has been saved without additional details."), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
