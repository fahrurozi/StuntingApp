package com.example.stunting.ui.care_nutrition;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.BuildConfig;
import com.example.stunting.R;
import com.example.stunting.data.model.care.DataCare;
import com.example.stunting.data.model.care.ResponseCare;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;
import com.example.stunting.ui.care_nutrition.detail.CareDetailActivity;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;

public class CareCategoryActivity extends AppCompatActivity implements CareInterface {

    private String category;
    private CareAdapter adapter;
    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_category);
        sharedPref = this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        category = getIntent().getStringExtra("category");
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(capitalizeString(category));

        adapter = new CareAdapter(this);
        RecyclerView rvCare = findViewById(R.id.rvData);
        rvCare.setAdapter(adapter);
        rvCare.setLayoutManager(new LinearLayoutManager(this));
        getArticle();

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

    }

    private void getArticle() {
        try {
            RequestBody body;
            JSONStringer json = new JSONStringer();
            json.object();
            json.key("get_articles").value("filter_articles");
            json.key("article_sub_type").value(category);
            json.endObject();
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            endpoint.getArticle(body).enqueue(new retrofit2.Callback<ResponseCare>() {
                @Override
                public void onResponse(Call<ResponseCare> call, retrofit2.Response<ResponseCare> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getArticles() != null) {
                        adapter.insertDataList(response.body().getArticles());
                    }
                }

                @Override
                public void onFailure(Call<ResponseCare> call, Throwable t) {
                    if (Objects.equals(t.getMessage(), "closed")) {
                        getArticle();
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String capitalizeString(String str) {
        String words[] = str.split("\\s");
        String capitalizeStr = "";

        for (String word : words) {
            // Capitalize first letter
            String firstLetter = word.substring(0, 1);
            // Get remaining letter
            String remainingLetters = word.substring(1);
            capitalizeStr += firstLetter.toUpperCase() + remainingLetters + " ";
        }
        return capitalizeStr;
    }

    @Override
    public void onChildClick(DataCare datacare) {
        startActivity(new Intent(this, CareDetailActivity.class).putExtra("datacare", datacare));
    }
}