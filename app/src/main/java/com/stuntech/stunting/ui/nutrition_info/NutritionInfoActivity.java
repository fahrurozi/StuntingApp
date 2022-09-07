package com.stuntech.stunting.ui.nutrition_info;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.stuntech.stunting.R;
import com.stuntech.stunting.ui.care_nutrition.CareCategoryActivity;

public class NutritionInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_info);

        CardView btnProtein, btnCarbo, btnFat;

        btnProtein = findViewById(R.id.btnProtein);
        btnCarbo = findViewById(R.id.btnCarbo);
        btnFat = findViewById(R.id.btnFat);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        btnProtein.setOnClickListener(v -> {
            startActivity(new Intent(this, NutritionInfoCategoryActivity.class).putExtra("category", "protein"));
        });

        btnCarbo.setOnClickListener(v -> {
            startActivity(new Intent(this, NutritionInfoCategoryActivity.class).putExtra("category", "karbohidrat"));
        });

        btnFat.setOnClickListener(v -> {
            startActivity(new Intent(this, NutritionInfoCategoryActivity.class).putExtra("category", "lemak"));
        });
    }
}
