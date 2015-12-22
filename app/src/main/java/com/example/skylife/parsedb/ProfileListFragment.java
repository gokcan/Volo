package com.example.skylife.parsedb;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.List;


public class ProfileListFragment extends ListFragment implements View.OnClickListener, Serializable {

    /*
    Initiliazing the params and our view.
     */
    TextView text1;
    String eventName,eventContext;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    protected List<ParseObject> mParticipatedEvents;
    private View view;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileListFragment() {
        // Required empty public constructor
    }


    public static ProfileListFragment newInstance(String param1, String param2) {
        ProfileListFragment fragment = new ProfileListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("event_user");
        query.whereEqualTo("userID", ParseUser.getCurrentUser().getObjectId() );
        query.whereEqualTo("isPart", true);
                query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> events, ParseException e) {
                if (e == null) {
                    mParticipatedEvents= events;



                    ProfileListAdapter adapter = new ProfileListAdapter(getListView().getContext(), mParticipatedEvents);
                    setListAdapter(adapter);



                } else {

                }
            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*
        FloatingAB used for material design guidelines.
        We listen for clicks but in this version user can click many times we need to
        implement opposite button reaction like " Participate " and " Not Participate " state changes.
         */


        view = inflater.inflate(R.layout.fragment_profile_list, container, false);

        return view;

    }

    @Override
    public void onClick(View v) {
        /*
         */

        ParseObject eventsPrt = new ParseObject("eventsPrt");

        eventsPrt.put("eventsPrt", ParseUser.getCurrentUser());
        ParseUser.getCurrentUser().put("eventsPrt", 1);

        eventsPrt.saveInBackground();

        Snackbar.make(view, "You joined that event!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }
    public void setOnItemClickListener(){

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void onListItemClick(ListView l,View v, int position, long id){
        super.onListItemClick(l, v, position, id);


        ParseObject eventObject = mParticipatedEvents.get(position);

        String objectId = eventObject.getObjectId();
        String eventName = eventObject.getString("eventName");
        String eventCon = eventObject.getString("eventContext");

        ClikedEventFragment fragment = new ClikedEventFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", objectId);


        bundle.putString("Context", eventCon);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();


        transaction.replace(R.id.navcontent, fragment, "EVENT_FRAGMENT");
        transaction.addToBackStack(null);

        transaction.commit();
    }

}