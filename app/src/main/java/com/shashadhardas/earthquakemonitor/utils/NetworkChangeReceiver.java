package com.shashadhardas.earthquakemonitor.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.shashadhardas.earthquakemonitor.application.EarthQuakeApplication;

/**
 * Created by shashadhar on 05-02-2017.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        EarthQuakeApplication.getInstance().sendBroadcast(new Intent(EarthQuakeApplication.ACTION_NETWORK_CONNECTION_CHANGED));
    }
}
