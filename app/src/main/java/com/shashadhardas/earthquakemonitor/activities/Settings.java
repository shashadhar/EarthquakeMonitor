package com.shashadhardas.earthquakemonitor.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.shashadhardas.earthquakemonitor.R;
import com.shashadhardas.earthquakemonitor.application.EarthQuakeApplication;
import com.shashadhardas.earthquakemonitor.databinding.FragmentSettingsBinding;
import com.shashadhardas.earthquakemonitor.utils.SettingsManager;


public class Settings extends Fragment {
    FragmentSettingsBinding binding;
    int magnitude;
    int interval;

    private OnFragmentInteractionListener mListener;

    public Settings() {
        // Required empty public constructor
    }

    public static Settings newInstance() {
        Settings fragment = new Settings();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_settings, container, false);
        binding.seekBarInterval.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                try {
                    binding.intervalValue.setText(String.valueOf(i)+"/"+String.valueOf(seekBar.getMax()));
                    interval=i;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.seekBarMag.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                try {
                    binding.magValue.setText(String.valueOf(i)+"/"+String.valueOf(seekBar.getMax()));
                    magnitude=i;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SettingsManager.getInstance().setDefaultInterval(interval);
                    SettingsManager.getInstance().setDefaultMagnitude(magnitude);
                    // EarthQuakeApplication.getInstance().startService();
                    EarthQuakeApplication.getInstance().startAlarm();
                    ((BaseActivity)getActivity()).displaySuccess("Data updated successfully and you will be notified with next earthquake.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        binding.seekBarMag.setProgress((int)SettingsManager.getInstance().getDefaultMagnitude());
        binding.seekBarInterval.setProgress(SettingsManager.getInstance().getDefaultInterval());
        return binding.getRoot();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        try {
            menu.findItem(R.id.action_refresh).setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
