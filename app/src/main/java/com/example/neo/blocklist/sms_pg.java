package com.example.neo.blocklist;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by dell on 8/12/2017.
 */

public class sms_pg extends AppCompatActivity {
    Button snd,view,inbx;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smspage);
        snd=(Button)findViewById(R.id.sndmsg_btn);
        view=(Button)findViewById(R.id.viewmsg_btn);
        inbx=(Button)findViewById(R.id.Inbox_btn);

        snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EncDecSMSActivity.class);
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ViewMsg.class);
                startActivity(intent);
            }
        });

        inbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MsgInbox.class);
                startActivity(intent);
            }
        });
    }
}
