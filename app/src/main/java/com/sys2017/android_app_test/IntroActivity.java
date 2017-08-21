package com.sys2017.android_app_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        timer = new Timer();
        timer.schedule(timerTask,3000);

    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Intent intent = new Intent(IntroActivity.this,Intro2Activity.class);
            startActivity(intent);
            finish();
        }
    };

}
