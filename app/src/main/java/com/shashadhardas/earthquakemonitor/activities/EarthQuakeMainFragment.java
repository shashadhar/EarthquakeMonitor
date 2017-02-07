package com.shashadhardas.earthquakemonitor.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shashadhardas.earthquakemonitor.R;
import com.shashadhardas.earthquakemonitor.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shashadhar on 05-02-2017.
 */
public class EarthQuakeMainFragment extends Fragment {

    public static final String ARG_TAG_ALL = "ARG_TAG_ALL";
    public static final String ARG_TAG_SIGNIFICANT = "ARG_TAG_SIGNIFICANT";
    public static final String ARG_TAG_2_5 = "ARG_TAG_2_5";
    public static final String ARG_TAG_1_0 = "ARG_TAG_1_0";
    public static final String ARG_TAG_4_5 = "ARG_TAG_4_5";
    public static final String ARG_TAG = "ARG_TAG";

    TabLayout tabs;
    ViewPager appointmentPager;
    EarthQuakeFragment pastHourEQ;
    int position = 0;
    String tag;
    ViewPagerAdapter adapter;


    public static EarthQuakeMainFragment newInstance(String tag){
        EarthQuakeMainFragment fragment = new EarthQuakeMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TAG,tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.earthquake_main_lt, container, false);
            appointmentPager=(ViewPager)rootView.findViewById(R.id.appointments_pager) ;
            if(getArguments()!=null){
                tag=getArguments().get(ARG_TAG).toString();
            }
            tabs = (TabLayout)rootView.findViewById(R.id.tabs);
            adapter = new ViewPagerAdapter(getChildFragmentManager());
            pastHourEQ =EarthQuakeFragment.newInstance(tag);
            adapter.addFragment(pastHourEQ, getString(R.string.past_hour));
            appointmentPager.setAdapter(adapter);
            tabs.setupWithViewPager(appointmentPager);
            appointmentPager.setCurrentItem(position);
            Utils.allotEachTabWithEqualWidth(tabs);
            return  rootView;
        } catch (Exception e) {
            e.printStackTrace();
            return  new RelativeLayout(getActivity());
        }
    }

    public  ViewPagerAdapter getAdapter(){
        return adapter;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public  void updateTitle(String title,int position){
            try {
                mFragmentTitleList.add(position,title);
                notifyDataSetChanged();
                Utils.allotEachTabWithEqualWidth(tabs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
