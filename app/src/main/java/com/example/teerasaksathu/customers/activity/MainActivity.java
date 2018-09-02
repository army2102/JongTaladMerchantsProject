package com.example.teerasaksathu.customers.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.teerasaksathu.customers.R;
import com.example.teerasaksathu.customers.fragment.MarketListFragment;
import com.example.teerasaksathu.customers.fragment.MyProfileFragment;
import com.example.teerasaksathu.customers.fragment.ReportFragment;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public static Intent intent;
    private Button btnMarketList;
    private Button btnProfile;
    private Button btnReport;
    private Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MarketListFragment.newInstance())
                    .commit();
        }

        initInstances();


    }


    public void initInstances() {
        intent = getIntent();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setTitle("Jongtalad");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnMarketList = findViewById(R.id.btnMarketList);
        btnProfile = findViewById(R.id.btnProfile);
        btnReport = findViewById(R.id.btnReport);
        btnLogout = findViewById(R.id.btnLogout);

        btnMarketList.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
        btnReport.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentContainer);

        if (view == btnMarketList) {
            if (!(fragment instanceof MarketListFragment)) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.from_right, R.anim.to_left,
                                R.anim.from_left, R.anim.to_right
                        )
                        .replace(R.id.contentContainer, MarketListFragment.newInstance())
                        .commit();
                drawerLayout.closeDrawers();
            }
        } else if (view == btnProfile) {
            if (!(fragment instanceof MyProfileFragment)) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.from_right, R.anim.to_left,
                                R.anim.from_left, R.anim.to_right
                        )
                        .replace(R.id.contentContainer, MyProfileFragment.newInstance())
                        .commit();
                drawerLayout.closeDrawers();
            }
        } else if (view == btnReport) {
            if (!(fragment instanceof ReportFragment)) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.from_right, R.anim.to_left,
                                R.anim.from_left, R.anim.to_right
                        )
                        .replace(R.id.contentContainer, ReportFragment.newInstance())
                        .commit();
                drawerLayout.closeDrawers();
            }
        } else if (view == btnLogout) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("logined");
            SharedPreferences prefs = getSharedPreferences("user_token", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("logined", 0);
            editor.apply();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
