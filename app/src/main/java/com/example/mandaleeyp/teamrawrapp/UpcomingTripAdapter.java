package com.example.mandaleeyp.teamrawrapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mandaleeyp.teamrawrapp.models.TravelNotice;

import java.util.List;

public class UpcomingTripAdapter extends RecyclerView.Adapter<UpcomingTripAdapter.ViewHolder> {

    // declare variables
    private List<TravelNotice> mTrips;
    Context context;

    public UpcomingTripAdapter(List<TravelNotice> trips) {
        mTrips = trips;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tripsView = inflater.inflate(R.layout.item_upcoming_trip, parent, false);
        ViewHolder viewHolder = new ViewHolder(tripsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UpcomingTripAdapter.ViewHolder holder, int position) {
        TravelNotice trips = mTrips.get(position);
        holder.tv_from.setText(trips.dep_city);
        holder.tv_to.setText(trips.arr_city);
        holder.tv_dateFrom.setText(trips.dep_month + "/" + trips.dep_day + "/" + trips.dep_year);
        holder.tv_dateTo.setText(trips.arr_month + "/" + trips.arr_day + "/" + trips.arr_year);
        holder.tv_fromTime.setText(trips.dep_hour + ":" + trips.dep_min);
        holder.tv_toTime.setText(trips.arr_hour + ":" + trips.arr_min);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout relative_layout;
        public TextView tv_from;
        public TextView tv_to;
        public TextView tv_arrow;
        public TextView tv_fromTime;
        public TextView tv_toTime;
        public TextView tv_dateFrom;
        public TextView tv_dateTo;
        public TextView tv_requestsTitle;
        public TextView tv_pendingTitle;
        public TextView tv_requestsNo;
        public TextView tv_pendingNo;
        public Button bt_edit;
        public Button bt_delete;
        public Button bt_detail;


        public ViewHolder(View itemView) {
            super(itemView);

            relative_layout = (RelativeLayout) itemView.findViewById(R.id.relative_layout);
            tv_from = (TextView) itemView.findViewById(R.id.tv_from);
            tv_to = (TextView) itemView.findViewById(R.id.tv_to);
            tv_arrow = (TextView) itemView.findViewById(R.id.tv_arrow);
            tv_fromTime = (TextView) itemView.findViewById(R.id.tv_fromTime);
            tv_toTime = (TextView) itemView.findViewById(R.id.tv_toTime);
            tv_dateFrom = (TextView) itemView.findViewById(R.id.tv_dateFrom);
            tv_dateTo = (TextView) itemView.findViewById(R.id.tv_dateTo);
            tv_requestsTitle = (TextView) itemView.findViewById(R.id.tv_requestsTitle);
            tv_pendingTitle = (TextView) itemView.findViewById(R.id.tv_pendingTitle);
            tv_requestsNo = (TextView) itemView.findViewById(R.id.tv_requestNo);
            tv_pendingNo = (TextView) itemView.findViewById(R.id.tv_pendingNo);
            bt_edit = (Button) itemView.findViewById(R.id.bt_edit);
            bt_delete = (Button) itemView.findViewById(R.id.bt_delete);
            bt_detail = (Button) itemView.findViewById(R.id.bt_detail);
        }
    }

    public void clear() {
        mTrips.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<TravelNotice> list) {
        mTrips.addAll(list);
        notifyDataSetChanged();
    }
}


