package com.example.neo.blocklist;

/**
 * Created by dell on 5/10/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by dell on 5/10/2017.
 */


public class EncDecSMSActivity extends AppCompatActivity {


    /** Called when the activity is first created. */


    EditText recNum;


    EditText secretKey;


    EditText msgContent;


    Button send;


    Button cancel;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    AsyncHttpClient client;
    RequestParams params;
    String number;
    @Override

    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        sharedPreferences = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        client = new AsyncHttpClient();
        params = new RequestParams();

        recNum = (EditText) findViewById(R.id.recNum);

        secretKey = (EditText) findViewById(R.id.secretKey);

        msgContent = (EditText) findViewById(R.id.msgContent);

        send = (Button) findViewById(R.id.Send);
number=recNum.getText().toString();
        cancel = (Button) findViewById(R.id.cancel);
recNum.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        startActivityForResult(intent, 1);


    }});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Uri contactData = data.getData();
                Cursor cursor =  managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                 number =       cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //contactName.setText(name);
                recNum.setText(number);
                //contactEmail.setText(email);
            }
        }




        // finish the activity when click Cancel button

        cancel.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {


                finish();

            }
        });


        // encrypt the message and send when click Send button


        send.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {


                TelephonyManager telMgr =
                        (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(EncDecSMSActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                telMgr.getSubscriberId();
                String deviceID = telMgr.getDeviceId();
                String simSerialNumber = telMgr.getSimSerialNumber();
                String simLineNumber = telMgr.getLine1Number();
                String recNumString = recNum.getText().toString();
                number=recNum.getText().toString();
                Log.e("GETTTTTT",number);

                Date currentTime = java.util.Calendar.getInstance().getTime();
                String Date=String.valueOf(currentTime);
                String s=currentTime.toString();
                String time=s.substring(11,19);
                Log.e("YYYYYYYY",time);
                Log.e("DDDDDDDDD",Date);

                String secretKeyString = secretKey.getText().toString();



                String msgContentString = msgContent.getText().toString();

                editor.putString("RECEIVER",number);
                editor.putString("SENDER",simLineNumber);
                editor.putString("MSGCONTENT",msgContentString);
                editor.putString("DATE",Date);
                editor.putString("TIME",time);
                editor.apply();

                // check for the validity of the user input



                // key length should be 16 characters as defined by AES-128-bit




                if (number.length() > 0 && secretKeyString.length() > 0&&number.length() >=10
                        && msgContentString.length() > 0 && secretKeyString.length() == 16) {



                    // encrypt the message



                    byte[] encryptedMsg = encryptSMS(secretKeyString,



                            msgContentString);



                    // convert the byte array to hex format in order for



                    // transmission



                    String msgString = byte2hex(encryptedMsg);



                    // send the message through SMS



                    sendSMS(number, msgString);

                    String Userid=sharedPreferences.getString("usrid","");

                   // Toast.makeText(getBaseContext(), "send message", Toast.LENGTH_SHORT).show();
//                    params.put("receiver",recNumString);
//                    Log.e("simLineNumberrr",recNumString);
//
//                    params.put("sender",simLineNumber);
//                    Log.e("eeeeeeeeesimLineNumber",simLineNumber);
//
//                    params.put("message",msgContentString);
//                    Log.e("rrrrrrrrr",msgContentString);
                    params.put("userid",Userid);
                    Log.e("rrrrrrrrr",Userid);
                    params.put("msg",msgContentString);
                    params.put("phone",number);
                    Log.e("NUMBER",number);
                    client.get("http://sicsglobal.co.in/Privacy_App/API/SentMessage.aspx?",params,new AsyncHttpResponseHandler()
                    {
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);

                            try {
                                JSONObject jsonObject=new JSONObject(content);
                                String status=jsonObject.getString("Status");
                                Log.e("rrrrrrrrr",status);
                                if(status.equals("Success")){
                                    String msgid=jsonObject.getString("MessageId");
                    editor.putString("IDMsg",msgid);
                                    editor.apply();
                                    Toast.makeText(getBaseContext(), "send message", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    });


                    // finish



                    finish();



                } else
                    Toast.makeText(getBaseContext(),"Please enter phone number, secret key and the message. Secret key must be 16 characters!",
                            Toast.LENGTH_SHORT).show();



            }



        });



    }



    public static void sendSMS(String recNumString, String encryptedMsg) {



        try {



            // get a SmsManager



            SmsManager smsManager = SmsManager.getDefault();



            // Message may exceed 160 characters



            // need to divide the message into multiples



            ArrayList<String> parts = smsManager.divideMessage(encryptedMsg);



            smsManager.sendMultipartTextMessage(recNumString, null, parts,



                    null, null);



        } catch (Exception e) {



            e.printStackTrace();



        }



    }



    // utility function



    public static String byte2hex(byte[] b) {



        String hs = "";



        String stmp = "";


        for (int n = 0; n < b.length; n++) {

            stmp = Integer.toHexString(b[n] & 0xFF);

            if (stmp.length() == 1)

                hs += ("0" + stmp);

            else

                hs += stmp;



        }

        return hs.toUpperCase();



    }



    // encryption function



    public static byte[] encryptSMS(String secretKeyString,



                                    String msgContentString) {



        try {



            byte[] returnArray;



            // generate AES secret key from user input



            Key key = generateKey(secretKeyString);



            // specify the cipher algorithm using AES



            Cipher c = Cipher.getInstance("AES");



            // specify the encryption mode



            c.init(Cipher.ENCRYPT_MODE, key);



            // encrypt



            returnArray = c.doFinal(msgContentString.getBytes());



            return returnArray;



        } catch (Exception e) {



            e.printStackTrace();



            byte[] returnArray = null;



            return returnArray;



        }



    }


    private static Key generateKey(String secretKeyString) throws Exception {



        // generate secret key from string



        Key key = new SecretKeySpec(secretKeyString.getBytes(), "AES");



        return key;



    }}