package com.siech0.morsesms;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MorseVibratePlayer implements IMorsePlayer {

    private final Vibrator vibrator;
    MorseVibratePlayer(Vibrator vibrator){
        this.vibrator = vibrator;
    }

    class AsyncTaskParam{
        IMorseOutputStream os;
        int unitTime;
        public AsyncTaskParam(IMorseOutputStream os, int unitTime){
            this.os = os;
            this.unitTime = unitTime;
        }
    }

    private class PlayTask extends AsyncTask<AsyncTaskParam, Void, Void>{
        @Override
        protected Void doInBackground(AsyncTaskParam... param){
            IMorseOutputStream data = param[0].os;
            int unitTime = param[0].unitTime;
            Iterator<IMorseStreamChunk> it = data.iterator();
            while(it.hasNext()){
                IMorseStreamChunk chunk = it.next();
                Log.e("MORSE_VIBRATE_PLAYER:", String.format("Vibrate: %s, %b", chunk.units(), chunk.active()));
                if(chunk.active()){
                    vibrate(chunk.units()*unitTime);
                    try {
                        wait(chunk.units()*unitTime);
                        Log.e("MOUSE_VIBRATE_PLAYER", String.format("Wait: %s", chunk.units()));
                    } catch (Exception e){
                        Log.e("MORSE_VIBRATE_PLAYER", "Wait threw");
                    }
                    Log.e("MOUSE_VIBRATE_PLAYER", String.format("Play: %s", chunk.units()));
                } else {
                    try {
                        wait(chunk.units()*unitTime);
                        Log.e("MOUSE_VIBRATE_PLAYER", String.format("Wait: %s", chunk.units()));
                    } catch (Exception e){
                        Log.e("MORSE_VIBRATE_PLAYER", "Wait threw");
                    }
                }
            }
            return null;
        }
    }

    @Override
    public void play(final IMorseOutputStream data, final int unitTime){
        if(!verifyPermissions()){
            return;
        }
        new PlayTask().execute(new AsyncTaskParam(data, unitTime));
    }

    private boolean verifyPermissions(){
        return true;
    }

    private void vibrate(int milliseconds){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(milliseconds);
        }
    }

    private void wait(int milliseconds) {
        try
        {
            Thread.sleep(milliseconds);
        } catch(Exception e){
            Log.e("VIBRATE_PLAYER", "Sleep Interrupted");

        }
    }

    @Override
    public String playerName(){
        return "Vibrate Player";
    }

    @Override
    public List<String> getRequiredPermissions(){
        return new ArrayList<String>(Arrays.asList(Manifest.permission.VIBRATE));
    }
    @Override
    public String getPermissionDialogTitle(String perm){
        if(perm == Manifest.permission.VIBRATE) {
            return "Vibrate Permission";
        } else {
            throw new IllegalArgumentException("Provided permission string that is not requested by class.");
        }
    }
    @Override
    public String getPermissionDialogText(String perm){
        if(perm == Manifest.permission.VIBRATE) {
            return "Vibrate permission is required to vibrate the device.";
        } else {
            throw new IllegalArgumentException("Provided permission string that is not requested by class.");
        }
    }

}
