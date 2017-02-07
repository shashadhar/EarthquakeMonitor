package com.shashadhardas.earthquakemonitor.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.shashadhardas.earthquakemonitor.R;
import com.shashadhardas.earthquakemonitor.databinding.ActivityHomeBinding;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
      ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displayView();
    }

    public  void displayView(){
        try {
            onNavigationItemSelected(binding.navView.getMenu().findItem(R.id.nav_significantEQ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.home, menu);
       return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        ActionBar bar = getSupportActionBar();
        int id = item.getItemId();

        if (id == R.id.nav_significantEQ) {
            // Handle the camera action
            fragmentManager.beginTransaction().replace(R.id.content_fragment, EarthQuakeMainFragment.newInstance(EarthQuakeMainFragment.ARG_TAG_SIGNIFICANT)).commit();
            bar.setTitle("Significant Earthquakes");
        } else if (id == R.id.nav_m4_5EQ) {
            fragmentManager.beginTransaction().replace(R.id.content_fragment, EarthQuakeMainFragment.newInstance(EarthQuakeMainFragment.ARG_TAG_4_5)).commit();
            bar.setTitle("M4.5+ Earthquakes");
        } else if (id == R.id.nav_m2_5EQ) {
            fragmentManager.beginTransaction().replace(R.id.content_fragment, EarthQuakeMainFragment.newInstance(EarthQuakeMainFragment.ARG_TAG_2_5)).commit();
            bar.setTitle("M2.5+ Earthquakes");
        } else if (id == R.id.nav_m1_0EQ) {
            fragmentManager.beginTransaction().replace(R.id.content_fragment, EarthQuakeMainFragment.newInstance(EarthQuakeMainFragment.ARG_TAG_1_0)).commit();
            bar.setTitle("M1.0+ Earthquakes");
        } else if (id == R.id.nav_allEQ) {
            fragmentManager.beginTransaction().replace(R.id.content_fragment, EarthQuakeMainFragment.newInstance(EarthQuakeMainFragment.ARG_TAG_ALL)).commit();
            bar.setTitle("All Earthquakes");

        }else if (id == R.id.nav_settings) {
            fragmentManager.beginTransaction().replace(R.id.content_fragment, Settings.newInstance()).commit();
            bar.setTitle("Settings");

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
