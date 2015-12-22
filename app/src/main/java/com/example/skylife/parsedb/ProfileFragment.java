package com.example.skylife.parsedb;

/*

||v1.0||
||Author: Gökcan DEĞİRMENCİ||
||For detailed information please visit http://gokcan.degirmenci.me ||
||@Skylifee7||

 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;



public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public final static int PICK_PHOTO_CODE = 1046;

    View myInflatedView;
    String stringEventParticipated;
    int eventsParticipated;
    TextView r, t;
    StringBuilder s;
    ParseUser user;
    ParseQuery query2;
    ParseFile file;
    Bitmap bitmap;
    ByteArrayOutputStream stream;
    Uri imageUri;
    byte[] image;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        myInflatedView = inflater.inflate(R.layout.fragment_profile, container, false);

        FloatingActionButton fabUpload = (FloatingActionButton) myInflatedView.findViewById(R.id.fabImageUpload);
        fabUpload.setOnClickListener(this);

        countEventParticipated();



        if(ParseUser.getCurrentUser().getBoolean("isNew")) {
            Toast.makeText(getActivity().getApplicationContext(), "Please upload a profil picture", Toast.LENGTH_LONG).show();
        }


        ParseUser user = ParseUser.getCurrentUser();
        String userName = user.getUsername();


        if (!(user.getBoolean("isNew"))) {

            ParseFile userPhoto = user.getParseFile("profilePhoto");
            String imageUrl = userPhoto.getUrl();
            ImageView i = (ImageView) myInflatedView.findViewById((R.id.userPhoto));

            Picasso.with(getContext().getApplicationContext()).load(imageUrl).
                    placeholder(R.drawable.ic_highlight_remove_36dp).
                    transform(new TransformationCircular()).into(i);

        }

        else {

            ImageView i = (ImageView) myInflatedView.findViewById((R.id.userPhoto));
            Picasso.with(getContext().getApplicationContext()).load(R.drawable.ic_person_24dp).
                    placeholder(R.drawable.ic_highlight_remove_36dp).
                    transform(new TransformationCircular()).into(i);

        }


        // Set the text with its username.
        t = (TextView) myInflatedView.findViewById(R.id.userName);
        t.setText(userName);



        return myInflatedView;

    }

    public void onClick(View v) {

        FloatingActionButton fabUpload = (FloatingActionButton) myInflatedView.findViewById(R.id.fabImageUpload);
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {

            imageUri = data.getData();
            ImageView i = (ImageView) myInflatedView.findViewById((R.id.userPhoto));


            Picasso.with(getContext().getApplicationContext()).load(imageUri).resize(100,100).
                    centerCrop().noFade().placeholder(R.drawable.ic_highlight_remove_24dp).
                    transform(new TransformationCircular()).into(i);

            uploadFiletoDb(data);


        }}

    public void uploadFiletoDb (Intent data) {
        try {

            imageUri = data.getData();
            bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), imageUri);

            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 15, stream);

            image = stream.toByteArray();

            user = ParseUser.getCurrentUser();
            file = new ParseFile(ParseUser.getCurrentUser().getUsername()+".png", image);
            file.saveInBackground();

            user.put("profilePhoto", file);
            user.put("isNew", false);

            user.saveInBackground();

        }

        catch (FileNotFoundException e) {
            Toast.makeText(getActivity().getApplicationContext(), "We couldn't locate the file!", Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            Toast.makeText(getActivity().getApplicationContext(), "Photo does not exist anymore!", Toast.LENGTH_LONG).show();

        }
    }

    public void countEventParticipated() {

        user = ParseUser.getCurrentUser();

        query2= ParseQuery.getQuery("event_user");
        query2.whereEqualTo("userID", user.getObjectId());
        query2.whereEqualTo("isPart", true);


        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> eventList, ParseException e) {
                if (e == null) {

                    user.put("eventsParticipated", eventList.size());
                    eventsParticipated = ParseUser.getCurrentUser().getInt("eventsParticipated");

                    s = new StringBuilder();
                    s.append("");
                    s.append(eventsParticipated);
                    stringEventParticipated = s.toString();

                    r = (TextView) myInflatedView.findViewById((R.id.eventsParticipated));
                    r.setText(stringEventParticipated);


                } else {

                    Toast.makeText(getActivity().getApplicationContext(), "Events participated could not loaded!", Toast.LENGTH_LONG).show();


                }
            }
        });

        ParseUser.getCurrentUser().saveInBackground();
    }



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
