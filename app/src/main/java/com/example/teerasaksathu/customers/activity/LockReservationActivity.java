package com.example.teerasaksathu.customers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.teerasaksathu.customers.R;
import com.example.teerasaksathu.customers.fragment.LockReservationFragment;

public class LockReservationActivity extends AppCompatActivity {
    public static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_reservation);
        initInstances();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, LockReservationFragment.newInstance())
                    .commit();
        }
    }

    private void initInstances() {
        intent = getIntent();
    }
}
