package com.example.neo.blocklist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dell on 8/12/2017.
 */

public class ViewMsg extends AppCompatActivity {

    AsyncHttpClient client,clients,cl;
    RequestParams params,param,pm;
    ArrayList<String> arrayList;
    ArrayList<String> msgId;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String MSG;
    String Usr_id;
    ListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_msg_lst);
        sharedPreferences=getApplicationContext().getSharedPreferences("pref",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        client=new AsyncHttpClient();
        cl=new AsyncHttpClient();
        params=new RequestParams();
        pm=new RequestParams();
        lv=(ListView)findViewById(R.id.lst_view);
        arrayList=new ArrayList<String>();
        msgId=new ArrayList<String>();

     //   String snd=sharedPreferences.getString("SENDER","");
         Usr_id =sharedPreferences.getString("usrid","");
        params.put("userid",Usr_id);

         Log.e("SND",Usr_id);

        client.get("http://sicsglobal.co.in/Privacy_App/API/ViewMessage.aspx?",params, new AsyncHttpResponseHandler()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);

                try {
                    JSONObject jsonObject = new JSONObject(content);
                    String result = jsonObject.getString("Status");
                    Log.e("SND", content);
                      if(result.equals("Success")){
                    JSONArray jsonArray=jsonObject.getJSONArray("messages");
                    for (int i=0;i<jsonArray.length();i++) {
                      JSONObject jobj = jsonArray.getJSONObject(i);
                    String rec = jobj.getString("id");
                        Log.e("SND", rec);
                    msgId.add(rec);
                        String mm = jobj.getString("msg");
                    String dd = jobj.getString("date");
                    String phn = jobj.getString("phone");
                        Log.e("SND", dd);
                //    arrayList.add("Date:         " + dd);
//
//                        String tt = jobj.getString("Time");
//                        arrayList.add("Time :        "+tt);


                        Log.e("SND", mm);
                    arrayList.add(phn+"\n"+"Message:        " + mm+ "\n"+"Date:  "+dd);
                     }
                       }




                    adapter ad = new adapter();
                    lv.setAdapter(ad);

                }

                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"No Message Found",Toast.LENGTH_LONG).show();
                }


            }
        });

    }
    class adapter extends BaseAdapter {
        LayoutInflater inflater;

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            String p = arrayList.get(position);

            inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cust_view_lst, null);
            Viewholder holder =new Viewholder();

            holder.ssnam = (TextView) convertView.findViewById(R.id.txtview);
            holder.ssnam.setText(arrayList.get(position));

            holder.Delete = (TextView) convertView.findViewById(R.id.dlt);

            holder.Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MSG = msgId.get(position);


                    //   String CartId = pref.getString("CART_ID", "");
                    Log.e("qqqqqqqqqqqqqq", MSG);
                    pm.put("MessageId", MSG);
                    pm.put("userid", Usr_id);

                    cl.get("http://sicsglobal.co.in/Privacy_App/API/DeleteMesg.aspx?", pm, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            try {

                                JSONObject dlobj = new JSONObject(content);

                                Log.e("PPPPPPPPPPPPP", pm + "");

                                String status = dlobj.getString("Status");
                                Log.e("STATUS", status);

                                if (status.equals("Success")) {

                                    Intent in = new Intent(getApplicationContext(), ViewMsg.class);
                                    startActivity(in);
                                    Toast.makeText(getApplicationContext(), "Message Deleted", Toast.LENGTH_SHORT).show();

                                }


                            } catch (Exception e) {
                            }
                        }

                    });
                }
            });
            return convertView;
        }}
        //
        class Viewholder {
            TextView ssnam;
            TextView Delete;

        }}


