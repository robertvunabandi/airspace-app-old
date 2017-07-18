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

import com.example.mandaleeyp.teamrawrapp.R;
import com.example.mandaleeyp.teamrawrapp.UpcomingTripAdapter;
import com.example.mandaleeyp.teamrawrapp.models.TravelNotice;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TripsFragment extends Fragment {

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
        if (getArguments() != null) {
        }
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
                TravelNotice travelNotice = TravelNotice.fromJSONDB(travelNoticeList.getJSONObject(i));
                mTrips.add(travelNotice);
                upcomingTripAdapter.notifyItemInserted(mTrips.size() - 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
