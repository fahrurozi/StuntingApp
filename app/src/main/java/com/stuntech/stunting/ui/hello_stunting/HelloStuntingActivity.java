package com.stuntech.stunting.ui.hello_stunting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stuntech.stunting.R;
import com.stuntech.stunting.data.model.care.DataCare;
import com.stuntech.stunting.data.model.care.ResponseCare;
import com.stuntech.stunting.data.network.ApiEndpoint;
import com.stuntech.stunting.data.network.ApiService;
import com.stuntech.stunting.ui.food_help.FoodAdapter;
import com.stuntech.stunting.ui.food_help.FoodInterface;
import com.stuntech.stunting.ui.food_help.detail.FoodDetailActivity;
import com.stuntech.stunting.ui.hello_stunting.detail.HelloDetailActivity;
import com.stuntech.stunting.data.model.care.DataCare;
import com.stuntech.stunting.data.model.care.ResponseCare;
import com.stuntech.stunting.data.network.ApiEndpoint;
import com.stuntech.stunting.data.network.ApiService;
import com.stuntech.stunting.ui.hello_stunting.detail.HelloDetailActivity;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;

public class HelloStuntingActivity extends AppCompatActivity implements HelloInterface {
    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();
    private HelloAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_stunting);
        adapter = new HelloAdapter(this);
        RecyclerView rvHello = findViewById(R.id.rvHelloData);
        rvHello.setAdapter(adapter);
        rvHello.setLayoutManager(new LinearLayoutManager(this));
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
            json.key("article_type").value("hello_stunting");
            json.endObject();
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            endpoint.getArticle(body).enqueue(new retrofit2.Callback<ResponseCare>() {
                @Override
                public void onResponse(Call<ResponseCare> call, retrofit2.Response<ResponseCare> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getArticles() != null) {
                        Log.e("TTSSTTS", "onResponse: " + response.body().getArticles().size());
                        adapter.insertDataList(response.body().getArticles());
                    }
                }

                @Override
                public void onFailure(Call<ResponseCare> call, Throwable t) {
                    if (Objects.equals(t.getMessage(), "closed")) {
                        getArticle();
                    } else {
                        Log.e("ERRRORORO", "onFailure: " + t.getMessage());
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
    public void onChildClick(DataCare datainfo) {
        startActivity(new Intent(this, HelloDetailActivity.class).putExtra("datacare", datainfo));
    }
}
