package com.example.skylife.parsedb;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * package com.example.skylife.parsedb;
 * <p/>
 * /*
 * <p/>
 * ||v1.0||
 * ||Author: Gökcan DEĞİRMENCİ||
 * ||For detailed information please visit http://gokcan.degirmenci.me ||
 * ||@Skylifee7||
 */


public class ClickedEventFragment extends Fragment implements View.OnClickListener {

    /*
    Initiliazing the params and our view.
     */
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Random generator = new Random();
    static View view;
    String eventname1="hicbise";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private static String text3;
    protected ParseObject oneobje;
    public String jojo="default";
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

        view = inflater.inflate(R.layout.fragment_clickedevent, container, false);

        parseExecution();


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

