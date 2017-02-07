package com.shashadhardas.earthquakemonitor.net.volley;


import com.shashadhardas.earthquakemonitor.net.NetworkException;

/**
 * Created by anutosh on 25-05-2016.
 */
public interface VolleyResponse {

    interface ApiListener {
        <T> void onResponse(T response);
    }

    interface ErrorListener{
        void onError(NetworkException error);
    }

    interface AuthErrorListener{
        void onAuthError();
    }
}
