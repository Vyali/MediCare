package com.example.ayush.medicare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by vyali on 26/8/17.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorsViewHolder> {

    List<DoctorsDetails> mydocList;
    Context mycontext;

    DoctorAdapter(Context mycontext, List<DoctorsDetails> mydocList) {
        this.mycontext = mycontext;
        this.mydocList = mydocList;
    }

    @Override
    public DoctorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_card, parent, false);
        return new DoctorsViewHolder(view);


    }

    @Override
    public void onBindViewHolder(DoctorsViewHolder holder, int position) {
        final DoctorsDetails doctorsDetails = mydocList.get(position);
        holder.dname.setText(doctorsDetails.getDocName());
        holder.hospitalAdress.setText(doctorsDetails.getDochaddress());
        holder.degree.setText(doctorsDetails.getDocdegree() + "/" + doctorsDetails.getDocSepcial());
        holder.hospitalName.setText(doctorsDetails.getDochname());
        holder.rating.setText(doctorsDetails.getDocrating());
        holder.yearOfexp.setText(doctorsDetails.getDocyoexp());
        holder.makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mycontext, "Your Appointment request for " + doctorsDetails.getDocName() + " has been made", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mydocList.size();
    }

    public class DoctorsViewHolder extends RecyclerView.ViewHolder {
        TextView dname, degree, hospitalName, hospitalAdress, rating, yearOfexp;
        Button makeAppointment;

        public DoctorsViewHolder(View itemView) {
            super(itemView);
            dname = (TextView) itemView.findViewById(R.id.dname);
            degree = (TextView) itemView.findViewById(R.id.degree);
            hospitalName = (TextView) itemView.findViewById(R.id.hospital_name);
            hospitalAdress = (TextView) itemView.findViewById(R.id.hos_adderss);
            rating = (TextView) itemView.findViewById(R.id.rating);
            yearOfexp = (TextView) itemView.findViewById(R.id.yoexp);
            makeAppointment = (Button) itemView.findViewById(R.id.make_appointment);
        }
    }


}
