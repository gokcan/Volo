
package com.example.skylife.parsedb;

/*

||v1.0||
||Author: Gökcan DEĞİRMENCİ||
||For detailed information please visit http://gokcan.degirmenci.me ||
||@Skylifee7||

 */

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ClikedEventFragment extends Fragment implements View.OnClickListener {

    /*
    Initiliazing the params and our view.
     */
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;

    String eventId;
    ParseRelation<ParseObject> relation;
    boolean isPressed = false;
    boolean isPart;
    ParseUser user;
    int count;
    String userid;
    ParseQuery<ParseObject> query2;
    private String theid;



    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ClikedEventFragment() {

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
        This is a really complicated logic of participate event button.
         */

        view= inflater.inflate(R.layout.fragment_event, container, false);

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        theid = bundle.getString("id","d");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("EventData");

        query.getInBackground(theid, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("EventData", "The get the first method request failed!");

                } else {


                    String eventDescription = "Hey";
                    ParseFile userImage;

                    eventDescription = object.getString("eventContext");
                    userImage = object.getParseFile("eventPhoto");
                    String eventImageUrl = userImage.getUrl();
                    eventId = object.getObjectId();

                    user = ParseUser.getCurrentUser();

                    userid = user.getObjectId();


                    query2 = ParseQuery.getQuery("event_user");
                    query2.whereEqualTo("userID", userid);
                    query2.whereEqualTo("eventID", eventId);



                    query2.countInBackground(new CountCallback() {
                        @Override
                        public void done(int countA, ParseException e) {

                            if (e == null) {
                                count = countA;

                                if (count == 0) {

                                    ParseObject obj = new ParseObject("event_user");
                                    obj.put("userID", userid);
                                    obj.put("eventID", eventId);
                                    obj.put("isPart", false);


                                    obj.saveInBackground();

                                }

                                if (count == 1) {

                                    query2.findInBackground(new FindCallback<ParseObject>() {
                                        public void done(List<ParseObject> relationList, ParseException e) {
                                            if (e == null) {

                                                if (relationList.size() > 0) {
                                                    isPart = relationList.get(0).getBoolean("isPart");
                                                    relationList.get(0).saveInBackground();
                                                }

                                                if (isPart) {
                                                    fab.setImageResource(R.drawable.ic_highlight_remove_36dp);
                                                    isPressed = true;

                                                }
                                            } else {
                                                Log.d("score", "Error: " + e.getMessage());
                                            }
                                        }
                                    });


                                }

                            }

                        }
                    });


                    TextView z = (TextView) view.findViewById(R.id.eventDescriptionText);
                    z.setText(eventDescription);

                    ImageView i = (ImageView) view.findViewById((R.id.backdrop_lastevent));
                    Picasso.with(getContext().getApplicationContext()).load(eventImageUrl).
                            placeholder(R.drawable.ic_highlight_remove_36dp)
                            .into(i);


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

            //  ParseUser.getCurrentUser().increment("eventsParticipated", (-1));

            query2 = ParseQuery.getQuery("event_user");
            query2.whereEqualTo("userID", userid);
            query2.whereEqualTo("eventID", eventId);


            query2.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> relationList, ParseException e) {
                    if (e == null) {
                        relationList.get(0).put("isPart", false);
                        relationList.get(0).saveInBackground();

                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });


            fab.setImageResource(R.drawable.ic_assignment_turned_in_24dp);
            Snackbar.make(view, "You disjoined that event!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else {

            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


            // ParseUser.getCurrentUser().increment("eventsParticipated", (1));

            query2 = ParseQuery.getQuery("event_user");
            query2.whereEqualTo("userID", userid);
            query2.whereEqualTo("eventID", eventId);

            query2.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> relationList, ParseException e) {
                    if (e == null) {

                        if (relationList.size() > 0) {
                            relationList.get(0).put("isPart", true);
                            relationList.get(0).saveInBackground();
                        }

                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });

            fab.setImageResource(R.drawable.ic_highlight_remove_36dp);
            Snackbar.make(view, "You joined that event!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();



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

    /*
    public void addEvent(ParseObject o) {


        Take one of the pre-defined image as a Bitmap converts into ByteArray and compresses for lower
        quality. We want to reduce quality because of our database's speed&reliability concerns.
        Quality is now %80 of original one.
         */

        /*
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.peace);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
        byte[] image = stream.toByteArray();


        Event creation logic. IT IS JUST A TEST METHOD to see what we can do.
        ( We can do anything because we are magicians of thee new world ! )

        ParseFile file = new ParseFile("eventPhoto.png", image);
        file.saveInBackground();

        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setWriteAccess(ParseUser.getCurrentUser(), true);

        ParseObject eventObject = new ParseObject("EventData");
        eventObject.put("eventName", "Street Lamp Meeting");
        eventObject.put("eventDescription", "Street Lamp Project is a really helpful project which helps street orphan childs.");
        eventObject.put("availability", true);
        eventObject.put("eventPhoto", file);
        eventObject.put("createdBy", ParseUser.getCurrentUser());
        eventObject.put("isParticipated", false);
        eventObject.setACL(acl);
        eventObject.saveInBackground();

        //TEST 1 2 3

    }
*/
}




