package com.example.skylife.parsedb;

/*

||v1.0||
||Author: Gökcan DEĞİRMENCİ||
||For detailed information please visit http://gokcan.degirmenci.me ||
||@Skylifee7||

 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        Pretty cool Lollipop layout used for this parallax effect.
        CoordinatorLayout provides great UI components to use :)
         */
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "help@volo.org", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                /*
                We need an Activity, which contains an Intent and launches the email clients that are installed in the Android device.
                For this reason, we should define SENDTO as action in that Intent and mailto: as it’s data.
                 Moreover, should set the Intent’s type as message/rfc822, in order to prompt only email providers not other services.
                 It is super-cool to reach an outer email client via our Button.
                 */

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"help@volo.org"});
                i.putExtra(Intent.EXTRA_SUBJECT, "I need help for something");
                i.putExtra(Intent.EXTRA_TEXT, "I have a question/problem about that..");

                try {
                    startActivity(Intent.createChooser(i, "Send mail with: "));
                } catch (android.content.ActivityNotFoundException ex) { // Everyphone has email clients but we need to be sure..

                    Snackbar.make(view, "There are no email clients installed on this phone!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            }
        });
    }
}
