package com.stuntech.stunting.ui.hello_stunting.detail;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.stuntech.stunting.R;
import com.stuntech.stunting.data.model.care.DataCare;
import com.stuntech.stunting.data.network.ApiService;
import com.squareup.picasso.Picasso;
import com.stuntech.stunting.data.model.care.DataCare;

public class HelloDetailActivity extends AppCompatActivity {
    DataCare datacare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_detail);
        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView ivArticle = findViewById(R.id.ivArticle);
        btnBack.setOnClickListener(v -> finish());
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvAddress = findViewById(R.id.tvAddress);
        datacare = (DataCare) getIntent().getParcelableExtra("datacare");

        Log.e("TAG", "onCreate: " + datacare.toString());

        tvTitle.setText(datacare.getTitle());
        tvAddress.setText(datacare.getArticleSubType());
        WebView webView = (WebView) findViewById(R.id.wvArticle);
        webView.clearCache(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(ApiService.BASE_URL + "static/" + datacare.getArticleFile());
        Picasso.get().load(ApiService.BASE_URL + "static/" + datacare.getArticleCoverFile()).into(ivArticle);

    }
}
