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
           /*
        Dummy auth codes. Of course not real ones!
        */
        Parse.initialize(this, "xtkbvSBlb26qzP4LPt", "5vEXptj6DwN5c2O9xJubB7yLpu");
        ParseInstallation.getCurrentInstallation().saveInBackground();


    }

}
