package com.ecommerce.ecommerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;


public class SplashAcitivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acitivity);


        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        if (hasLoggedIn) {
            //Go directly to main activity.

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    Intent i = new Intent(SplashAcitivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }
            }, SPLASH_TIME_OUT);
        } else {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent(SplashAcitivity.this, SigninActivity.class);
                    startActivity(i);
                    finish();

                }
            }, SPLASH_TIME_OUT);


        }

    }
}