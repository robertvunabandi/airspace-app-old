package com.example.mandaleeyp.teamrawrapp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mandaleeyp.teamrawrapp.models.TravelNotice;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.List;

/**
 * Created by rdicker on 7/17/17.
 */

public class TripResultAdapter extends RecyclerView.Adapter<TripResultAdapter.ViewHolder> {

    // declare variables
    private List<TravelNotice> trips;
    Context context;

    public TripResultAdapter(List<TravelNotice> theTrips) {
        trips = theTrips;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tripView = inflater.inflate(R.layout.item_trip_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(tripView) {
        };
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(final TripResultAdapter.ViewHolder holder, int position) {
        TravelNotice trip = trips.get(position);


        holder.tvName.setText(trip.tuid + "'s Trip:");
        holder.tvFlightDate.setText("Departure:" + trip.dep_month + "/" + trip.dep_day + "/" + trip.dep_year
                    + "\n");
        holder.tvAirportCodes.setText(trip.dep_iata + " ➝ " + trip.arr_iata);
        holder.tvFlightTime.setText("");




        holder.ivToggleInfo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                // Toggle the expandable view
                holder.erl_info.toggle();

                // TODO - Change the drawable to either expanded or collapsed
                // TODO - Add filters in XML
            }
        });

    }



    @Override
    public int getItemCount() {
        return 0;
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvAirportCodes;
        public TextView tvFlightTime;
        public TextView tvFlightDate;

        final ImageView ivToggleInfo;
        final ExpandableRelativeLayout erl_info;

        public RelativeLayout rlCheckBoxes;
        public CheckBox cb_envelope_itr;
        public CheckBox cb_largeBox_itr;
        public CheckBox cb_smallBox_itr;
        public CheckBox cb_clothing_itr;
        public CheckBox cb_other_itr;

        public Button btn_request_itr;
        public Button btn_askQ_itr;




        public ViewHolder(final View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName_itr);
            tvAirportCodes = (TextView) itemView.findViewById(R.id.tvAirportCodes_itr);
            tvFlightTime = (TextView) itemView.findViewById(R.id.tvFlightTime_itr);
            tvFlightDate = (TextView) itemView.findViewById(R.id.tvFlightDate_itr);

            ivToggleInfo = (ImageView) itemView.findViewById(R.id.iv_toggleInfo);
            erl_info = (ExpandableRelativeLayout) itemView.findViewById(R.id.erl_info);

            rlCheckBoxes = (RelativeLayout) itemView.findViewById(R.id.rlCheckBoxes);
            cb_envelope_itr = (CheckBox) itemView.findViewById(R.id.cb_envelope_itr);
            cb_largeBox_itr = (CheckBox) itemView.findViewById(R.id.cb_largeBox_itr);
            cb_smallBox_itr = (CheckBox) itemView.findViewById(R.id.cb_smallBox_itr);
            cb_clothing_itr = (CheckBox) itemView.findViewById(R.id.cb_clothing_itr);
            cb_other_itr = (CheckBox) itemView.findViewById(R.id.cb_other_itr);

            btn_request_itr = (Button) itemView.findViewById(R.id.btn_request_itr);
            btn_askQ_itr = (Button) itemView.findViewById(R.id.btn_askQ_itr);






        }
    }


}
