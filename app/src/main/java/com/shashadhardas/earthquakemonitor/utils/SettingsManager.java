package com.shashadhardas.earthquakemonitor.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

/**
 * Created by shashadhar on 05-02-2017.
 */
public class SettingsManager {

    private static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "EarthquakePreference";
    private static final String LAST_SERVICE_DESTROYED_TIME="LAST_SERVICE_DESTROYED_TIME";
    private static final String LAST_UPDATED_START_TIME="LAST_UPDATED_START_TIME";
    private static final String LAST_UPDATED_END_TIME="LAST_UPDATED_END_TIME";
    private static final String DEFAULT_MAGNITUDE="DEFAULT_MAGNITUDE";
    private static final String DEFAULT_INTERVAL="DEFAULT_INTERVAL";
    private static final String IS_ALARM_SET="IS_ALARM_SET";


    private final SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private static SettingsManager instance = null;


    public static SettingsManager getInstance(Context mContext) {
        if (null == instance) {
            instance = new SettingsManager(mContext);
        }
        return instance;
    }

    public static SettingsManager getInstance() {
        if (null != instance) {
            return instance;
        }
        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
    }

    private SettingsManager(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }



    private void doEdit() {
        if (null == editor) {
            editor = sharedPreferences.edit();
        }
    }

    private void doCommit() {
        if (null != editor) {
            editor.commit();
            editor = null;
        }
    }

    public long getLastServiceDestroyedTime() {
        try {
            return sharedPreferences.getLong(LAST_SERVICE_DESTROYED_TIME, 0);
        } catch (Exception e) {
           return 0;
        }

    }

    public void setLastServiceDestroyedTime(long val) {
        try {
            doEdit();
            editor.putLong(LAST_SERVICE_DESTROYED_TIME, val);
            doCommit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getDefaultMagnitude() {
        try {
            return getDouble(sharedPreferences,DEFAULT_MAGNITUDE,5);
        } catch (Exception e) {
            return 0;
        }

    }

    public void setDefaultMagnitude(double val) {
        try {
            doEdit();
            editor=putDouble(editor,DEFAULT_MAGNITUDE,val);
            doCommit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getDefaultInterval() {
        try {
            return sharedPreferences.getInt(DEFAULT_INTERVAL, 5);
        } catch (Exception e) {
            return 0;
        }

    }

    public void setDefaultInterval(int val) {
        try {
            doEdit();
            editor.putInt(DEFAULT_INTERVAL, val);
            doCommit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getIsAlarmSet() {
        try {
            return sharedPreferences.getBoolean(IS_ALARM_SET, false);
        } catch (Exception e) {
            return false;
        }

    }

    public void setIsAlarmSet(Boolean val) {
        try {
            doEdit();
            editor.putBoolean(IS_ALARM_SET, val);
            doCommit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getLastUpdatedStartTime() {
        try {
            return sharedPreferences.getLong(LAST_UPDATED_START_TIME, 0);
        } catch (Exception e) {
            return 0;
        }

    }

    public void setLastUpdatedStartTime(long val) {
        try {
            doEdit();
            editor.putLong(LAST_UPDATED_START_TIME, val);
            doCommit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getLastUpdatedEndTime() {
        try {
            return sharedPreferences.getLong(LAST_UPDATED_END_TIME, 0);
        } catch (Exception e) {
            return 0;
        }

    }

    public void setLastUpdatedEndTime(long val) {
        try {
            doEdit();
            editor.putLong(LAST_UPDATED_END_TIME, val);
            doCommit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
