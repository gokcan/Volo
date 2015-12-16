package com.example.skylife.parsedb;

//@ Author and Hacker: Gökcan DEĞİRMENCİ @Skylifee7

//Signup Activity

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity  {

    protected EditText mUsername,mEmail,mPassword;
    protected Button mRegisterButton;
    public int eventsPrt = 80;
    public boolean emailVerified = false;
    public int passLen;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    public MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupWindowAnimations();

        mp = MediaPlayer.create(getApplicationContext(), R.raw.pad_confirm);

        final TextInputLayout emailWrapper = (TextInputLayout) findViewById(R.id.emailWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);


        mUsername = (EditText) findViewById(R.id.textUsername);

        mEmail = (EditText) findViewById(R.id.textEmail);
        mPassword = (EditText) findViewById(R.id.textPassword);
        mRegisterButton = (Button) findViewById(R.id.buttonRegister);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                String username = mUsername.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                if (!validateEmail(email)) {
                    mEmail.setError("");
                    emailWrapper.setError("Not a valid email address!");
                } else if (!validatePassword(password)) {
                    mPassword.setError("");
                    passwordWrapper.setError(" Password lengh " + passLen + " is out of boundries of 6-12");
                } else {
                    emailWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);


                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setPassword(password);

                    user.setEmail(email);
                    /*
                    Use that in LoginActivity

                    user = ParseUser.getCurrentUser();
                    Boolean isVerified = user.getBoolean("emailVerified");
                    */

                    // other fields can be set just like with ParseObject

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {

                                if (mp.isPlaying()) {
                                    mp.stop();
                                    mp.release();
                                    mp = MediaPlayer.create(getApplicationContext(), R.raw.pad_confirm);
                                }
                                mp.start();

                                Toast.makeText(MainActivity.this, "Success! Welcome to Volo! ", Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, "Please verify your Email! ", Toast.LENGTH_SHORT).show();


                                Intent takeUserHome = new Intent(MainActivity.this, NavigationActivity.class);
                                overridePendingTransition(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_grow_fade_in_from_bottom);
                                startActivity(takeUserHome);



                            } else {
                                Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    protected void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean validatePassword(String pass) {

        if (pass.length() < 6 || pass.length() > 12 || pass.contains("'")) {

            passLen = pass.length();
            return false;
        }
        else
            return true;
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }


}
