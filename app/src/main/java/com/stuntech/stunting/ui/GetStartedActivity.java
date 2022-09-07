package com.stuntech.stunting.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.stuntech.stunting.R;
import com.stuntech.stunting.ui.login.LoginActivity;
import com.stuntech.stunting.ui.register.RegisterActivity;
import com.stuntech.stunting.ui.login.LoginActivity;
import com.stuntech.stunting.ui.register.RegisterActivity;

public class GetStartedActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        btnLogin = findViewById(R.id.btn_sign_in);
        btnRegister = findViewById(R.id.btn_new_account_create);

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}