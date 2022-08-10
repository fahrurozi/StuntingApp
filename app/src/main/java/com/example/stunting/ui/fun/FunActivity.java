package com.example.stunting.ui.fun;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.R;
import com.example.stunting.data.model.child.DataChild;
import com.example.stunting.data.model.fun.ResponseLevelAvailable;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FunActivity extends AppCompatActivity {
    private FunAdapter adapter;

    private RecyclerView rvView;
    private RecyclerView.LayoutManager layoutManager;

    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fun_stunting);

        adapter = new FunAdapter();
        RecyclerView rvData = findViewById(R.id.rvFunData);
        rvData.setAdapter(adapter);
        rvData.setLayoutManager(new GridLayoutManager(this, 4));

//        adapter.insertDataList(data);
//        rvHello.setAdapter(adapter);
//        rvHello.setLayoutManager(new LinearLayoutManager(this));

//        dataSet = new ArrayList<>();
//        initDataset();
//
//        rvView = (RecyclerView) findViewById(R.id.rv_main);
//        rvView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(this);
//        rvView.setLayoutManager(layoutManager);
//
//        adapter = new RecyclerViewAdapterSInfo(dataSet);
//        rvView.setAdapter(adapter);
        getLevelAvailable();
    }

    private void getLevelAvailable(){
        try {
            JSONStringer json = new JSONStringer();
            json.object();
            json.key("get_type").value("levels_has_question");
            json.endObject();

            Call<ResponseLevelAvailable> levelListCall = endpoint.getFunStuntUser( json.toString());
            levelListCall.enqueue(new Callback<ResponseLevelAvailable>() {

                @Override
                public void onResponse(Call<ResponseLevelAvailable> call, Response<ResponseLevelAvailable> response) {
                    if (response.isSuccessful() && response.body().getLevels() != null) {
                        if (response.body().getLevels().size() == 0){
                            Toast.makeText(getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d("data type", response.body().getLevels().get(0).getClass().getName());
                            adapter.insertDataList(response.body().getLevels());
                            Log.e("Data Level", "onResponse: " + response.body().getLevels());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseLevelAvailable> call, Throwable t) {
                    if (Objects.equals(t.getMessage(), "closed")) {
                        getLevelAvailable();
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
