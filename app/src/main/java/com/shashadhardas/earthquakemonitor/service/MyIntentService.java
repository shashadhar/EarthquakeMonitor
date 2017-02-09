package com.shashadhardas.earthquakemonitor.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.shashadhardas.earthquakemonitor.R;
import com.shashadhardas.earthquakemonitor.activities.EarthQuakeDataLoader;
import com.shashadhardas.earthquakemonitor.activities.SplashActivity;
import com.shashadhardas.earthquakemonitor.model.EarthQuake;
import com.shashadhardas.earthquakemonitor.net.NetworkException;
import com.shashadhardas.earthquakemonitor.utils.SettingsManager;
import com.shashadhardas.earthquakemonitor.utils.Utils;

import java.util.List;

public class MyIntentService extends IntentService {

    private static  double MIN_MAGNITUDE = SettingsManager.getInstance().getDefaultMagnitude();
    private   int notificationId=0;

    Intent requestIntent;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        requestIntent=intent;
        loadEQs();

    }

    public  void releaseLock(){
        try {
            WakefulBroadcastReceiver.completeWakefulIntent(requestIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void  loadEQs() {
       try {
           Log.e("Intent service:fic","Sending request");
            String startDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC(SettingsManager.getInstance().getLastUpdatedEndTime());
            SettingsManager.getInstance().setLastUpdatedEndTime(Utils.getCurrentTime_UTC());
            String endDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC();

            EarthQuakeDataLoader.getEarthQuake(startDate, endDate, MIN_MAGNITUDE, new EarthQuakeDataLoader.IEventsListener() {
                @Override
                public void EQLoaded(List<EarthQuake.Feature> features) {
                    //sendNotification("test","test");
                    if(features!=null && features.size()>0){

                        for(EarthQuake.Feature feature:features){
                            String title=feature.getProperties().getTitle();
                            String body=feature.getProperties().getPlace();
                            Log.e("MyBackgroundService:fic",body+":"+title);
                            sendNotification(body,title,notificationId);
                            notificationId= (int)Math.random();
                        }


                    }
                    releaseLock();


                }

                @Override
                public void onError(NetworkException ex) {
                    releaseLock();
                }
            });
        } catch (Exception e) {
            releaseLock();
            e.printStackTrace();
        }

    }
    private void sendNotification(String messageBody, String title,int id) {
        try {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.earthquake_ic)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(id, notificationBuilder.build());
        } catch (Exception e) {
            releaseLock();
            e.printStackTrace();
        }

    }

}
