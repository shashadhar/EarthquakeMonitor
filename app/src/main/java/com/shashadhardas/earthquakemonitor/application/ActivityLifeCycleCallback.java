package com.shashadhardas.earthquakemonitor.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.shashadhardas.earthquakemonitor.activities.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shashadhar on 05-02-2017..
 */
public class ActivityLifeCycleCallback implements Application.ActivityLifecycleCallbacks {

    private List<Activity> activities = new ArrayList<Activity>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activities.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activities.remove(activity);
    }

    public void clearBackstack(){
        try {
            for(Activity a : activities){
                if(!(a instanceof HomeActivity)) {
                    a.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearBackstackAll(){
        try {
            for(Activity a : activities){
                    a.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
