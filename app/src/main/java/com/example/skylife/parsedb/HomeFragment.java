package com.example.skylife.parsedb;

/*

||v1.0||
||Author: Gökcan DEĞİRMENCİ||
||For detailed information please visit https://gokcan.degirmenci.me ||
||@Skylifee7||

 */

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseObject;
import com.parse.ParseUser;


public class HomeFragment extends Fragment implements View.OnClickListener {

    /*
    Initiliazing the params and our view.
     */
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }




    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*
        FloatingAB used for material design guidelines.
        We listen for clicks but in this version user can click many times we need to
        implement opposite button reaction like " Participate " and " Not Participate " state changes.
         */
        view = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        /*Event participation button logic.
        But it needs to be changed with proper one.
        It is a just a placeholder for event participation counter problem.
         */

        ParseObject eventsPrt = new ParseObject("eventsPrt");
        eventsPrt.put("eventsPrt", ParseUser.getCurrentUser());
        ParseUser.getCurrentUser().put("eventsPrt", 1);

        eventsPrt.saveInBackground();

        Snackbar.make(view, "You joined that event!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
