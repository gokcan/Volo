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
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public final static int PICK_PHOTO_CODE = 1046;

    View myInflatedView;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
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

            countEventParticipated();



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myInflatedView = inflater.inflate(R.layout.fragment_profile, container, false);

        FloatingActionButton fabUpload = (FloatingActionButton) myInflatedView.findViewById(R.id.fabImageUpload);
        fabUpload.setOnClickListener(this);

        if(ParseUser.getCurrentUser().getBoolean("isNew")) {
            Toast.makeText(getActivity().getApplicationContext(), "Please upload a profil picture", Toast.LENGTH_LONG).show();
        }

        ParseUser user = ParseUser.getCurrentUser();
        String email = user.getEmail();
        String userName = user.getUsername();
        int eventsParticipated = user.getInt("eventsParticipated");


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



        StringBuilder s = new StringBuilder();
        s.append("");
        s.append(eventsParticipated);
        String stringEventParticipated = s.toString();

        // Set the text with its username.
        TextView t = (TextView) myInflatedView.findViewById(R.id.userName);
        t.setText(userName);

        TextView e = (TextView) myInflatedView.findViewById((R.id.eventsParticipated));
        e.setText(stringEventParticipated);



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

            Uri imageUri = data.getData();
            ImageView i = (ImageView) myInflatedView.findViewById((R.id.userPhoto));


            Picasso.with(getContext().getApplicationContext()).load(imageUri).resize(100,100).
                    centerCrop().noFade().placeholder(R.drawable.ic_highlight_remove_24dp).
                    transform(new TransformationCircular()).into(i);

            uploadFiletoDb(data);


        }}

    public void uploadFiletoDb (Intent data) {
        try {

            Uri imageUri = data.getData();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), imageUri);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 15, stream);

            byte[] image = stream.toByteArray();

            ParseUser user = ParseUser.getCurrentUser();
            ParseFile file = new ParseFile(ParseUser.getCurrentUser().getUsername()+".png", image);
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

        ParseUser user = ParseUser.getCurrentUser();

        ParseRelation relation = user.getRelation("events");

        ParseQuery query2 = relation.getQuery();
        query2.whereEqualTo("isJoined", true);

        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> eventList, ParseException e) {
                if (e == null) {
                    ParseUser.getCurrentUser().put("eventsParticipated", eventList.size());
                } else {

                }
            }
        });
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
