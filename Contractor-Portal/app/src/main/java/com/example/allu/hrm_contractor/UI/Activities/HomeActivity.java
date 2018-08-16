package com.example.allu.hrm_contractor.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.allu.hrm_contractor.R;
import com.example.allu.hrm_contractor.UI.Fragments.HomeFragment;
import com.example.allu.hrm_contractor.UI.Fragments.RequestFragment;
import com.example.allu.hrm_contractor.UI.Fragments.WorkerFragment;
import com.example.allu.hrm_contractor.utils.DataAttributes;
import com.example.allu.hrm_contractor.utils.Navigation_connector;
import com.example.allu.hrm_contractor.utils.Utils;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FRAGMENT_KEY = "fragment";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    private Utils utils;
    private int fragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utils = new Utils(this);
        initNavigationDrawer();

        Intent i = getIntent();
        if(i.hasExtra(DataAttributes.INTENT_FragmentId)){
            fragmentId = i.getIntExtra(DataAttributes.INTENT_FragmentId,R.id.nav_home);
        }else{
            if (savedInstanceState == null) {
                fragmentId = R.id.nav_home;
            } else {
                fragmentId = savedInstanceState.getInt(FRAGMENT_KEY);
            }
        }



        displaySelectedScreen(fragmentId);
    }

    private void initNavigationDrawer() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                openDrawer();
                invalidateOptionsMenu();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(FRAGMENT_KEY, fragmentId);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                utils.Goto(SettingActivity.class);
                return true;
            case R.id.action_logout:
                utils.Logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displaySelectedScreen(int itemId) {
        //creating fragment object
        Fragment fragment = null;
        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                toolbar.setTitle("Home");
                toolbar.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_workers:
                fragment = new WorkerFragment();
                toolbar.setTitle("Workers Activity");
                toolbar.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_viewrequest:
                fragment = new RequestFragment();
                toolbar.setTitle("Requests");
                toolbar.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_logout:
                utils.Logout();
                break;
            default:
                break;
        }
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
            navigationView.setCheckedItem(itemId);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        displaySelectedScreen(id);
        drawerLayout.closeDrawer(navigationView);
        return false;
    }

    public void openDrawer() {
        drawerLayout.openDrawer(navigationView);
    }

}
