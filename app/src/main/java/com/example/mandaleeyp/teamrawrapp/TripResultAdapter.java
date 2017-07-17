package com.example.mandaleeyp.teamrawrapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mandaleeyp.teamrawrapp.models.TravelNotice;

import java.util.List;

/**
 * Created by rdicker on 7/17/17.
 */

public class TripResultAdapter extends RecyclerView.Adapter<TripResultAdapter.ViewHolder> {



    private List<TravelNotice> trips;
    Context context;

    public TripResultAdapter(List<TravelNotice> theTrips) {
        trips = theTrips;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_trip_result, parent, false);
        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(tweetView) {
        };
        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(TripResultAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
