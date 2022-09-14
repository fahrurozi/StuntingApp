package com.stuntech.stunting.ui.health_status;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.stuntech.stunting.R;
import com.stuntech.stunting.ui.MainActivity;
import com.stuntech.stunting.ui.hello_stunting.HelloStuntingActivity;
import com.stuntech.stunting.ui.info.StuntingInfoActivity;
import com.stuntech.stunting.ui.nutrition_info.NutritionInfoActivity;
import com.stuntech.stunting.ui.stunting_map.StuntingMapActivity;

public class HealthStatusReadMoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_status_readmore);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        CardView btnNutritionist, btnHealthFacilities;

        btnNutritionist = findViewById(R.id.btn_contact_nutritionist);
        btnHealthFacilities = findViewById(R.id.btn_health_facilities);

        btnNutritionist.setOnClickListener(v -> {
            startActivity(new Intent(this, HelloStuntingActivity.class));
        });

//        btnHealthFacilities.setOnClickListener(v -> {
//            startActivity(new Intent(this, .class));
//        });
//        btnHealthFacilities.setOnClickListener(v -> parent.openMenuNav(R.id.nav_child));
//        open maps fragment onclick


//        open fragment
//        btnNutritionist.setOnClickListener(v ->{
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//
//        });



    }
}

