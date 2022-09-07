package com.stuntech.stunting.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stuntech.stunting.BuildConfig;
import com.stuntech.stunting.R;
import com.stuntech.stunting.data.model.login.ResponseLogin;
import com.stuntech.stunting.data.model.token.ResponseToken;
import com.stuntech.stunting.data.network.ApiEndpoint;
import com.stuntech.stunting.data.network.ApiService;
import com.stuntech.stunting.ui.MainActivity;
import com.stuntech.stunting.data.model.login.ResponseLogin;
import com.stuntech.stunting.data.model.token.ResponseToken;
import com.stuntech.stunting.data.network.ApiEndpoint;
import com.stuntech.stunting.data.network.ApiService;

import org.json.JSONException;
import org.json.JSONStringer;

import okhttp3.RequestBody;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etUsername, etPassword;

    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.button_submit_login);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        sharedPref = this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Silahkan email dan password", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    RequestBody body;
                    JSONStringer json = new JSONStringer();
                    json.object();
                    json.key("username").value(username);
                    json.key("password").value(password);
                    json.endObject();
                    body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
                    Call<ResponseToken> loginCall = endpoint.login(body);

                    loginCall.enqueue(new retrofit2.Callback<ResponseToken>() {
                        @Override
                        public void onResponse(Call<ResponseToken> call, retrofit2.Response<ResponseToken> response) {
                            if (response.body().getToken() != null) {
                                editor.putString(getString(R.string.token), response.body().getToken());
                                editor.apply();
                                getProfile();
                            } else {
                                Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseToken> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getProfile() {
        Call<ResponseLogin> profileCall = endpoint.getProfile();
        profileCall.enqueue(new retrofit2.Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, retrofit2.Response<ResponseLogin> response) {
                if (response.body().getProfile() != null) {
                    editor.putString(getString(R.string.name), response.body().getProfile().getName());
                    editor.putString(getString(R.string.username), response.body().getUser().getUsername());
                    editor.putString(getString(R.string.id), response.body().getUser().getId());
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}