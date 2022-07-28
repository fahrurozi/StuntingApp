package com.example.stunting.ui.stunting_health;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.stunting.R;
import com.example.stunting.ui.info.StuntingInfoActivity;
import com.example.stunting.ui.info.StuntingInfoActivityBak;
import com.example.stunting.ui.nutrition_info.NutritionInfoActivity;

public class StuntingHealthActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stunting_health);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        CardView btnInfo, btnNutrition;

        btnInfo = findViewById(R.id.btn_info);
        btnNutrition = findViewById(R.id.btn_nutrition);

        btnInfo.setOnClickListener(v -> {
            startActivity(new Intent(this, StuntingInfoActivity.class));
        });

        btnNutrition.setOnClickListener(v -> {
            startActivity(new Intent(this, NutritionInfoActivity.class));
        });

    }

}
