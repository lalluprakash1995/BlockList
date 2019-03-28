package com.example.neo.blocklist;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

public class CallBlock extends BroadcastReceiver {
    private String number;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals("android.intent.action.PHONE_STATE"))
            return;
        else
        {
            number=intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (MainActivity_Block.blocklist.contains(new BlackList(number))){
                disconnectPhoneItelephony(context);
                return;
            }
        }


    }
    private void disconnectPhoneItelephony(Context context){
        ITelephony telephonyService;
        TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        try{
            Class c= Class.forName(telephonyManager.getClass().getName());
            Method m=c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService=(ITelephony)m.invoke(telephonyManager);
            telephonyService.endCall();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
