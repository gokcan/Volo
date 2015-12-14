package com.example.skylife.parsedb;

//@ Author and Hacker: @Skylifee7

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextInputLayout emailWrapper = (TextInputLayout) findViewById(R.id.emailWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);


        Parse.initialize(this, "wZJ1Nx8n1wxsmED2LQQ7C4xtkbvSBlb26qzP4LPt", "5vEXptj6DwN5c2O9aqOCzAfnEKaPQfxJubB7yLpu");
        ParseInstallation.getCurrentInstallation().saveInBackground();

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
                    passwordWrapper.setError(" Password lengh "+ passLen + " is out of boundries of 6-12");
                } else {
                    emailWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);


                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setPassword(password);

                    user.setEmail(email);

                    // other fields can be set just like with ParseObject

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(MainActivity.this, "Success! Welcome to Volo! ", Toast.LENGTH_SHORT).show();
                                //Intent takeUserHome = new Intent(this, HomeActivity.class);
                                // startActivity(takeUserHome);

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

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean validatePassword(String pass) {

        if (pass.length() < 6 || pass.length() > 12) {
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

}
