package com.stuntech.stunting.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.stuntech.stunting.BuildConfig;
import com.stuntech.stunting.R;

public class SplashScreen extends AppCompatActivity {
    Animation app_splash, btt;
    ImageView app_logo;
    TextView app_subtitle;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Session
        sharedPref = this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        // load element
        app_logo = findViewById(R.id.app_logo_splash);

        // run animation
        app_logo.startAnimation(app_splash);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            // merubah activity ke activity lain
            String ID = sharedPref.getString(getString(R.string.id), "");
            if (ID.equals("")) {
                Intent gogetstarted = new Intent(SplashScreen.this, GetStartedActivity.class);
                startActivity(gogetstarted);
                finish();
            } else {
                Intent gomain = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(gomain);
                finish();
            }
        }, 2000); // 2000 ms = 2s
    }
}