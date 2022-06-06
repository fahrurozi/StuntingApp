package com.example.stunting.ui.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stunting.R;
import com.example.stunting.data.model.register.ResponseRegister;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;

import org.json.JSONException;
import org.json.JSONStringer;

import okhttp3.RequestBody;
import retrofit2.Call;

public class RegisterActivity extends AppCompatActivity {

    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etName, etUsername, etEmail, etPassword;
        etName = findViewById(R.id.et_name);
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        Button btnSubmit = findViewById(R.id.btn_submit_register);
        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Silahkan lengkapi field yang ada!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    RequestBody body;
                    JSONStringer json = new JSONStringer();
                    json.object();
                    json.key("name").value(name);
                    json.key("username").value(name);
                    json.key("email").value(email);
                    json.key("b64_profile_img").value("");
                    json.key("role_name").value("user");
                    json.key("password").value(password);
                    json.endObject();
                    body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

                    Call<ResponseRegister> userCall = endpoint.register(body);
                    userCall.enqueue(new retrofit2.Callback<ResponseRegister>() {
                        @Override
                        public void onResponse(Call<ResponseRegister> call, retrofit2.Response<ResponseRegister> response) {
                            try {
                                if (response.body().getProfile() != null) {
                                    Toast.makeText(getApplicationContext(), "Berhasil mendaftar!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Gagal mendaftar!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Gagal mendaftar!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseRegister> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Gagal mendaftar!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}