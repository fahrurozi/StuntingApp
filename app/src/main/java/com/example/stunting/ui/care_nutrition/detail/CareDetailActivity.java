package com.example.stunting.ui.care_nutrition.detail;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stunting.R;
import com.example.stunting.data.model.care.DataCare;
import com.example.stunting.data.network.ApiService;
import com.squareup.picasso.Picasso;

public class CareDetailActivity extends AppCompatActivity {

    DataCare datacare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_detail);
        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView ivArticle = findViewById(R.id.ivArticle);
        btnBack.setOnClickListener(v -> finish());
        TextView tvTitle = findViewById(R.id.tvTitle);
        datacare = (DataCare) getIntent().getParcelableExtra("datacare");
        tvTitle.setText(datacare.getTitle());
        WebView webView = (WebView) findViewById(R.id.wvArticle);
        webView.clearCache(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(ApiService.BASE_URL + "static/" + datacare.getArticleFile());
        Picasso.get().load(ApiService.BASE_URL + "static/" + datacare.getArticleCoverFile()).into(ivArticle);

    }
}