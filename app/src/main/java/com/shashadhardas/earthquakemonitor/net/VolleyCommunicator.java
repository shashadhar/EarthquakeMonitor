package com.shashadhardas.earthquakemonitor.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.shashadhardas.earthquakemonitor.model.EarthQuake;
import com.shashadhardas.earthquakemonitor.net.volley.VolleyRequest;
import com.shashadhardas.earthquakemonitor.net.volley.VolleyResponse;
import com.shashadhardas.earthquakemonitor.net.volley.VolleyWorker;
import com.shashadhardas.earthquakemonitor.utils.AppProperties;

/**
 * Created by shashadhar on 05-02-2017.
 */
public class VolleyCommunicator {

    private final Context mCtx;

    public VolleyCommunicator(Context context) {
        this.mCtx = context;
    }
    private String getEndpointUrl(String endpoint) {
        String rootUrl = AppProperties.getInstance().getAppProperty("rooturl");
        return rootUrl + AppProperties.getInstance().getAppProperty(endpoint);
    }


    public  void getEarthQuakeList(String startTime,String endTime,double magnitude,final NetworkResponse.Listener listener, final NetworkResponse.ErrorListener errorListener, String tag) {
        try {
            String url = getEndpointUrl("query");
            url += "&" + "starttime=" + startTime;
            url += "&" + "endtime=" + endTime;
            url += "&" + "minmagnitude=" + magnitude;

            String requestBody = "";
            final VolleyRequest volleyRequest = new VolleyRequest();
            volleyRequest.url = url;
            volleyRequest.method = Request.Method.GET;
            volleyRequest.data = requestBody;
            volleyRequest.contentType = "application/json";
            volleyRequest.context = mCtx;
            volleyRequest.tag = tag;
            Log.e("Communicator","Url="+url);
            VolleyWorker.doNetworkOperation(volleyRequest,
                    new VolleyResponse.ApiListener() {
                        @Override
                        public <T> void onResponse(T response) {
                            try {
                                if (response != null) {
                                    EarthQuake EarthQuakes = (EarthQuake) response;
                                    listener.onResponse(EarthQuakes.getFeatures());

                                } else {
                                    listener.onResponse(null);
                                }
                            } catch (Exception e) {
                                listener.onResponse(null);
                            }
                        }
                    }, new VolleyResponse.ErrorListener() {
                        @Override
                        public void onError(NetworkException error) {
                            try {
                                errorListener.onError(error);
                            } catch (Exception e) {
                                NetworkException ex = new NetworkException(null, "Error in response");
                                errorListener.onError(ex);
                                e.printStackTrace();
                            }
                        }
                    },null, EarthQuake.class);
        } catch (Exception e) {
            NetworkException ex = new NetworkException(null, null);
            errorListener.onError(ex);
            e.printStackTrace();
        }
    }


}
