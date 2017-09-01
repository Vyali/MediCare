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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EhRecord.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EhRecord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EhRecord extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    RecyclerView mehrRecycler;
    List<EhrDetails> ehrList;
    EhrAdapter myEhrAdapter;
    EhrDetails ehr;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    // database reference
    private DatabaseReference mRefrence;
    private OnFragmentInteractionListener mListener;

    public EhRecord() {
        // Required empty public constructor
    }

    // adding value listener for value change


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EhRecord.
     */
    // TODO: Rename and change types and number of parameters
    public static EhRecord newInstance(String param1, String param2) {
        EhRecord fragment = new EhRecord();
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
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_eh_record, container, false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        ehrList = new ArrayList<>();


        String mail = mUser.getEmail();
        String m = mail.substring(0, mail.length() - 10);
        Log.d("Kandv", "mail is " + m);

        mRefrence = FirebaseDatabase.getInstance().getReference().child("medicare-850d8").child("path0logy").child(m);


        mehrRecycler = (RecyclerView) view.findViewById(R.id.record);


        mRefresh();


        Log.d("kandv", "above the adapter");
//          prepareEhrList("abc","asdf");
        myEhrAdapter = new EhrAdapter(getContext(), ehrList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mehrRecycler.setLayoutManager(layoutManager);
        mehrRecycler.setAdapter(myEhrAdapter);
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

    void prepareEhrList(String kname, String vname) {
        ehr = new EhrDetails(kname, vname);
        Log.d("kandv", "k val" + kname + "vnam" + vname);
        ehrList.add(ehr);
        Log.d("kandv", "ehr" + ehrList);
        // ehr = new EhrDetails("sdfd","fsdf");
        //ehrList.add(ehr);
        //ehr = new EhrDetails("asdf","fasdf");
        // ehrList.add(ehr);
    }

    void mRefresh() {


        Log.d("kandv", "in refresh ");
        mRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String k, value;
                Log.d("snaphot", "snap value " + dataSnapshot.getValue());
                ArrayList<String> keyList = (ArrayList<String>) dataSnapshot.getValue();
                Log.d("keylist", "k list is " + keyList.get(3) + " 1 " + keyList.get(4));
                for (int i = 1; i <= keyList.size() - 1; i++) {

                    k = keyList.get(i);
                    i = i + 1;
                    value = keyList.get(i);
                    prepareEhrList(k, value);
                }
                myEhrAdapter.notifyDataSetChanged();
                // prepareDocList(name,special,yoexp,rating,degree,hadd,hname);
                //Log.d("KEY3", (String) d.get("special"));
                //Log.d("KEY",name+" ghg");
                //Log.d("KEY",key.getKey()+" ghg");
                //Log.d("KEY", String.valueOf(d)+" gdg");
                //for(Object k : d.entrySet()){
                //Object i  = k.getClass();
                // Log.d("KEY2",k+" df");

                // }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }


    public interface EhRecordListener {

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


