package com.example.mandaleeyp.teamrawrapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
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

        holder.tv_from_itr.setText(trip.dep_iata);
        holder.tv_to_itr.setText(trip.arr_iata);

        holder.tv_dateFrom_itr.setText(trip.dep_month + "/" + trip.dep_day + "/" + String.valueOf(trip.dep_year).substring(2));
        holder.tv_dateTo_itr.setText(trip.arr_month + "/" + trip.arr_day + "/" + String.valueOf(trip.arr_year).substring(2));

        holder.tv_fromTime_itr.setText(trip.dep_hour + ":" + trip.dep_min);
        holder.tv_toTime_itr.setText(trip.arr_hour + ":" + trip.arr_min);

        holder.cb_envelope_itr.setChecked(trip.item_envelopes);
        holder.cb_largeBox_itr.setChecked(trip.item_lgbox);
        holder.cb_smallBox_itr.setChecked(trip.item_smbox);
        holder.cb_clothing_itr.setChecked(trip.item_clothing);
        holder.cb_other_itr.setChecked(trip.item_other);

        holder.cb_envelope_itr.setEnabled(false);
        holder.cb_largeBox_itr.setEnabled(false);
        holder.cb_smallBox_itr.setEnabled(false);
        holder.cb_clothing_itr.setEnabled(false);
        holder.cb_other_itr.setEnabled(false);

        holder.tv_dropoff.setText(trip.drop_off_flexibility);
        holder.tv_pickup.setText(trip.pick_up_flexibility);

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
        return trips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tv_from_itr;
        public TextView tv_arrow_itr;
        public TextView tv_to_itr;
        public TextView tv_fromTime_itr;
        public TextView tv_toTime_itr;
        public TextView tv_dateFrom_itr;
        public TextView tv_dateTo_itr;

        final ImageView ivToggleInfo;
        final ExpandableRelativeLayout erl_info;

        public RelativeLayout rlCheckBoxes;
        public CheckBox cb_envelope_itr;
        public CheckBox cb_largeBox_itr;
        public CheckBox cb_smallBox_itr;
        public CheckBox cb_clothing_itr;
        public CheckBox cb_other_itr;

        public TextView tv_dropoff;
        public TextView tv_pickup;

        public Button btn_request_itr;
        public Button btn_askQ_itr;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName_itr);
            tv_arrow_itr = (TextView) itemView.findViewById(R.id.tv_arrow_itr);
            tv_from_itr = (TextView) itemView.findViewById(R.id.tv_from_itr);
            tv_to_itr = (TextView) itemView.findViewById(R.id.tv_to_itr);
            tv_fromTime_itr = (TextView) itemView.findViewById(R.id.tv_fromTime_itr);
            tv_toTime_itr = (TextView) itemView.findViewById(R.id.tv_toTime_itr);
            tv_dateFrom_itr = (TextView) itemView.findViewById(R.id.tv_dateFrom_itr);
            tv_dateTo_itr = (TextView) itemView.findViewById(R.id.tv_dateTo_itr);

            ivToggleInfo = (ImageView) itemView.findViewById(R.id.iv_toggleInfo);
            erl_info = (ExpandableRelativeLayout) itemView.findViewById(R.id.erl_info);

            rlCheckBoxes = (RelativeLayout) itemView.findViewById(R.id.rlCheckBoxes);
            cb_envelope_itr = (CheckBox) itemView.findViewById(R.id.cb_envelope_itr);
            cb_largeBox_itr = (CheckBox) itemView.findViewById(R.id.cb_largeBox_itr);
            cb_smallBox_itr = (CheckBox) itemView.findViewById(R.id.cb_smallBox_itr);
            cb_clothing_itr = (CheckBox) itemView.findViewById(R.id.cb_clothing_itr);
            cb_other_itr = (CheckBox) itemView.findViewById(R.id.cb_other_itr);

            tv_dropoff = (TextView) itemView.findViewById(R.id.tv_dropoff_itr);
            tv_pickup = (TextView) itemView.findViewById(R.id.tv_pickup_itr);

            btn_request_itr = (Button) itemView.findViewById(R.id.btn_request_itr);
            btn_askQ_itr = (Button) itemView.findViewById(R.id.btn_askQ_itr);

            btn_request_itr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    // Intent i = new Intent(context, RequestActivity.class);
                    // context.startActivity(i);
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you a")
                            .setTitle(R.string.dialog_title);

                    builder.setPositiveButton("sender", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            Intent i = new Intent(context, SenderFormActivity.class);
                            i.putExtra("trip_id", trips.get(pos).id);
                            i.putExtra("sender_id", "596d0b5626bffc280b32187e");
                            context.startActivity(i);
                        }
                    });
                    builder.setNegativeButton("receiver", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            Intent i = new Intent(context, RecipientFormActivity.class);
                            i.putExtra("trip_id", trips.get(pos).id);
                            i.putExtra("recipient_id", "596d0b5626bffc280b32187e");
                            context.startActivity(i);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });

            btn_askQ_itr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    // Intent i = new Intent(context, NewConversationActivity.class);
                    // context.startActivity(i);
                }
            });
        }
    }

    public void clear() {
        trips.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<TravelNotice> list) {
        trips.addAll(list);
        notifyDataSetChanged();
    }
}
