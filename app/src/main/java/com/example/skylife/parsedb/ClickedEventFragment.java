package com.example.skylife.parsedb;

/*

||v1.0||
||Author: Berke Soysal ||
||For detailed information please dont visit anywhere ||
||Button logic @Skylifee7||

 */

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;


public class ClickedEventFragment extends Fragment implements View.OnClickListener {

    /*
    Initiliazing the params and our view.
     */
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String theid;
    View view;

    String eventId;
    ParseRelation<ParseObject> relation;
    boolean isPressed = false;
    boolean isJoined;
    ParseQuery query2;
    ParseUser user;


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ClickedEventFragment() {
        // Required empty public constructor
    }


    public static EventFragment newInstance(String param1, String param2) {
        EventFragment fragment = new EventFragment();
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

        view= inflater.inflate(R.layout.fragment_clickedevent, container, false);

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);


        Bundle bundle = this.getArguments();
        theid = bundle.getString("id","d");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("EventData");
        query.getInBackground(theid, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {


                    String eventName = "deneme ";
                    eventName = object.getString("eventName");
                    TextView text1 = (TextView) view.findViewById(R.id.eventDescriptionText);
                    text1.setText(eventName);
                    String eventCont = "";
                    eventCont = object.getString("eventContext");
                    ImageView imageHP;
                    imageHP = (ImageView) view.findViewById(R.id.backdrop_event);
                    Picasso.with(getContext().
                            getApplicationContext()).
                            load(object.getParseFile("eventPhoto").
                                    getUrl()).noFade().
                            into(imageHP);

                    user = ParseUser.getCurrentUser();

                    relation = user.getRelation("events");
                    relation.add(object);

                    query2 = relation.getQuery();

                    query2.getInBackground(theid, new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {

                                isJoined = object.getBoolean("isJoined");

                                if (isJoined) {
                                    fab.setImageResource(R.drawable.ic_highlight_remove_36dp);
                                    isPressed = true;
                                }
                                else {

                                }


                            } else {
                                // something went wrong
                            }
                        }
                    });


                } else {


                }
            }
        });

        return view;

    }



    public void onClick(View v) {
        /*Event participation button logic.
        But it needs to be changed with proper one.
        It is a just a placeholder for event participation counter problem.
         */

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        if (isPressed) {

            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            ParseUser.getCurrentUser().increment("eventsParticipated", (-1));

            query2.getInBackground(theid, new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {

                        object.put("isJoined", false);
                        object.saveInBackground();

                        Toast.makeText(getActivity().getApplicationContext(), " Armo" + isJoined + "orospudur ", Toast.LENGTH_LONG).show();
                    } else {
                        // something went wrong
                    }
                }
            });


            fab.setImageResource(R.drawable.ic_assignment_turned_in_24dp);
            Snackbar.make(view, "You disjoined that event!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
        else {

            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

            fab.setImageResource(R.drawable.ic_highlight_remove_36dp);
            Snackbar.make(view, "You joined that event!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            ParseUser.getCurrentUser().increment("eventsParticipated", (1));

            query2.getInBackground(theid, new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {

                        object.put("isJoined", true);
                        object.saveInBackground();



                    } else {
                        // something went wrong
                    }
                }
            });



        }


        isPressed= !isPressed;

        ParseUser.getCurrentUser().saveInBackground();

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }




    public void parseExecution(){


        Bundle bundle = this.getArguments();
        String theid = bundle.getString("id","d");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("EventData");
        query.getInBackground(theid, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {



                    String eventName="deneme ";
                    eventName=object.getString("eventName");
                    TextView text1 = (TextView) view.findViewById(R.id.eventDescriptionText);
                    text1.setText(eventName);
                    String eventCont ="";
                    eventCont=object.getString("eventContext");
                    ImageView imageHP;
                    imageHP = (ImageView) view.findViewById(R.id.backdrop_event);
                    Picasso.with(getContext().
                            getApplicationContext()).
                            load(object.getParseFile("eventPhoto").
                                    getUrl()).noFade().
                            into(imageHP);

                } else {


                }
            }
        });

    }
}


