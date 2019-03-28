package com.example.neo.blocklist;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Registration extends AppCompatActivity {
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Set up the login form.


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mPasswordView = (EditText) findViewById(R.id.keylog);


    }}
