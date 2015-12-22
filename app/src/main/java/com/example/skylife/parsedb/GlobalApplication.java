package com.example.skylife.parsedb;
/*

||v1.0||
||Author: Gökcan DEĞİRMENCİ||
||For detailed information please visit http://gokcan.degirmenci.me ||
||@Skylifee7||

 */

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "wZJ1Nx8n1wxsmED2LQQ7C4xtkbvSBlb26qzP4LPt", "5vEXptj6DwN5c2O9aqOCzAfnEKaPQfxJubB7yLpu");
        ParseInstallation.getCurrentInstallation().saveInBackground();




    }

}