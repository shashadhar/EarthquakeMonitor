package com.shashadhardas.earthquakemonitor.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.shashadhardas.earthquakemonitor.application.EarthQuakeApplication;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Created by shashadhar on 05-02-2017.
 */
public class Utils {



    public  static String getDateInMMMDDYYYYHHMMSSA(long timeInMillis){
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal.setTimeInMillis(timeInMillis);
            DateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
            df.setTimeZone(cal.getTimeZone());
            df.setCalendar(cal);
            Date dt = cal.getTime();
            String date_str = df.format(dt);
            return date_str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public  static String getDateInMMMDDYYYYHHMMSSA_IST(long timeInMillis){
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
            cal.setTimeInMillis(timeInMillis);
            DateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
            df.setTimeZone(cal.getTimeZone());
            df.setCalendar(cal);
            Date dt = cal.getTime();
            String date_str = df.format(dt);
            return date_str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public  static long getCurrentTime_UTC(){
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            return cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis();
        }
    }


    public  static String getDateInYYYY_MM_DD_T_HH_MM_SS_UTC(long prevTime){
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal.setTimeInMillis(prevTime);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            df.setTimeZone(cal.getTimeZone());
            df.setCalendar(cal);
            Date dt = cal.getTime();
            String date_str = df.format(dt);
            return date_str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public  static String getDateInYYYY_MM_DD_T_HH_MM_SS_UTC(){
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            df.setTimeZone(cal.getTimeZone());
            df.setCalendar(cal);
            Date dt = cal.getTime();
            String date_str = df.format(dt);
            return date_str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public  static String getDateInYYYY_MM_DD_T_HH_MM_SS_UTC(int hour){
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal.add(Calendar.HOUR,hour);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            df.setTimeZone(cal.getTimeZone());
            df.setCalendar(cal);
            Date dt = cal.getTime();
            String date_str = df.format(dt);
            return date_str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public  static String getDateInYYYY_MM_DD_T_HH_MM_SS_UTC_MIN(int min){
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal.add(Calendar.MINUTE,-min);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            df.setTimeZone(cal.getTimeZone());
            df.setCalendar(cal);
            Date dt = cal.getTime();
            String date_str = df.format(dt);
            return date_str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public  static String getDateInMMMDDYYYY(long timeInMillis){
        try {
            Calendar cal= Calendar.getInstance();
            cal.setTimeInMillis(timeInMillis);
            DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
            df.setCalendar(cal);
            Date dt = cal.getTime();
            String date_str = df.format(dt);
            return date_str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isNetworkAvailable(final Context context) {
        try {
            final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static  void allotEachTabWithEqualWidth(TabLayout tabs){

        try {
            ViewGroup slidingTabStrip = (ViewGroup) tabs.getChildAt(0);
            for (int i = 0; i < tabs.getTabCount(); i++) {
                View tab = slidingTabStrip.getChildAt(i);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();
                layoutParams.weight = 1;
                tab.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void navigateToMapActivity(Context ct,double lat,double lon, String address) {
        try {

           //Mark place
           if(lat!=0 && lon!=0) {
               String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%f,%f(%s)", lat, lon, address);
               Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
               mapIntent.setPackage("com.google.android.apps.maps");
               if (mapIntent.resolveActivity(ct.getPackageManager()) != null) {
                   ct.startActivity(mapIntent);
               }
           }

          //Navigate user
         /*   if(lat!=0 && lon!=0) {
                //Uri gmmIntentUri = Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia");
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+String.valueOf(lat)+","+String.valueOf(lon));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                ct.startActivity(mapIntent);
            }else{
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+address);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                ct.startActivity(mapIntent);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showBrowser(String url,Context context) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
