package com.example.mandaleeyp.teamrawrapp.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mandaleeyp.teamrawrapp.AdditionalDetails;
import com.example.mandaleeyp.teamrawrapp.R;
import com.example.mandaleeyp.teamrawrapp.models.Flight;
import com.example.mandaleeyp.teamrawrapp.models.TravelNotice;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class TravelFragment extends Fragment {
    // TAG
    public final static String TAG = "TravelFragment";

    // base URL for API
    public final static String API_BASE_URL = "https://api.flightstats.com/flex/schedules/rest";
    // parameter name for API key
    public final static String APP_KEY_PARAM = "appKey";
    public final static String APP_ID_PARAM = "appId";
    // our database url
    public final static String DB_HEROKU_URL = "http://mysterious-headland-54722.herokuapp.com";
    public final static String DB_LOCAL_URL = "http://172.22.11.86:3000";
    public final static String[] DB_URLS = {DB_HEROKU_URL, DB_LOCAL_URL};

    private int flightYear;
    private int flightMonth;
    private int flightDay;
    private String airlineCode;
    Button btnSubmit;
    int FROM_CODE = 0;

    // instance fields
    AsyncHttpClient client;
    Flight flight;


    public TravelFragment() {
        // Required empty public constructor
    }

    public static TravelFragment newInstance(String param1, String param2) {
        TravelFragment fragment = new TravelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = new AsyncHttpClient();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_travel, container, false);
        TextInputLayout airlinecodeWrapper = (TextInputLayout) v.findViewById(R.id.airlinecodeWrapper);
        TextInputLayout flightnumberWrapper = (TextInputLayout) v.findViewById(R.id.flightnumberWrapper);
        TextInputLayout dateWrapper = (TextInputLayout) v.findViewById(R.id.dateWrapper);
        btnSubmit = (Button) v.findViewById(R.id.bt_submit);
        final EditText airlineCode = (EditText) v.findViewById(R.id.til_airlineCode);
        final EditText flightNumber = (EditText) v.findViewById(R.id.til_flightNumber);

        airlinecodeWrapper.setHint("Airline code");
        flightnumberWrapper.setHint("Flight number");
        dateWrapper.setHint("Date of departure");

        final EditText et_date = (EditText) v.findViewById(R.id.et_date);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // updateLabel();
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                et_date.setText(sdf.format(myCalendar.getTime()));

                flightYear = year;
                flightMonth = monthOfYear + 1; // Since month start at 0
                flightDay = dayOfMonth;
            }
        };

        // onClickListener for date
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // onClickListener for submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create URL
                String url = API_BASE_URL + "/v1/json/flight/" + airlineCode.getText() + "/" + flightNumber.getText() + "/departing/" + flightYear + "/" + flightMonth + "/" + flightDay;
                Log.e(TAG, url);
                // set request parameters
                RequestParams params = new RequestParams();
                params.put(APP_KEY_PARAM, getString(R.string.api_key)); // API key, always required
                params.put(APP_ID_PARAM, getString(R.string.app_id)); // AppId

                //execute a GET request expecting a JSON object response
                client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            // get flight
                            flight = Flight.fromJSON(response);
                            // Log.d(TAG, String.format("%s", response)); // debugging to see response
                            processResponse(response);
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), String.format("JSON Parsing of response from flight API failed"), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.e(TAG, String.format("error 1: %s", errorResponse));
                        Toast.makeText(getContext(), String.format("error 1 %s", errorResponse), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Log.e(TAG, String.format("error 2: %s", errorResponse));
                        Toast.makeText(getContext(), String.format("error 2 %s", errorResponse), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.e(TAG, String.format("error 3: %s", responseString));
                        Toast.makeText(getContext(), String.format("error 3 %s", responseString), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return v;
    }

    public void processResponse(final JSONObject response) {
//        Toast.makeText(getContext(), String.format("%s", response.toString()), Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(flight.getAirlineName() + "\nFlight " + flight.getAirlineCode() +  " " + flight.getFlightNumber() + "\n"
                            + flight.getDepartureAirportCode() + " to " + flight.getArrivalAirportCode()
                            + "\nDeparting on " + flight.getDepartFullDate() + " at " + flight.getDepartureTime())
                .setTitle(R.string.dialog_title);

        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button, so send response to database

                String traveler_id = "596d0b5626bffc280b32187e"; // TODO - Make it so the traveler id is actually the id of the person using the app like "get res client"
                // first, create the travelNotice, all surrounded by try catch
                try {
                    // creates a travel notice
                    final TravelNotice tvl = TravelNotice.fromJSON(response, traveler_id, null, null);
                    // get parameters from the method createParams() in TravelNotice, see that method
                    RequestParams params = tvl.createParams();
                    // Send a request to the database with endpoint /travel_notice_add
                    client.post(DB_URLS[0] + "/travel_notice_add", params, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                if (!response.getBoolean("error")){
                                    TravelNotice tvl_updated = TravelNotice.fromJSONDB(response.getJSONObject("data"));
                                    // in case of no error, do the additionalDetailsDialog(); method
                                    additionalDetailsDialog(tvl_updated);
                                } else {
                                    // get the error from the DB
                                    String error = response.getJSONObject("message").toString();
                                    // if there is an internal db error that occurred, we handle it
                                    Toast.makeText(getContext(), String.format("An internal server error occurred: %s", error), Toast.LENGTH_LONG).show();
                                    // TODO - Handle what to do in case of an error from the DB. i.e.: tvl was not saved
                                    // Consider handling the case of the user having no internet connection
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                String toastMessage = "JSON Parsing error in onSuccess";
                                Log.e("TravelFragment", String.format("%s, %s", e, toastMessage));
                                Toast.makeText(getContext(), String.format("%s", toastMessage), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.e(TAG, String.format("CODE: %s ERROR: %s", statusCode, errorResponse));
                            Toast.makeText(getContext(), String.format("error 1 %s", errorResponse), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            Log.e(TAG, String.format("CODE: %s ERROR: %s", statusCode, errorResponse));
                            Toast.makeText(getContext(), String.format("error 2 %s", errorResponse), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.e(TAG, String.format("%s", responseString));
                            Toast.makeText(getContext(), String.format("error 3 %s", responseString), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    // Don't move forward if an error occurs
                    e.printStackTrace();
                    String toastMessage = "JSON Parsing error in client.post, BAD";
                    Log.e("TravelFragment", String.format("%s, %s", e.toString(), toastMessage));
                    Toast.makeText(getContext(), String.format("%s", toastMessage), Toast.LENGTH_LONG).show();
                }
            }

        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void additionalDetailsDialog(TravelNotice travelNotice){
        Intent i = new Intent(getContext(), AdditionalDetails.class);
        // Parcels.wrap parselizes the data, to get it from the additionalDetails, do Parcels.unwrap(data.getParcelableExtra("travel_notice"));
        // i.putExtra("travel_notice", Parcels.wrap(travelNotice)); // TODO - FIX Parcel because it causes an error right now
        getContext().startActivity(i);
    }

}
