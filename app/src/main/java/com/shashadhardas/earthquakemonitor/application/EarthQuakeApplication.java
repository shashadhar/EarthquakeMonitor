package com.shashadhardas.earthquakemonitor.application;

import android.app.Application;
import android.content.Intent;

import com.shashadhardas.earthquakemonitor.activities.Settings;
import com.shashadhardas.earthquakemonitor.service.MyBackgroundService;
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
        startService();
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

}
