package com.example.neo.blocklist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Neo on 16-08-2017.
 */

public class BlockLog extends AppCompatActivity {
    ListView blockLog;
    static ArrayList<String> blockLog_array =new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_log);
        blockLog = (ListView)findViewById(R.id.block_log);
        SharedPreferences shared=getSharedPreferences("inNum", Context.MODE_PRIVATE);
        String incomeNum= shared
                .getString("incomingNumber",null);
        blockLog_array.add(incomeNum);
        Adapter dap =new Adapter();
        blockLog.setAdapter(dap);
    }
    class Adapter extends BaseAdapter {
        LayoutInflater inflater;
        @Override
        public int getCount() {
            return blockLog_array.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.block_log_text_view,null);
            TextView indexNum,listItem,sysTime;
            listItem =(TextView) convertView.findViewById(R.id.log_num);
            listItem.setText(blockLog_array.get(position));
            indexNum=(TextView) convertView.findViewById(R.id.index_no);
            indexNum.setText(""+(position+1));
            sysTime=(TextView) convertView.findViewById(R.id.time);
            sysTime.setText(Calendar.getInstance().getTime().toString());
            return convertView;
        }

    }
}
