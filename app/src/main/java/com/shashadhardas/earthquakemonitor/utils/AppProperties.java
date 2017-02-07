package com.shashadhardas.earthquakemonitor.utils;

import android.content.res.Resources;

import com.shashadhardas.earthquakemonitor.R;
import com.shashadhardas.earthquakemonitor.application.EarthQuakeApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Created by shashadhar on 05-02-2017.
 */
public class AppProperties extends Properties {

    private static final String DEFAULT_VALUE = "";

    private static AppProperties instance = null;

    public static AppProperties getInstance(){
        if(instance == null){
            instance = new AppProperties();
            instance.loadProperties();
        }
        return instance;
    }

    private AppProperties(){

    }

    private void loadProperties(){
        loadProperties(R.raw.app);
        loadProperties(R.raw.endpoint);
    }

    private void loadProperties(int resourceId){
        Resources resources = EarthQuakeApplication.getInstance().getApplicationContext().getResources();
        InputStream rawResource = resources.openRawResource(resourceId);
        try {
            load(rawResource);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getAppProperty(String key){
        return getProperty(key, DEFAULT_VALUE);
    }

    public String getOpenTokApiKey(){
        return getProperty("openTokApiKey", DEFAULT_VALUE);
    }

    public float getDefaultTimezoneOffset(){
        try{
            return Float.valueOf(getAppProperty("default_timezone"));
        }catch (Exception e){
            return 0;
        }
    }

    public String getMediaResourceRootPath(){
        try{
            return getAppProperty("app_media_resource_root_path");
        }catch (Exception e){
            return "";
        }
    }
}
