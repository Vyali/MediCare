package com.example.ayush.medicare;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AmbulanceAdapter extends RecyclerView.Adapter<AmbulanceAdapter.MyViewHolder> {
    Context mContext;
    List<AmbulanceDetail> ambulanceList;

    public AmbulanceAdapter(Context mContext, List<AmbulanceDetail> ambulanceList) {
        this.mContext = mContext;
        this.ambulanceList = ambulanceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ambulance_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AmbulanceDetail ambulanceDetail = ambulanceList.get(position);
        final String num = ambulanceDetail.getNumber();
        holder.name.setText(ambulanceDetail.getName());

        holder.address.setText(ambulanceDetail.getAddress());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("button", "button clicked");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + num));
                mContext.startActivity(callIntent);
                if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(mContext, "allow call and contact permission for MediCare app in settings", Toast.LENGTH_SHORT).show();
                    return;

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return ambulanceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, address;
        ImageView call;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.amb_sr_name);
            address = (TextView) itemView.findViewById(R.id.amb_address);
            call = (ImageView) itemView.findViewById(R.id.call);


        }
    }
}


