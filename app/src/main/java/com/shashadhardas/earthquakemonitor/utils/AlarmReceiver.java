package com.shashadhardas.earthquakemonitor.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shashadhardas.earthquakemonitor.service.MyBackgroundService;
import com.shashadhardas.earthquakemonitor.service.MyIntentService;

/**
 * Created by shashadhar on 05-02-2017.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Intent startServiceIntent = new Intent(context, MyIntentService.class);
            context.startService(startServiceIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
