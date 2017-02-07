package com.shashadhardas.earthquakemonitor.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shashadhardas.earthquakemonitor.application.EarthQuakeApplication;
import com.shashadhardas.earthquakemonitor.service.MyBackgroundService;

/**
 * Created by shashadhar on 05-02-2017.
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Intent intentService=new Intent(context, MyBackgroundService.class);
            intent.putExtra(MyBackgroundService.ARG_RE_CREATE,true);
            context.startService(intentService);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
