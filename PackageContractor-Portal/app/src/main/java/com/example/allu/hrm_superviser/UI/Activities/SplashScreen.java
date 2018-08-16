package com.example.allu.hrm_superviser.UI.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.allu.hrm_superviser.R;
import com.example.allu.hrm_superviser.utils.DataAttributes;
import com.example.allu.hrm_superviser.utils.Utils;


public class SplashScreen extends AppCompatActivity {

    String TAG = "SplashScreen";
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        utils = new Utils(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PostTime();
            }
        }, DataAttributes.SPLASH_SCREEN_DISPLAY_TIME);
    }

    void PostTime(){
        if(utils.LoginStatus()){
            utils.Goto(HomeActivity.class);
        }else{
            utils.Goto(LoginActivity.class);
        }
    }
}
