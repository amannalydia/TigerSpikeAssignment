package com.tigerspike.assignment.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tigerspike.assignment.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    private Timer splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splash = new Timer();
        //create Timer to run for 3sec
        splash.schedule(new Splash(),3000);
    }

    private class Splash extends TimerTask {

        @Override
        public void run() {
            startActivity(new Intent(SplashScreenActivity.this,FlickrFeedActivity.class));
            splash.cancel();
            splash.purge();
            finish();

        }
    }
}
