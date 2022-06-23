package com.example.stunting.ui.reminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.stunting.BuildConfig;
import com.example.stunting.R;
import com.example.stunting.data.model.reminder.DataReminder;
import com.example.stunting.data.model.reminder.ResponseReminder;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;


public class ReminderActivity extends AppCompatActivity {

    private RecyclerView rvRData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataReminder> listData = new ArrayList<>();
    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();
    private SharedPreferences sharedPref;
    private ReminderAdapter adapter;
    private SwipeRefreshLayout srlData;
    private FloatingActionButton fabTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        sharedPref = this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        rvRData = findViewById(R.id.rvRData);
        srlData = findViewById(R.id.srl_data);
        fabTambah = findViewById(R.id.fab_tambah);

        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRData.setLayoutManager(lmData);


        srlData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlData.setRefreshing(true);
                retrireveData();
                srlData.setRefreshing(false);
            }
        });

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReminderActivity.this, ReminderAddActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrireveData();
    }

    public void retrireveData(){
        endpoint.getReminder(sharedPref.getString(getString(R.string.token), "")).enqueue(new retrofit2.Callback<ResponseReminder>() {
            @Override
            public void onResponse(Call<ResponseReminder> call, retrofit2.Response<ResponseReminder> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listData = response.body().getReminders();
                    Log.e("TESTES", String.valueOf(listData.size()));
                    adData = new ReminderAdapter(ReminderActivity.this, listData);
                    rvRData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseReminder> call, Throwable t) {

            }
        });
    }
}
