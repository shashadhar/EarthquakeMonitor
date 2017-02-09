package com.shashadhardas.earthquakemonitor.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.shashadhardas.earthquakemonitor.R;
import com.shashadhardas.earthquakemonitor.activities.EarthQuakeDataLoader;
import com.shashadhardas.earthquakemonitor.activities.SplashActivity;
import com.shashadhardas.earthquakemonitor.model.EarthQuake;
import com.shashadhardas.earthquakemonitor.net.NetworkException;
import com.shashadhardas.earthquakemonitor.net.VolleyCommunicator;
import com.shashadhardas.earthquakemonitor.utils.SettingsManager;
import com.shashadhardas.earthquakemonitor.utils.Utils;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shashadhar on 06-02-2017.
 */

public class MyBackgroundService extends Service {
    private static  int NOTIFY_INTERVAL = SettingsManager.getInstance().getDefaultInterval();
    private static  double MIN_MAGNITUDE = SettingsManager.getInstance().getDefaultMagnitude();
    private   int notificationId=0;
    public  static String ARG_NOTIFY_INTERVAL="ARG_NOTIFY_INTERVAL";
    public  static String ARG_NOTIFY_MAGNITUDE="ARG_NOTIFY_MAGNITUDE";
    public  static String ARG_RE_CREATE="ARG_RE_CREATE";
    private   boolean isServiceReCreate=false;
    private    VolleyCommunicator communicator=new VolleyCommunicator(this);
    private  static HandlerThread thread;


    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    // timer handling
    private Timer mTimer = null;
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            try {
                Log.e("MyBackgroundService","service running");
                if(mTimer!=null){
                    mTimer.cancel();
                }
                mTimer=null;
                mTimer = new Timer();
                mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, (NOTIFY_INTERVAL * 60 * 1000));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("MyBackgroundService","onCreate");
        if(thread==null) {
             thread = new HandlerThread("MyBackgroundService", Process.THREAD_PRIORITY_BACKGROUND);
        }
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
       }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MyBackgroundService","onStartCommand");
        if(mTimer!=null){
            mTimer.cancel();
        }
        if(intent!=null) {
            MIN_MAGNITUDE = intent.getDoubleExtra(ARG_NOTIFY_MAGNITUDE, 5);
            NOTIFY_INTERVAL = intent.getIntExtra(ARG_NOTIFY_INTERVAL, 5);
            isServiceReCreate=intent.getBooleanExtra(ARG_RE_CREATE,false);
        }
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        return START_STICKY;
    }



    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            Log.e("MyBackgroundService","timer run");
            loadEQs();
        }

        private void  loadEQs() {

            try {
                String startDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC_MIN(NOTIFY_INTERVAL);
                //Service created from reboot , get last destroyed time
                if(isServiceReCreate && SettingsManager.getInstance().getLastServiceDestroyedTime()!=0){
                 startDate=Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC(SettingsManager.getInstance().getLastServiceDestroyedTime());
                 isServiceReCreate=false;
                }

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
                                notificationId= (int)Math.random();
                                sendNotification(body,title,notificationId);
                            }


                        }

                    }

                    @Override
                    public void onError(NetworkException ex) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

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
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        try {
            Log.e("MyBackgroundService","Service destroyed");
            SettingsManager.getInstance().setLastServiceDestroyedTime(Utils.getCurrentTime_UTC());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}