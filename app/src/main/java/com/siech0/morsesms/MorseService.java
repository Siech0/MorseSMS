package com.siech0.morsesms;



import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Vibrator;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

public class MorseService extends Service {

    private final LocalBinder mBinder = new LocalBinder();
    private IMorseTranslator translator;
    private IMorsePlayer player;

    public class LocalBinder extends Binder {
        MorseService getService() {
            return MorseService.this;
        }
    }

    private SMSReceiver.Listener smsListener = new SMSReceiver.Listener() {
        @Override
        public void onSMSReceived(String sender, String body) {

            final MorseStreamChunk di = new MorseStreamChunk(1, true);
            final MorseStreamChunk dit = di;
            final MorseStreamChunk dah = new MorseStreamChunk(3, true);
            final MorseStreamChunk s = new MorseStreamChunk(1, false);
            IMorseOutputStream full = new MorseListOutputStream();
            IMorseOutputStream osSender = translator.translate(String.format("sender: %s", sender));
            IMorseOutputStream osBody = translator.translate(String.format("body: %s", body));
            full.addAll(osSender);
            full.addAll(translator.wordSpace());
            full.addAll(osBody);
            Log.e("MORSE_SERVICE", translator.toMorseString(full));
            final IMorseOutputStream os = new MorseListOutputStream(Arrays.asList(
                    di, s, di, s, di, s, s, s, dah, s, dah, s, dah, s, s, s,
                    di, s, di, s, di, s, s, s, dah, s, dah, s, dah, s, s, s,
                    di, s, di, s, di, s, s, s, dah, s, dah, s, dah, s, s, s,
                    di, s, di, s, di, s, s, s, dah, s, dah, s, dah, s, s, s,
                    di, s, di, s, di, s, s, s, dah, s, dah, s, dah, s, s, s
            ));
            player.play(os, 1000);
            Log.e("MORSE_SERVICE", String.format("Sender: %s, Text: %s", sender, body));
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();

        //SMS Receiver Setup
        SMSReceiver.addListener(this.smsListener);;
        translator = new MorseITLTranslator();
        player = new MorseVibratePlayer((Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE));

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        return android.app.Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSReceiver.removeListener(this.smsListener);
        Toast.makeText(this, R.string.morse_service_stopped, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    void onSMSReceived(String sender, String text) {
        Log.e("MORSE_SERVICE", "Sms receieved");
        IMorseOutputStream os = translator.translate(String.format("%s: %s", sender, text));
        player.play(os, 150);
    }
}
