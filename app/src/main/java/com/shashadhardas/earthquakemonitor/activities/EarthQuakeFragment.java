package com.shashadhardas.earthquakemonitor.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shashadhardas.earthquakemonitor.R;
import com.shashadhardas.earthquakemonitor.adapter.GenericListAdapter;
import com.shashadhardas.earthquakemonitor.application.EarthQuakeApplication;
import com.shashadhardas.earthquakemonitor.databinding.EarthquakeScheduleBinding;
import com.shashadhardas.earthquakemonitor.model.EarthQuake;
import com.shashadhardas.earthquakemonitor.net.NetworkException;
import com.shashadhardas.earthquakemonitor.utils.RecyclerViewClickListener;
import com.shashadhardas.earthquakemonitor.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//*** Created by shashadhar on 05-02-2017.*/
public class EarthQuakeFragment extends Fragment implements RecyclerViewClickListener, GenericListAdapter.MyViewHolder {

    private GenericListAdapter<EarthQuake.Feature> adapter;
    public static String ARG_MAIN_TAG = "ARG_MAIN_TAG";
    String tag;
    static final String REFRESH_LIST = "REFRESH_LIST";
    EarthquakeScheduleBinding binding;
    private int maxNoOfHourToFetch = -24;
    private  int lastHourFetch = 0;
    private List<EarthQuake.Feature> EQs = new ArrayList<>();

    public static EarthQuakeFragment newInstance(String mainTag) {
        EarthQuakeFragment fragment = new EarthQuakeFragment();
        fragment.tag = mainTag;
        Bundle args = new Bundle();
        args.putString(ARG_MAIN_TAG, mainTag);
        fragment.setArguments(args);
        fragment.hasOptionsMenu();
        return fragment;
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equals(EarthQuakeApplication.ACTION_NETWORK_CONNECTION_CHANGED)) {
                    if (Utils.isNetworkAvailable(getActivity())) {
                        refreshEarthQuakes();
                    } else {

                    }
                }
                if (intent.getAction().equals(REFRESH_LIST)) {
                    if (Utils.isNetworkAvailable(getActivity())) {
                        refreshEarthQuakes();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void refreshEarthQuakes() {
        switch (tag.toUpperCase()) {
            case EarthQuakeMainFragment.ARG_TAG_2_5: {
                lastHourFetch = lastHourFetch - 3;
                String startDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC(lastHourFetch);
                String endDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC();
                updatePagerTitle();
                refreshList(startDate, endDate, 2.5);
            }
            break;

            case EarthQuakeMainFragment.ARG_TAG_4_5: {
                lastHourFetch = lastHourFetch - 3;
                String startDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC(lastHourFetch);
                String endDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC();
                updatePagerTitle();
                refreshList(startDate, endDate, 4.5);
            }
            break;

            case EarthQuakeMainFragment.ARG_TAG_1_0: {
                lastHourFetch = lastHourFetch - 1;
                String startDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC(lastHourFetch);
                String endDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC();
                updatePagerTitle();
                refreshList(startDate, endDate, 1.0);
            }
            break;

            case EarthQuakeMainFragment.ARG_TAG_ALL: {
                lastHourFetch = lastHourFetch - 1;
                String startDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC(lastHourFetch);
                String endDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC();
                updatePagerTitle();
                refreshList(startDate, endDate, 0);
            }
            break;

            case EarthQuakeMainFragment.ARG_TAG_SIGNIFICANT: {
                lastHourFetch = lastHourFetch - 6;
                String startDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC(lastHourFetch);
                String endDate = Utils.getDateInYYYY_MM_DD_T_HH_MM_SS_UTC();
                updatePagerTitle();
                refreshList(startDate, endDate, 5);
            }
            break;
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.home, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if(item.getItemId()==R.id.action_refresh){
                lastHourFetch=0;
                refreshEarthQuakes();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void updatePagerTitle() {
        try {
            if(getParentFragment()!=null && getParentFragment() instanceof EarthQuakeMainFragment){
                String title="Last %s Hour";
                title=String.format(Locale.ENGLISH,title,Math.abs(lastHourFetch));
                ((EarthQuakeMainFragment)getParentFragment()).getAdapter().updateTitle(title,0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  int getNextHourToFetch(){

        switch (tag.toUpperCase()) {
            case EarthQuakeMainFragment.ARG_TAG_2_5: {
                return  (lastHourFetch - 3);
            }
            case EarthQuakeMainFragment.ARG_TAG_4_5: {
                return  (lastHourFetch - 3);
            }
            case EarthQuakeMainFragment.ARG_TAG_1_0: {
                return  (lastHourFetch - 1);
            }
            case EarthQuakeMainFragment.ARG_TAG_ALL: {
                return  (lastHourFetch - 1);
            }
            case EarthQuakeMainFragment.ARG_TAG_SIGNIFICANT: {
                return  (lastHourFetch - 6);
            }
        }
        return  lastHourFetch - 1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setHasOptionsMenu(true);
            IntentFilter filter = new IntentFilter(EarthQuakeApplication.ACTION_NETWORK_CONNECTION_CHANGED);
            filter.addAction(REFRESH_LIST);
            getActivity().registerReceiver(broadcastReceiver, filter);
        } catch (Exception e) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            binding = DataBindingUtil.inflate(inflater, R.layout.earthquake_schedule, container, false);
            tag = getArguments().getString(ARG_MAIN_TAG);
            binding.earthquakeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.ribbon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    refreshEarthQuakes();
                }
            });
            updateData();
            return binding.getRoot();
        } catch (Exception e) {
            return new View(inflater.getContext());
        }
    }

    private void setUpAllEarthQuakesAndListener() {
        try {
            if (EQs != null && EQs.size() > 0) {
                if (adapter == null) {
                    adapter = new GenericListAdapter(EQs, EarthQuakeFragment.this, EarthQuakeFragment.this);
                }
                binding.earthquakeRecyclerView.setAdapter(adapter);
            }
            binding.noResultsTxt.setText("No Earthquakes");
            refreshEarthQuakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void showLoading() {
        try {
            binding.progressBarBlock.setVisibility(View.VISIBLE);
            binding.noEQ.setVisibility(View.GONE);
            binding.earthquakeRecyclerView.setVisibility(View.GONE);
        } catch (Exception e) {

        }
    }

    void showNoEQ() {
        try {
            binding.progressBarBlock.setVisibility(View.GONE);
            binding.noEQ.setVisibility(View.VISIBLE);
            if(lastHourFetch>=maxNoOfHourToFetch){
                binding.loadMoreLt.setVisibility(View.VISIBLE);
                String msg="Last %s hour loaded.Click to load last %s hour";
                msg= String.format(msg, Math.abs(lastHourFetch),  Math.abs(getNextHourToFetch()));
                binding.noOfDays.setText(msg);

            }else{
                binding.loadMoreLt.setVisibility(View.GONE);
            }
            binding.earthquakeRecyclerView.setVisibility(View.GONE);
        } catch (Exception e) {

        }
    }

    void showEQs() {
        try {
            if (EQs != null && EQs.size() > 0) {
                binding.noEQ.setVisibility(View.GONE);
                binding.earthquakeRecyclerView.setVisibility(View.VISIBLE);
            } else {
                binding.noEQ.setVisibility(View.VISIBLE);
                binding.earthquakeRecyclerView.setVisibility(View.GONE);
            }
            binding.progressBarBlock.setVisibility(View.GONE);
            if (EQs != null && EQs.size() > 0) {
                if (adapter == null) {
                    adapter = new GenericListAdapter(EQs, EarthQuakeFragment.this, EarthQuakeFragment.this);
                }
                binding.earthquakeRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    public  void updateData() {
        try {
            setUpAllEarthQuakesAndListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void refreshList(String startDate, String endDate, double magnitude) {
        try {
            showLoading();
            EarthQuakeDataLoader.getEarthQuake(startDate, endDate, magnitude, new EarthQuakeDataLoader.IEventsListener() {
                @Override
                public void EQLoaded(List<EarthQuake.Feature> features) {
                    try {
                        if (features != null && features.size() > 0) {
                            if (lastHourFetch > maxNoOfHourToFetch) {
                                EarthQuake.Feature loadMoreFeature = new EarthQuake.Feature();
                                loadMoreFeature.setType("LOADMORE");
                                loadMoreFeature.setLastHourFetched(lastHourFetch);
                                loadMoreFeature.setNextHourToFetch(lastHourFetch);
                                features.add(loadMoreFeature);
                            }
                            EQs.clear();
                            EQs.addAll(features);
                            showEQs();
                        } else {
                            showNoEQ();
                        }
                    } catch (Exception e) {
                        showNoEQ();
                    }
                }


                @Override
                public void onError(NetworkException ex) {
                    ((BaseActivity) getActivity()).displayError("We are not able to complete your request now");
                    showNoEQ();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            showEQs();
        }
    }

    @Override
    public View onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        try {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eq, parent, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onBindView(Object item, final GenericListAdapter.ViewHolder view, final int position) {
        try {
            final GenericListAdapter.ViewHolder mView = view;
            final int itemPosition = position;
            EarthQuake.Feature featureItem = (EarthQuake.Feature) item;
            try {
                if (featureItem.getType().equals("LOADMORE")) {
                    view.getView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View mView) {
                            view.mListener.onClick(mView,itemPosition);
                        }
                    });
                    mView.getView().findViewById(R.id.mainLayout).setVisibility(View.GONE);
                    mView.getView().findViewById(R.id.loadMoreLt).setVisibility(View.VISIBLE);
                    String msg="Last %s hour loaded.Click to load last %s hour";
                    msg= String.format(msg, Math.abs(lastHourFetch),  Math.abs(getNextHourToFetch()));
                    ((TextView) mView.getView().findViewById(R.id.noOfDays)).setText(msg);

                     } else {
                    mView.getView().findViewById(R.id.mainLayout).setVisibility(View.VISIBLE);
                    mView.getView().findViewById(R.id.loadMoreLt).setVisibility(View.GONE);
                    ((TextView) mView.getView().findViewById(R.id.place)).setText(featureItem.getProperties().getPlace());
                    ((TextView) mView.getView().findViewById(R.id.magnitude)).setText(String.valueOf(featureItem.getProperties().getMag()));
                    try {
                        String UTCTime = Utils.getDateInMMMDDYYYYHHMMSSA(featureItem.getProperties().getTime());
                        String ISTTime = Utils.getDateInMMMDDYYYYHHMMSSA_IST(featureItem.getProperties().getTime());
                        ((TextView) mView.getView().findViewById(R.id.timeUTC)).setText(UTCTime + "(UTC)");
                        ((TextView) mView.getView().findViewById(R.id.timeIST)).setText(ISTTime + "(IST)");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        String lat = String.valueOf(featureItem.getGeometry().getCoordinates().get(1));
                        String lon = String.valueOf(featureItem.getGeometry().getCoordinates().get(0));
                        ((Button) mView.getView().findViewById(R.id.latlon)).setText("Lat:" + lat + " " + "Lon:" + lon);
                         mView.getView().findViewById(R.id.latlon).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View mView) {
                                view.mListener.onClick(mView,itemPosition);

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ((Button) mView.getView().findViewById(R.id.details)).setText(featureItem.getProperties().getDetail());

                    mView.getView().findViewById(R.id.details).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View mView) {
                            view.mListener.onClick(mView,itemPosition);

                        }
                    });
                }
                }catch(NumberFormatException e){
                    e.printStackTrace();
                }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view, int position) {
        try {
            EarthQuake.Feature feature=adapter.getItem(position);
            if(feature!=null && feature.getType().equals("LOADMORE")){
                    refreshEarthQuakes();
            }else {
                if (feature != null) {
                    switch (view.getId()) {
                        case R.id.details: {
                            Utils.showBrowser(feature.getProperties().getDetail(), getContext());
                        }
                        break;

                        case R.id.latlon: {
                            double lat = feature.getGeometry().getCoordinates().get(1);
                            double lon = feature.getGeometry().getCoordinates().get(0);
                            Utils.navigateToMapActivity(getContext(), lat, lon, feature.getProperties().getPlace());
                        }
                        break;

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}









