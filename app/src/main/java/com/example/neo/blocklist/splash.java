package com.example.neo.blocklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dell on 7/13/2017.
 */

public class splash extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Thread background = new Thread()
        {
            public void run()
            {

                try
                {
                    // Thread will sleep for 5 seconds

                    sleep(3000);

                    // After 5 seconds redirect to another intent



                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);


                    //Remove activity
                    finish();
                     /* */
                } catch (Exception e)
                {

                }
            }
        };
        // start thread
        background.start();
    }}