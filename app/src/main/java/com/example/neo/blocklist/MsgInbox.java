package com.example.neo.blocklist;

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

public  class MsgInbox extends AppCompatActivity {

    AsyncHttpClient client,clients,cl;
    RequestParams params,param,pm;
    ArrayList<String> arrayList;
    ArrayList<String> msgNum;
    ArrayList<String> msgCont;
    ArrayList<String> msgDate;
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
        msgNum=new ArrayList<String>();
        msgCont=new ArrayList<String>();
        msgDate=new ArrayList<String>();

        //   String snd=sharedPreferences.getString("SENDER","");
        Usr_id =sharedPreferences.getString("usrid","");
        params.put("userid",Usr_id);

        Log.e("SND",Usr_id);

        client.get("http://sicsglobal.co.in/Privacy_App/API/ViewRecvdMesg.aspx?",params, new AsyncHttpResponseHandler()
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
                            String rec = jobj.getString("phonenum");
                            Log.e("SND", rec);
                            msgNum.add(rec);
                            String mm = jobj.getString("mesg");
                            msgCont.add(mm);
                            String dd = jobj.getString("date_add");
                            Log.e("SND", dd);
                            msgDate.add(dd);

                            Log.e("SND", mm);
                            arrayList.add("Phn:        " + mm+ "\n"+"Date:  "+dd);
                        }
                    }


                    adapter ad = new adapter();
                    lv.setAdapter(ad);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {



                            editor.putString("MSG_NUM", msgNum.get(position).toString());
                            editor.putString("MSG_CONT", msgCont.get(position).toString());
                            editor.putString("MSG_DATE", msgDate.get(position).toString());
                            editor.apply();
                            Log.e("qqqqqqqqqqqqqq", msgNum.get(position).toString());
//                        Log.e("qqqqqqqqqqqqqq", usrId.get(position).toString());
                            Intent aa=new Intent(getApplicationContext(),ViewInboxMsg.class);
                            startActivity(aa);

                        }
                    });
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


            inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cust_inbox, null);
            Viewholder holder =new Viewholder();

            holder.ssnam = (TextView) convertView.findViewById(R.id.txtview);
            holder.ssnam.setText(arrayList.get(position));


            return convertView;
        }}
    //
    class Viewholder {
        TextView ssnam;


    }}