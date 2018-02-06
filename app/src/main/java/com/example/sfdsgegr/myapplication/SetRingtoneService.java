package com.example.sfdsgegr.myapplication;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Created by ABC20259 on 2018/1/31.
 */
public class SetRingtoneService extends IntentService {
    private static final String TAG="SetRingtoneService";
    public SetRingtoneService(String name) {
        super(name);
    }

    public SetRingtoneService()
    {
        super("SetRingtoneService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG,"start in SetRingtoneService onHandleIntent");
        try
        {
//            Bundle bundle = intent.getExtras();
//            String params = bundle.getString("Information", ");
//            String number = bundle.containsKey("Information") ? bundle.getString("Information","") : "";"
            String phoneNumber = SysProp.get("persist.setting.contact.number","");
            String ringName = SysProp.get("persist.setting.contact.ring","");
            if(phoneNumber.length()==0 || ringName.length()==0){
                Log.i(TAG,"onHandleIntent / phoneNumber.length()==0 || ringName.length()==0");
                return;
            }

            final Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));//123456999

            final Uri lookupUri = ContactsContract.Contacts.getLookupUri(getContentResolver(), uri);

            final String lookupKey = lookupUri.getPathSegments().get(2);
            final Uri lookupWithoutIdUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                    lookupKey);
//            final Intent intent = new Intent(Intent.ACTION_EDIT, lookupWithoutIdUri);
//        startActivity(intent);
            Log.i(TAG,"uri:"+uri);
            Log.i(TAG,"lookupUri:"+lookupUri);
            Log.i(TAG,"lookupKey:"+lookupKey);
            Log.i(TAG,"lookupWithoutIdUri:"+lookupWithoutIdUri);

//            setRingtone(lookupUri,"content://media/internal/audio/media/206"); //65
            String ringtoneUri=getRingtoneUriByName(ringName).toString();
            setRingtone(lookupUri,ringtoneUri);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d(TAG, e.toString());
        }

    }



    private Uri getRingtoneUriByName( String targetRingToneName){

        RingtoneManager ringtoneMgr = new RingtoneManager(this);
        ringtoneMgr.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor alarmsCursor = ringtoneMgr.getCursor();
        int alarmsCount = alarmsCursor.getCount();
        if (alarmsCount == 0 && !alarmsCursor.moveToFirst()) {
            return null;
        }
        Uri[] alarms = new Uri[alarmsCount];
        while(!alarmsCursor.isAfterLast() && alarmsCursor.moveToNext()) {
            int currentPosition = alarmsCursor.getPosition();
            alarms[currentPosition] = ringtoneMgr.getRingtoneUri(currentPosition);

            Ringtone ringtone=RingtoneManager.getRingtone(this,alarms[currentPosition]);
            String title=ringtone.getTitle(this);
            Log.i(TAG," alarms[currentPosition] "+currentPosition+": "+ alarms[currentPosition]+" Title:" + title);

            if(title.indexOf(targetRingToneName)>=0){
                alarmsCursor.close();
                return alarms[currentPosition];
            }

        }
        alarmsCursor.close();
        return null;

    }

    private Uri[] canBeChoosedRington(){

        RingtoneManager ringtoneMgr = new RingtoneManager(this);
        ringtoneMgr.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor alarmsCursor = ringtoneMgr.getCursor();
        int alarmsCount = alarmsCursor.getCount();
        if (alarmsCount == 0 && !alarmsCursor.moveToFirst()) {
            return null;
        }
        Uri[] alarms = new Uri[alarmsCount];
        while(!alarmsCursor.isAfterLast() && alarmsCursor.moveToNext()) {
            int currentPosition = alarmsCursor.getPosition();
            alarms[currentPosition] = ringtoneMgr.getRingtoneUri(currentPosition);

            Ringtone ringtone=RingtoneManager.getRingtone(this,alarms[currentPosition]);
            String title=ringtone.getTitle(this);
            Log.i(TAG," alarms[currentPosition] "+currentPosition+": "+ alarms[currentPosition]+" Title:" + title);

        }
        alarmsCursor.close();
        return alarms;

    }
    private void setRingtone(Uri contactUri , String value ) {
//        Uri contactUri = intent.getParcelableExtra(EXTRA_CONTACT_URI);//contactUri //use lookupUri
//        String value = intent.getStringExtra(EXTRA_CUSTOM_RINGTONE); //content://media/internal/audio/media/165

        if (contactUri == null) {
            Log.e(TAG, "Invalid arguments for setRingtone");
            return;
        }
        ContentValues values = new ContentValues(1);
        values.put(ContactsContract.Contacts.CUSTOM_RINGTONE, value);
        getContentResolver().update(contactUri, values, null, null);
    }



    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

}
