package com.example.neo.blocklist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainAct1 extends AppCompatActivity {
    EditText enter_number;
    Button block;
    ArrayList<String> contact=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act1);
        enter_number = (EditText) findViewById(R.id.enter_contact);
        block = (Button) findViewById(R.id.block);
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.add(enter_number.getText().toString());
                Log.e("block", contact.toString());
            }
        });


    }
}
