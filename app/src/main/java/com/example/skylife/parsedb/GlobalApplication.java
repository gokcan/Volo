package com.example.skylife.parsedb;
/*

||v1.0||
||Author: Gökcan DEĞİRMENCİ||
||For detailed information please visit http://gokcan.degirmenci.me ||
||@Skylifee7||

 */

import android.app.Application;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "wZJ1Nx8n1wxsmED2LQQ7C4xtkbvSBlb26qzP4LPt", "5vEXptj6DwN5c2O9aqOCzAfnEKaPQfxJubB7yLpu");
        ParseInstallation.getCurrentInstallation().saveInBackground();


        ParseUser user = ParseUser.getCurrentUser();

        ParseRelation relation = user.getRelation("events");

        ParseQuery query2 = relation.getQuery();
        query2.whereEqualTo("isJoined", true);

        query2.findInBackground(new FindCallback<ParseObject>() {
        public void done(List<ParseObject> scoreList, ParseException e) {
        if (e == null) {
             ParseUser.getCurrentUser().put("eventsParticipated", scoreList.size());
        } else {

        }
    }
});


    }

}