package com.siech0.morsesms;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_CODE = 45690;
    private boolean allowService = false;
    private boolean serviceRunning = false;

    DexterBuilder smsPermChecker;
    DexterBuilder vibratePermChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        smsPermChecker = Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
                .withListener(DialogOnAnyDeniedMultiplePermissionsListener.Builder
                        .withContext(this)
                        .withTitle("SMS Read/Receive Permission")
                        .withMessage("SMS Read/Receive permissisions are required for basic service functionality")
                        .withButtonText(android.R.string.ok)
                        .withIcon(R.mipmap.app_icon)
                        .build()
                )
                .withErrorListener((new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Log.e("Dexter", error.toString());
                    }
                }));
        vibratePermChecker = Dexter.withActivity(this)
                .withPermission(Manifest.permission.VIBRATE)
                .withListener(DialogOnDeniedPermissionListener.Builder
                        .withContext(this)
                        .withTitle("Vibrate Permission")
                        .withMessage("Vibrate permissions are needed to vibrate the device.")
                        .withButtonText(android.R.string.ok)
                        .withIcon(R.mipmap.app_icon)
                        .build()
                )
                .withErrorListener((new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Log.e("Dexter", error.toString());
                    }
                }));
        setContentView(R.layout.activity_main);
    }

    public void btnServiceActivator_Click(View view){
        smsPermChecker.check();
        vibratePermChecker.check();
        final Button btnServiceActivator = (Button) view;
        final TextView lblServiceStatus = (TextView) findViewById(R.id.lblServiceStatus);
        if(serviceRunning){
            lblServiceStatus.setText("Running.");
            btnServiceActivator.setText("Start Service");
            terminateMorseService();
        } else {
            lblServiceStatus.setText("Online.");
            btnServiceActivator.setText("Terminate Service");
            startMorseService();
        }
    }

    private void startMorseService(){
        startService(new Intent(this, MorseService.class));
        serviceRunning = true;
        Toast.makeText(this, "Started morse sms service.", Toast.LENGTH_LONG).show();
    }

    private void terminateMorseService(){
        stopService(new Intent(this, MorseService.class));
        serviceRunning = false;
        Toast.makeText(this, "Terminated morse sms service.", Toast.LENGTH_LONG).show();
    }
}
