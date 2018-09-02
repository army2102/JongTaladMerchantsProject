package com.example.teerasaksathu.customers.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.teerasaksathu.customers.R;
import com.example.teerasaksathu.customers.fragment.LoginFragment;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = getSharedPreferences("user_token", Context.MODE_PRIVATE);
        int loginToken = prefs.getInt("logined",0);

        if(loginToken != 0){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("merchantId", loginToken);
            startActivity(intent);
            finish();
        }

        else if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, LoginFragment.newInstance())
                    .commit();
        }
    }
}
