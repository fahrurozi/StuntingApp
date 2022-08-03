package com.example.stunting.ui.stunting_tribute.detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.BuildConfig;
import com.example.stunting.R;
import com.example.stunting.data.model.review.ResponseReview;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;
import com.example.stunting.ui.reminder.ReminderActivity;
import com.example.stunting.ui.reminder.ReminderAddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;

public class TributeDetailActivity extends AppCompatActivity {
    private Integer idMaps;
    private String desc, place_name, address, photo_ref;
    private Double avg_rating;
    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();
    private ListReviewAdapter adapter;
    private SharedPreferences sharedPref;
    private FloatingActionButton fabTambah;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stunting_tribute_detail);
        sharedPref = this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView ivArticle = findViewById(R.id.ivArticle);
        btnBack.setOnClickListener(v -> finish());
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvSubtitle = findViewById(R.id.tvSubtitle);
        TextView tvAvgRating = findViewById(R.id.tvAvgRating);
        RatingBar rbRating = findViewById(R.id.rbRating);
        FloatingActionButton fabTambah = findViewById(R.id.fabAddReview);

        idMaps = getIntent().getIntExtra("place_id", 0);
        place_name = getIntent().getStringExtra("place_name");
        address = getIntent().getStringExtra("address");
        avg_rating = getIntent().getDoubleExtra("avg_rating", 0);
        photo_ref = getIntent().getStringExtra("photo_ref");

        tvTitle.setText(place_name);
        tvSubtitle.setText(address);
        tvAvgRating.setText(avg_rating.toString());
        rbRating.setRating(avg_rating.floatValue());
        String url_photo = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key=AIzaSyAN6a7kSklwRHnNojU72nDnCfhYGhrATh0&photo_reference=";
        Picasso.get().load(url_photo + photo_ref).into(ivArticle);

        adapter = new ListReviewAdapter();
        RecyclerView rvDataReview = findViewById(R.id.rvListReviewData);
        rvDataReview.setAdapter(adapter);
        rvDataReview.setLayoutManager(new LinearLayoutManager(this));

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd = new Intent(TributeDetailActivity.this, AddReviewActivity.class);
                intentAdd.putExtra("place_id", idMaps);
                startActivity(intentAdd);
            }
        });

//        getListReview();

    }

    private void getListReview(){
        try {
            RequestBody body;
            JSONStringer json = new JSONStringer();
            json.object();
            json.key("filter");
            json.object();
            json.key("stuntplace_id").value(idMaps);
            json.key("user_email").value(null);
            json.endObject();
            json.endObject();
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            endpoint.getReview(body).enqueue(new retrofit2.Callback<ResponseReview>() {
                @Override
                public void onResponse(Call<ResponseReview> call, retrofit2.Response<ResponseReview> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getReviews() != null) {
                        adapter.insertDataList(response.body().getReviews());
                    }
                }

                @Override
                public void onFailure(Call<ResponseReview> call, Throwable t) {
                    if (Objects.equals(t.getMessage(), "closed")) {
                        getListReview();
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
