package com.stuntech.stunting.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.stuntech.stunting.R;
import com.stuntech.stunting.ui.account.AccountFragment;
import com.stuntech.stunting.ui.child.ChildFragment;
import com.stuntech.stunting.ui.child.management.ChildManagementFragment;
import com.stuntech.stunting.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stuntech.stunting.ui.account.AccountFragment;
import com.stuntech.stunting.ui.child.management.ChildManagementFragment;
import com.stuntech.stunting.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity implements MainInterface {

    BottomNavigationView bnHome;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnHome = findViewById(R.id.bnHome);

        bnHome.setOnItemSelectedListener(item -> openFragment(item.getItemId()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Default fragment
        openFragment(R.id.nav_home);
    }

    @SuppressLint("NonConstantResourceId")
    private boolean openFragment(Integer menuID) {
        switch (menuID) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flHome, new HomeFragment()).commit();
                return true;
            case R.id.nav_child:
//                getSupportFragmentManager().beginTransaction().replace(R.id.flHome, new ChildFragment()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.flHome, new ChildManagementFragment()).commit();
                return true;
            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.flHome, new AccountFragment()).commit();
                return true;
        }
        return false;
    }

    @Override
    public void openMenuNav(Integer menuID) {
        bnHome.setSelectedItemId(menuID);
        openFragment(menuID);
    }

}