package com.shashadhardas.earthquakemonitor.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shashadhardas.earthquakemonitor.R;
import com.shashadhardas.earthquakemonitor.application.EarthQuakeApplication;
import com.shashadhardas.earthquakemonitor.utils.Utils;

/**
 * Created by shashadhar on 05-02-2017.
 */

public class BaseActivity extends AppCompatActivity {


    ProgressDialog dialog;
    Handler handler = new Handler();
    String errorMessage;
    String successMessage;
    static PopupWindow noInternetConnectivityDialog;

    Runnable displaySuccess = new Runnable() {

        @Override
        public void run() {
            displaySuccessDialog(successMessage);

        }
    };
    Runnable displayError = new Runnable() {

        @Override
        public void run() {
            displayErrorDialog(errorMessage);

        }
    };
    Runnable displayNoInternet = new Runnable() {

        @Override
        public void run() {
            displayNoConnectivityErrorDialog();

        }
    };

    public class CloseDialog implements Runnable {
        PopupWindow dialog;

        public CloseDialog(PopupWindow dialog) {
            this.dialog = dialog;
        }

        public void run() {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equals(EarthQuakeApplication.ACTION_NETWORK_CONNECTION_CHANGED)) {
                    showNetWorkError();
                }

            } catch (Exception e) {

            }
        }
    };

    public void showNetWorkError() {
        try {
            if (Utils.isNetworkAvailable(BaseActivity.this)) {
                if (noInternetConnectivityDialog != null) {
                    handler.postDelayed(new CloseDialog(noInternetConnectivityDialog), 300);
                }
            } else {
                handler.post(displayNoInternet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            IntentFilter intentFilter =new IntentFilter();
            intentFilter.addAction(EarthQuakeApplication.ACTION_NETWORK_CONNECTION_CHANGED);
            registerReceiver(networkChangeReceiver,intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        try {
            showNetWorkError();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(networkChangeReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public void showIndefiniteProgressDialog(String message) {
        try {
            dialog = ProgressDialog.show(this, null, message, true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelProgressDialog() {
        dialog.cancel();
    }



    void displaySuccessDialog(String message) {
        try {

            LayoutInflater inflater = (LayoutInflater) this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.success_ribbon, null);
            PopupWindow popup = new PopupWindow(layout,
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView messageView = (TextView) layout.findViewById(R.id.message);
            Spanned s1 = Html.fromHtml(message);
            final SpannableString s = new SpannableString(s1);
            /*Linkify.addLinks(s, Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES
                    | Linkify.PHONE_NUMBERS);*/
            messageView.setText(s);
            messageView.setMovementMethod(LinkMovementMethod.getInstance());
            popup.setAnimationStyle(R.style.PopupAnimation);
            popup.showAtLocation(getWindow().getDecorView().getRootView(),
                    Gravity.CENTER, 0, 0);

            handler.postDelayed(new CloseDialog(popup), 3000);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("BaseCompatActivity", "Error displaying dialog");
        }
    }

    void displayErrorDialog(String message) {

        try {

            LayoutInflater inflater = (LayoutInflater) this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.error_ribbon, null);
            PopupWindow popup = new PopupWindow(layout,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView messageView = (TextView) layout.findViewById(R.id.message);
            Spanned s1 = Html.fromHtml(message);
            final SpannableString s = new SpannableString(s1);
            Linkify.addLinks(s, Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES
                    | Linkify.PHONE_NUMBERS);
            messageView.setText(s);
            messageView.setMovementMethod(LinkMovementMethod.getInstance());

            popup.setAnimationStyle(R.style.PopupAnimation);
            popup.showAtLocation(getWindow().getDecorView().getRootView(),
                    Gravity.CENTER, 0, 0);

            handler.postDelayed(new CloseDialog(popup), 3000);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("BaseCompatActivity", "Error displaying dialog");
        }
    }

    void displayNoConnectivityErrorDialog() {

        try {

            LayoutInflater inflater = (LayoutInflater) this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.no_internect_connectivity_layout, null);
            if (noInternetConnectivityDialog == null) {
                noInternetConnectivityDialog = new PopupWindow(layout,
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                noInternetConnectivityDialog.setAnimationStyle(R.style.PopupAnimation);
                noInternetConnectivityDialog.showAtLocation(getWindow().getDecorView().getRootView(),
                        Gravity.BOTTOM, 0, 0);
                final View remove= layout.findViewById(R.id.remove_dialog);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.postDelayed(new CloseDialog(noInternetConnectivityDialog), 300);
                    }
                });

            } else {
                noInternetConnectivityDialog.dismiss();
                noInternetConnectivityDialog = null;
                noInternetConnectivityDialog = new PopupWindow(layout,
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                noInternetConnectivityDialog.setAnimationStyle(R.style.PopupAnimation);
                noInternetConnectivityDialog.showAtLocation(getWindow().getDecorView().getRootView(),
                        Gravity.BOTTOM, 0, 0);
                final View remove=layout.findViewById(R.id.remove_dialog);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.postDelayed(new CloseDialog(noInternetConnectivityDialog), 300);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("BaseCompatActivity", "Error displaying dialog");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
 /*       try {
            if(!(this instanceof MenuBaseActivity)) {
                getMenuInflater().inflate(R.menu.menu_home, menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    /*    try {
            if(item.getItemId()==R.id.action_positra_Home){
                ProviderApplication.getInstance().clearBackstack();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return super.onOptionsItemSelected(item);
    }

    public void displayError(String message) {
        this.errorMessage = message;
        handler.post(displayError);
    }

    public void displaySuccess(String message) {
        this.successMessage = message;
        handler.post(displaySuccess);
    }



}
