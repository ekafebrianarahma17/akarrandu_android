package com.project.appakarrandu;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//
//        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this code will pause the app for 1.5 sec and then any thing in run method will run.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                boolean isLoggedIn = userPref.getBoolean("isLoggedIn", false);

                if (isLoggedIn){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(MainActivity.this,AuthActivity.class));
                    finish();
                }
            }
        },1500);
    }

//    private void isFirstTime() {
//        SharedPreferences preferences = getApplication().getSharedPreferences("Dashboard", Context.MODE_PRIVATE);
//        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);
//        if (isFirstTime){
//            SharedPreferences.Editor editor= preferences.edit();
//            editor.apply();
//
//            //start onBoard activity
//            startActivity(new Intent(MainActivity.this,DashboardActivity.class));
//            finish();
//        }else{
//            //start Auth Activity
//            startActivity(new Intent(MainActivity.this,AuthActivity.class));
//            finish();
//        }
//
//    }
}