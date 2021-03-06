package com.shashadhardas.earthquakemonitor.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.shashadhardas.earthquakemonitor.application.EarthQuakeApplication;
import com.shashadhardas.earthquakemonitor.service.MyBackgroundService;
import com.shashadhardas.earthquakemonitor.service.MyIntentService;

/**
 * Created by shashadhar on 05-02-2017.
 */
public class BootCompletedReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Intent startServiceIntent = new Intent(context, MyIntentService.class);
            startWakefulService(context, startServiceIntent);
            EarthQuakeApplication.getInstance().startAlarm();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
