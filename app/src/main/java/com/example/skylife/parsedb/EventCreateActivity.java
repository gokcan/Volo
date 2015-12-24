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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EventCreateActivity extends AppCompatActivity {

    protected FloatingActionButton mCreateEventButton, mRegisterButton;
    protected EditText mEventName, mEventDescription, mEventLocation;
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

/* I will not use EventCreate page as a Activity
*  Instead of using Activity, I will re-write the whole event creation logic with Fragments from zero.
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabImageEventUpload);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Bring up gallery to select a photo
                    startActivityForResult(intent, PICK_PHOTO_CODE);
                }

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mCreateEventButton = (FloatingActionButton) findViewById(R.id.fabCreateEvent);
        mEventName = (EditText)findViewById(R.id.textEventName);
        mEventLocation = (EditText) findViewById(R.id.textEventLocation);
        mEventDescription = (EditText) findViewById(R.id.textEventDescription);

        mCreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eventname = mEventName.getText().toString();
                String eventDescription = mEventDescription.getText().toString();
                String eventLocation = mEventLocation.getText().toString();

                ParseUser user = ParseUser.getCurrentUser();

                Snackbar.make(view, "Hooray! You created an Volo event! " + user.getUsername(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                if ( eventname.isEmpty() || eventDescription.isEmpty() || eventLocation.isEmpty())
                {
                    Snackbar.make(view, "You must fill all the input fields!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                else {

//                    ParseFile file = new ParseFile(eventname + ".png", image);
               //     file.saveInBackground();
                    ParseObject event = new ParseObject("EventData");
                    event.put("eventName", eventname);

                    event.put("eventContext", eventDescription);

                    event.put("eventLocation", eventLocation);

                    event.put("createdBy", ParseUser.getCurrentUser());

                    ParseACL acl = new ParseACL();

                    event.put("availability", true);
               //     event.put("eventPhoto", file);

                    acl.setPublicReadAccess(true);
                    acl.setWriteAccess(user, true);

                    event.setACL(acl);
                    event.saveEventually();

                   //Intent intent = new Intent(EventCreateActivity.this, MainNavActivity.class);
                   // startActivity(intent);
                }

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {

            imageUri = data.getData();

            /*
            ImageView i = (ImageView) myInflatedView.findViewById((R.id.userPhoto));


            Picasso.with(getApplicationContext().getApplicationContext()).load(imageUri).resize(100,100).
                    centerCrop().noFade().placeholder(R.drawable.ic_highlight_remove_24dp).
                    transform(new TransformationCircular()).into(i);
*/
            uploadFiletoDb(data);


        }}



    public void uploadFiletoDb (Intent data) {
        try {

            imageUri = data.getData();
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 15, stream);

            image = stream.toByteArray();


            ParseQuery queryA = new ParseQuery("EventData");
            queryA.orderByDescending("createdAt");

            queryA.getFirstInBackground(new GetCallback() {
                @Override
                public void done(ParseObject object, ParseException e) {


                    ParseFile file = new ParseFile(ParseUser.getCurrentUser().getUsername() + ".png", image);
                    file.saveInBackground();

                    object.put("eventPhoto", image);
                    object.saveInBackground();
                }

                @Override
                public void done(Object o, Throwable throwable) {

                }
            });






        }

        catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "We couldn't locate the file!", Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Photo does not exist anymore!", Toast.LENGTH_LONG).show();

        }
    }





}
