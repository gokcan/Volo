package com.example.skylife.parsedb;

/*

||v1.0||
||Author: Gökcan DEĞİRMENCİ||
||For detailed information please visit http://gokcan.degirmenci.me ||
||@Skylifee7||

 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class EventCreateActivity extends AppCompatActivity {

    protected Button mLogintoRegister, mRegisterButton;
    protected EditText mUsername, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
    }

    /* public void addEvent(ParseObject o) {
        Take one of the pre-defined image as a Bitmap converts into ByteArray and compresses for lower
        quality. We want to reduce quality because of our database's speed&reliability concerns.
        Quality is now %80 of original one.
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
    }*/

}
