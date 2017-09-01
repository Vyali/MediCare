package com.example.ayush.medicare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Ambulance.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Ambulance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ambulance extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView myRecyclerView;
    AmbulanceAdapter myAmbulanceAdapter;
    List<AmbulanceDetail> myAmbulanceList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Ambulance() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ambulance.
     */
    // TODO: Rename and change types and number of parameters
    public static Ambulance newInstance(String param1, String param2) {
        Ambulance fragment = new Ambulance();
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

        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_ambulance, container, false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.ambulance_view);
        myAmbulanceList = new ArrayList<>();
        prepareAmbulanceList();
        myAmbulanceAdapter = new AmbulanceAdapter(getActivity(), myAmbulanceList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setAdapter(myAmbulanceAdapter);
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

    private void prepareAmbulanceList() {
        AmbulanceDetail a = new AmbulanceDetail("Name of the provider", "address", "8423987578");
        myAmbulanceList.add(a);
        a = new AmbulanceDetail("on clicking on the", "call button", "8843451256");
        myAmbulanceList.add(a);
        a = new AmbulanceDetail("user can directly ", "make call to the", "8428526578");
        myAmbulanceList.add(a);
        a = new AmbulanceDetail("ambulance services", "as of now", "9636963545");
        myAmbulanceList.add(a);
        a = new AmbulanceDetail("its all", "dummy data", "8843499855");
        myAmbulanceList.add(a);


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
