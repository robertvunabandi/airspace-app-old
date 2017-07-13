package com.example.mandaleeyp.teamrawrapp.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import com.example.mandaleeyp.teamrawrapp.models.Flight;
import com.example.mandaleeyp.teamrawrapp.R;
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

    // base URL for API
    public final static String API_BASE_URL = "https://api.flightstats.com/flex/schedules/rest";
    // parameter name for API key
    public final static String APP_KEY_PARAM = "appKey";
    public final static String APP_ID_PARAM = "appId";
    // our database url
    public final static String DB_HEROKU_URL = "http://mysterious-headland-54722.herokuapp.com";
    public final static String DB_LOCAL_URL = "http://172.22.9.199:3000";
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
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
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
                flightMonth = monthOfYear;
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
                Log.e("TravelFragment", url);
                // set request parameters
                RequestParams params = new RequestParams();
                params.put(APP_KEY_PARAM, getString(R.string.api_key)); // API key, always required
                params.put(APP_ID_PARAM, getString(R.string.app_id)); // AppId


                //execute a GET request expecting a JSON object response
                client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            flight = Flight.fromJSON(response);
                            processResponse(response);
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), String.format("WOW"), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getContext(), String.format("error 1 %s", errorResponse, toString()), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Toast.makeText(getContext(), String.format("error 2 %s", errorResponse, toString()), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(getContext(), String.format("error 3 %s", responseString), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return v;
    }

    public void processResponse(JSONObject response) {
//        Toast.makeText(getContext(), String.format("%s", response.toString()), Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(flight.getAirlineName() + "\nFlight " + flight.getAirlineCode() +  " " + flight.getFlightNumber() + "\n"
                            + flight.getDepartureAirportCode() + " to " + flight.getArrivalAirportCode()
                            + "\nDeparting on " + flight.getDepartFullDate() + " at " + flight.getDepartureTime())
                .setTitle(R.string.dialog_title);

        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                // Send response to database
                RequestParams params = new RequestParams();
                // TODO - PUT PAREMETERS
                client.post(DB_URLS[1] + "/travel_notice_add", params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // TODO - Potentially do some stuffs with the db response
                        additionalDetailsDialog();
                    }
                });
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

    public void additionalDetailsDialog(){

    }

}

/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 *
public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
}

//        btnDate = (ImageButton) v.findViewById(R.id.ib_date);
//
//        btnDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getContext(), com.example.mandaleeyp.teamrawrapp.DatePicker.class);
//                i.putExtra("placeholder", "To where");
//                startActivityForResult(i, TO_CODE);
//            }
//        });
//

//    int TO_CODE = 0;
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

//    private OnFragmentInteractionListener mListener;

*/
