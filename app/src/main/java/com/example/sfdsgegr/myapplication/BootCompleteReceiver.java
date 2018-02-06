package com.example.sfdsgegr.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, " in BootCompleteReceiver sfdsgegr");

//        "com.example.sfdsgegr.myapplication@%@android.intent.action.MAIN"
        Intent mIntent = new Intent();
        mIntent.setAction("android.intent.action.MAIN");
        mIntent.setPackage("com.example.sfdsgegr.myapplication");
        context.startService(mIntent);

    }

}