package com.example.mandaleeyp.teamrawrapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mandaleeyp.teamrawrapp.models.Msg;

import java.util.List;

/**
 * Created by rdicker on 7/14/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Msg> mMessages;
    private Context mContext;

    public MessageAdapter(Context context, List<Msg> messages) {
        mContext = context;
        mMessages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_msg, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageOther;
        ImageView imageMe;
        TextView tvMsg;
        Button btnDenyRq;
        Button btnViewRq;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMsg = (TextView)itemView.findViewById(R.id.tvMsg);
            btnDenyRq = (Button) itemView.findViewById(R.id.btnDenyRq);
            btnViewRq = (Button) itemView.findViewById(R.id.btnViewRq);
        }







    }

}
