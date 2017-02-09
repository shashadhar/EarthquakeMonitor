package com.shashadhardas.earthquakemonitor.application;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.shashadhardas.earthquakemonitor.activities.Settings;
import com.shashadhardas.earthquakemonitor.service.MyBackgroundService;
import com.shashadhardas.earthquakemonitor.utils.AlarmReceiver;
import com.shashadhardas.earthquakemonitor.utils.SettingsManager;

/**
 * Created by shashadhar on 05-02-2017.
 */

public class EarthQuakeApplication extends Application {
  private static  EarthQuakeApplication instance=null;
    public static final String ACTION_NETWORK_CONNECTION_CHANGED = "com.positra.provider.ACTION_NETWORK_CONNECTION_CHANGED";
   ActivityLifecycleCallbacks activityLifecycleCallbacks=new ActivityLifeCycleCallback();

  public static EarthQuakeApplication getInstance (){
     return instance;
  }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        SettingsManager.getInstance(this);
        //startService();
        if(!SettingsManager.getInstance().getIsAlarmSet()) {
            startAlarm();
        }
    }


    public void startAlarm(){
        try {
            cancelAlarm();
            scheduleAlarm();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  void startService(){
        try {
            Intent intent=new Intent(this, MyBackgroundService.class);
            intent.putExtra(MyBackgroundService.ARG_NOTIFY_INTERVAL,SettingsManager.getInstance().getDefaultInterval());
            intent.putExtra(MyBackgroundService.ARG_NOTIFY_MAGNITUDE, SettingsManager.getInstance().getDefaultMagnitude());
            startService(intent);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void scheduleAlarm() {
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                (SettingsManager.getInstance().getDefaultInterval()*60*1000), pIntent);
        SettingsManager.getInstance().setIsAlarmSet(true);
    }

    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
        SettingsManager.getInstance().setIsAlarmSet(false);
    }

}
