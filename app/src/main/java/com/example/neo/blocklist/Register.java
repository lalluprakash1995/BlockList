package com.example.neo.blocklist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

public class Register extends AppCompatActivity{
    EditText name,email,password,phone;
    Button submit,clear;
    AsyncHttpClient client;
    RequestParams params;
    JSONObject jobject;
    JSONArray jarry;
    String url="http://sicsglobal.co.in/Privacy_App/API/Registration.aspx?";
    SharedPreferences shared;
    String strname;
    String stremail;
    String strpassword;
    String strphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name=(EditText)findViewById(R.id.name_reg);
        email=(EditText)findViewById(R.id.emailid_reg);
        password=(EditText)findViewById(R.id.password_reg);
        phone=(EditText)findViewById(R.id.phone_reg);
        submit=(Button)findViewById(R.id.registerrr);
        client=new AsyncHttpClient();
        params=new RequestParams();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strname=name.getText().toString();
                stremail=email.getText().toString();
                strpassword=password.getText().toString();
                strphone=phone.getText().toString();
                shared=getApplicationContext().getSharedPreferences("pref",MODE_PRIVATE);
                final SharedPreferences.Editor edit=shared.edit();

                if (strname.isEmpty()) {
                    name.setError("is Empty");
                }
                else if (stremail.isEmpty()) {
                    email.setError("is Empty");
                }
                else if (strpassword.isEmpty()) {
                    password.setError("is Empty");
                }
                else  if (strphone.isEmpty()) {
                    phone.setError("is Empty");
                }
                else {
                    edit.putString("sharedemail",stremail);
                    edit.putString("sharedpass",strpassword);
                    edit.putString("sharedphone",strphone);
                    params.put("name",strname);
                    params.put("email",stremail);
                    params.put("password",strpassword);
                    params.put("phone",strphone);
                    client.get(url,params,new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            Log.e("content:",content);
                            try {
                                jobject=new JSONObject(content);
                                String s=jobject.getString("Status");
                                Log.e("Status:",s);
                                if (s.equals("Success")){
                                    Intent i=new Intent(Register.this,LoginActivity.class);
                                    edit.apply();
                                    startActivity(i);
                                }
                                else if (s.equals("AlreadyExist")){
                                    Toast.makeText(Register.this,s,Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(Register.this,MainActivity.class);
                                    startActivity(i);
                                }



                            }
                            catch (Exception e){

                            }

                        }
                    });

                }
            }
        });
    }
}
