package com.example.neo.blocklist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);
try {
    SharedPreferences prefs = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
    String list = prefs.getString("BLOCK", "");
    Log.e("GETTING",list);
}
catch (Exception e){

}
    }

    public void msg_icon(View view){

        Intent intent=new Intent(getApplicationContext(),sms_pg.class);
        startActivity(intent);
    }
    public void call_blk_icon(View view){

            Intent cl=new Intent(getApplicationContext(),MainActivity_Block.class);
            startActivity(cl);


    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            SharedPreferences prefs = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
            String list = prefs.getString("BLOCK", "");
            Log.e("GETTING",list);
        }
        catch (Exception e){

        }
    }
}
