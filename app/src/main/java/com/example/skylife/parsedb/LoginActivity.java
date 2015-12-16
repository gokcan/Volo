package com.example.skylife.parsedb;

/*

||v1.0||
||Author: Gökcan DEĞİRMENCİ||
||For detailed information please visit http://gokcan.degirmenci.me ||
||@Skylifee7||

 */

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;


public class LoginActivity extends AppCompatActivity{

    protected Button mLogintoRegister, mRegisterButton;
    protected EditText mUsername, mPassword;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
        Simple usage of Mediaplayer, it uses pre-defined audio effect. ∂
         */
        mp = MediaPlayer.create(getApplicationContext(), R.raw.pad_confirm);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "wZJ1Nx8n1wxsmED2LQQ7C4xtkbvSBlb26qzP4LPt", "5vEXptj6DwN5c2O9aqOCzAfnEKaPQfxJubB7yLpu");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        mUsername = (EditText) findViewById(R.id.textUsername1);

        mPassword = (EditText) findViewById(R.id.textPassword1);
        mRegisterButton = (Button) findViewById(R.id.buttonRegister1);
        mLogintoRegister = (Button) findViewById(R.id.buttonSignup);

        mLogintoRegister.setTransformationMethod(null); //Avoid AllCaps bug on Lollipop


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                /*
                This part is the coolest snippet of our LoginActivity .
                Snackbar raises text just after the login. We welcome our users with their name and cat emoji :)
                Cool, huh ?
                 */
                final Snackbar snackbar = Snackbar
                        .make(v, "Nice to see you " + username + " \uD83D\uDE3B ", Snackbar.LENGTH_LONG)
                        .setAction("action", null);

                snackbar.show();

                /*dwdjkndcjnejkncekjcnkjencjkenjkcnjencfjkencf
                feferferfeferfeferfe
                rfefrerferferferferferf
                referfefrbhhlvllvgugvj Used for some delay. We cannot use Thread.sleep() method.
                 */
                ParseUser.logInInBackground(username, password, new LogInCallback() {

                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {

                            // Hooray! The user is logged in!

                            /*
                            The mediaplayer instance starts playing before the Intent. Because
                            of the synchronisation problems.
                             */
                            if (mp.isPlaying()) {
                                mp.stop();
                                mp.release();
                                mp = MediaPlayer.create(getApplicationContext(), R.raw.pad_confirm);
                            }
                            mp.start();
                            Intent takeUserHome = new Intent(LoginActivity.this, NavigationActivity.class);
                            startActivity(takeUserHome);

                        } else {
                            Toast.makeText(LoginActivity.this, "Something went wrong! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        mLogintoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takeUserSignup = new Intent(LoginActivity.this, MainActivity.class);

                /*
                Custom animation on bridge between Login to Signup pages.
                 */
                startActivity(takeUserSignup);
                overridePendingTransition(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_grow_fade_in_from_bottom);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
