package com.example.stunting.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stunting.R;

public class SplashScreen extends AppCompatActivity {
    Animation app_splash, btt;
    ImageView app_logo;
    TextView app_subtitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        // load element
        app_logo = findViewById(R.id.app_logo_splash);

        // run animation
        app_logo.startAnimation(app_splash);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            // merubah activity ke activity lain
            Intent gogetstarted = new Intent(SplashScreen.this, GetStartedActivity.class);
            startActivity(gogetstarted);
            finish();
        }, 2000); // 2000 ms = 2s
    }
}