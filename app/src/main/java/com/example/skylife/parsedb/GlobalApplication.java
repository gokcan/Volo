package com.example.skylife.parsedb;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "wZJ1Nx8n1wxsmED2LQQ7C4xtkbvSBlb26qzP4LPt", "5vEXptj6DwN5c2O9aqOCzAfnEKaPQfxJubB7yLpu");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseUser.getCurrentUser().put("eventA", false);
        ParseUser.getCurrentUser().put("eventsParticipated", 0);
        ParseUser.getCurrentUser().saveInBackground();
    }

}