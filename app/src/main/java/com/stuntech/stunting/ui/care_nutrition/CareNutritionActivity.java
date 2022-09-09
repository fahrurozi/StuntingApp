package com.stuntech.stunting.ui.care_nutrition;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.stuntech.stunting.R;

public class CareNutritionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_nutrition);

        CardView btnBreakfast, btnLunch, btnDinner;

        btnBreakfast = findViewById(R.id.btnBreakfast);
        btnLunch = findViewById(R.id.btnLunch);
        btnDinner = findViewById(R.id.btnDinner);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        btnBreakfast.setOnClickListener(v -> {
            startActivity(new Intent(this, CareCategoryActivity.class).putExtra("category", "breakfast"));
        });

        btnLunch.setOnClickListener(v -> {
            startActivity(new Intent(this, CareCategoryActivity.class).putExtra("category", "lunch"));
        });

        btnDinner.setOnClickListener(v -> {
            startActivity(new Intent(this, CareCategoryActivity.class).putExtra("category", "dinner"));
        });


    }
}