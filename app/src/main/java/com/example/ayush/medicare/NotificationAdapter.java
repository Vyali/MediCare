package com.example.ayush.medicare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vyali on 25/8/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    Context c;
    List<NotificationDetails> notificationDetailsList;


    public NotificationAdapter(Context c, List<NotificationDetails> notificationDetailsList) {
        this.c = c;
        this.notificationDetailsList = notificationDetailsList;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);


        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        NotificationDetails notificationDetails = notificationDetailsList.get(position);
        holder.body.setText(notificationDetails.mgetBody());
        // holder.body.setText(notificationDetails.mgetHeading());

    }

    @Override
    public int getItemCount() {
        return notificationDetailsList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView heading, body;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            //  heading = (TextView) itemView.findViewById(R.id.n_heading);
            body = (TextView) itemView.findViewById(R.id.body);

        }

    }
}


