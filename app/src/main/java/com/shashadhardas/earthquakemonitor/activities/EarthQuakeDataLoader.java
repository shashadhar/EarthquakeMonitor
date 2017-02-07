package com.shashadhardas.earthquakemonitor.activities;

import com.shashadhardas.earthquakemonitor.application.EarthQuakeApplication;
import com.shashadhardas.earthquakemonitor.model.EarthQuake;
import com.shashadhardas.earthquakemonitor.net.NetworkException;
import com.shashadhardas.earthquakemonitor.net.NetworkResponse;
import com.shashadhardas.earthquakemonitor.net.VolleyCommunicator;

import java.util.List;

/**
 * Created by shashadhar on 05-02-2017.
 */

public class EarthQuakeDataLoader {
    private static final VolleyCommunicator comm = new VolleyCommunicator(EarthQuakeApplication.getInstance());
    private static  String TAG="EarthQuakeDataLoader";

    public interface IEventsListener {
        void EQLoaded(List<EarthQuake.Feature> features);
        void onError(NetworkException ex);
    }

    public  static void getEarthQuake(String startDate,String endDate,Double magnitude, final IEventsListener listener) {
        try {

            comm.getEarthQuakeList(startDate, endDate, magnitude, new NetworkResponse.Listener() {
                boolean respondedDataFromFile = false;

                @Override
                public void onResponse(Object o) {
                    try {
                        List<EarthQuake.Feature> EQs = (List<EarthQuake.Feature>) o;
                        if (!respondedDataFromFile) {
                            if (listener != null) {
                                listener.EQLoaded(EQs);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new NetworkResponse.ErrorListener() {
                @Override
                public void onError(NetworkException error) {
                   listener.onError(error);

                }
            },TAG);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError(new NetworkException("Error in loading earthquake data from server"));
        }
    }
}
