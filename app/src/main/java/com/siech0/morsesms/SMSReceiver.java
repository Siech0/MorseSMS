package com.siech0.morsesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SMSReceiver extends BroadcastReceiver {
    private final String TAG = this.getClass().getSimpleName();
    private static List<Listener> listeners = new ArrayList<Listener>();

    @Override
    public void onReceive(Context context, Intent intent){
        String smsSender = "";
        String smsBody = "";
        if(Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                for(SmsMessage smsMessage: Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsSender = smsMessage.getOriginatingAddress();
                    smsBody = smsMessage.getMessageBody();
                }
            } else {
                Bundle smsBundle = intent.getExtras();
                if (smsBundle != null) {
                    Object[] pdus = (Object[]) smsBundle.get("pdus");
                    if (pdus == null) {
                        Log.e(TAG, "SmsBundle had no pdus key.");
                        return;
                    }
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; ++i) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        smsBody += messages[i].getMessageBody();
                    }
                    smsSender = messages[0].getOriginatingAddress();
                }
            }

            for(Listener l : listeners){
                l.onSMSReceived(smsSender, smsBody);
            }
        }
    }

    public static void addListener(Listener listener){
        listeners.add(listener);
    }
    public static void removeListener(Listener listener){
        listeners.remove(listener);
    }
    public static boolean hasListener(Listener listener){
        return listeners.contains(listener);
    }

    public interface Listener{
        void onSMSReceived(String sender, String body);
    }
}
