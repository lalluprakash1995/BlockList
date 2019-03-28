package com.example.neo.blocklist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

/**
 * Created by Neo on 12-08-2017.
 */

public class BlockCast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
//        ArrayList<String> blockList=new ArrayList<>();
//        blockList=intent.getStringArrayListExtra("BLOCK_LIST");
//        Log.e("getList",blockList.toString());
        String incomingNum;

        if (!intent.getAction().equals("android.intent.action.PHONE_STATE"))
            return;
        else {
            incomingNum=intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Toast.makeText(context,incomingNum,Toast.LENGTH_LONG).show();
            MainActivitys check=new MainActivitys();

            if(check.search(incomingNum)){
                disconnectPhoneItelephony(context);
            }
        }

    }
    private void disconnectPhoneItelephony(Context context)
    {
        ITelephony telephonyService;
        TelephonyManager telephony = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        try
        {
            Class c = Class.forName(telephony.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(telephony);
            telephonyService.endCall();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
