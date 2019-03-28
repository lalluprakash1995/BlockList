package com.example.neo.blocklist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText emailedt,passedt;
    TextView register,changepass;
    String url="http://sicsglobal.co.in/Privacy_App/API/Login.aspx?";
    AsyncHttpClient client;
    RequestParams params;
    JSONObject jobject;
    JSONArray jarray;
    String email,password;
    String name,userid;
    SharedPreferences shared;
    ProgressDialog progress;
    //SharedPreferences shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//         undDrawable(new ColorDrawable(Color.parseColor("#0000ff")));
        login= (Button) findViewById(R.id.loginbut);
        emailedt=(EditText)findViewById(R.id.UserNamelog);
        passedt=(EditText)findViewById(R.id.keylog);
        register=(TextView)findViewById(R.id.registerbut);
        changepass=(TextView)findViewById(R.id.forgetbut);
        client=new AsyncHttpClient();
        params=new RequestParams();
        progress=new ProgressDialog(this);
        progress.setTitle("Processing....");
        progress.setMessage("Loading......");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
//        changepass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //   progress.show();
//                Intent ip=new Intent(MainActivity.this,ForgotPassword.class);
//                startActivity(ip);
//
//            }
//        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.show();
                email=emailedt.getText().toString();
                password=passedt.getText().toString();
                params.put("email",email);
                params.put("password",password);
                client.get(url,params,new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String content) {

                        super.onSuccess(content);
                        Log.e("content",content);
                        try {

                            jobject=new JSONObject(content);
                            String s=jobject.getString("Status");
                            Log.e("Status=",s);

                            if (s.equals("Success")){

                                name=jobject.getString("Name");
                                Log.e("name",name);
                                userid=jobject.getString("UserId");
                                Log.e("id",userid);
                                shared=getApplicationContext().getSharedPreferences("pref",MODE_PRIVATE);
                                SharedPreferences.Editor edit=shared.edit();
                                edit.putString("usrid",userid);
                                edit.putString("usrname",name);

                                Toast.makeText(LoginActivity.this,name,Toast.LENGTH_SHORT).show();
                                Intent ip=new Intent(LoginActivity.this,MainActivity.class);
                                edit.apply();
                                startActivity(ip);
                                progress.dismiss();
                                finish();

                            }
                            else
                                Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT).show();
                            progress.dismiss();

                        }
                        catch (Exception e){

                        }
                    }
                });

//
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,Register.class);
                startActivity(i);
            }
        });
    }
}


