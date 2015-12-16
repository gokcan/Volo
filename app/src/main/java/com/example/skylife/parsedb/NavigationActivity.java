package com.example.skylife.parsedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/*

||v1.0||
||Author: Gökcan DEĞİRMENCİ||
||For detailed information please visit https://gokcan.degirmenci.me ||
||@Skylifee7||

 */

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        Initiliase the layouts.
         */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         Inflate the menu; this adds items to the action bar if it is present.
          */
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Handle action bar item clicks here. The action bar will
        automatically handle clicks on the Home/Up button
         */
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent takeUserSettings = new Intent(NavigationActivity.this, ScrollingActivity.class);
            startActivity(takeUserSettings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        /*
         Handle navigation view item clicks here and also
          handle the Fragment Transactions . @Skylifee7
          */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            HomeFragment homeFragment = new HomeFragment();
            fragmentTransaction.replace(R.id.navcontent, homeFragment, "HOME_FRAGMENT");
            fragmentTransaction.commit();
            String mTitle = "Home";

            actionBar.setTitle(mTitle);

        } else if (id == R.id.nav_lastevent) {


        } else if (id == R.id.nav_userprofile) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_security) {

        }

        /*
         Highlight the selected item, update the title, and close the drawer simultaneuosly @Skylifee7
          */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;



    }

}
