package com.example.stunting;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.stunting.account.AccountFragment;
import com.example.stunting.child.ChildFragment;
import com.example.stunting.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bnHome = findViewById(R.id.bnHome);

        // Default fragment
        openFragment(new HomeFragment());

        bnHome.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    openFragment(new HomeFragment());
                    return true;
                case R.id.nav_child:
                    openFragment(new ChildFragment());
                    return true;
                case R.id.nav_account:
                    openFragment(new AccountFragment());
                    return true;
            }
            return false;
        });

    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.flHome, fragment).commit();
    }
}