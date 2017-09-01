package com.example.ayush.medicare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoctorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    List<DoctorsDetails> mDoctorList;
    RecyclerView docRecyclerView;
    List<DoctorsDetails> mydocList;
    DoctorAdapter mydocAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference mRefrence;
    private OnFragmentInteractionListener mListener;

    public DoctorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorFragment newInstance(String param1, String param2) {
        DoctorFragment fragment = new DoctorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_doctor, container, false);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRefrence = FirebaseDatabase.getInstance().getReference().child("medicare-850d8").child("doctor");
        mDoctorList = new ArrayList<>();

        docRecyclerView = (RecyclerView) view.findViewById(R.id.doc_recycler);
        mydocList = new ArrayList<>();

        try {
            mRefrence.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    Map<String, Object> keylist = (Map<String, Object>) dataSnapshot.getValue();
                    Log.d("SNAPSHOT", "value is " + keylist);
                    for (Map.Entry<String, Object> key : keylist.entrySet()) {
                        Map d = (Map) key.getValue();
                        String name = (String) d.get("username");
                        String special = (String) d.get("specilization");
                        String yoexp = (String) d.get("Experience");
                        String rating = (String) d.get("rating");
                        String degree = (String) d.get("degree");
                        String hadd = (String) d.get("hospitaaddress");
                        String hname = (String) d.get("hospital");
                        prepareDocList(name, special, yoexp, rating, degree, hadd, hname);

                        //Log.d("KEY3","size is "+d.size());
                        //Log.d("KEY",name+" ghg");
                        //Log.d("KEY",key.getKey()+" ghg");
                        //Log.d("KEY", String.valueOf(d)+" gdg");
                  /*  for(Object k : d.entrySet()){
                        //Object i  = k.getClass();
                        Log.d("KEY2",k+" df");

                    }*/

                    }
                    mydocAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

        }

        mydocAdapter = new DoctorAdapter(getActivity(), mydocList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        docRecyclerView.setLayoutManager(layoutManager);
        docRecyclerView.setAdapter(mydocAdapter);


        return view;
    }

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

    void prepareDocList(String name, String special, String yoexp, String rating, String degree, String hadd, String hname) {
        DoctorsDetails doctorsDetails = new DoctorsDetails(name, degree, hname, hadd, rating, yoexp, special);
        mydocList.add(doctorsDetails);

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
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
