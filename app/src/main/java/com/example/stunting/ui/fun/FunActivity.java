package com.example.stunting.ui.fun;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.R;
import com.example.stunting.data.model.child.DataChild;
import com.example.stunting.data.model.fun.ResponseLevelAvailable;
import com.example.stunting.data.model.fun.ResponseScorePerLevel;
import com.example.stunting.data.model.fun.TestModel;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FunActivity extends AppCompatActivity {
    private FunAdapter adapter;

    private RecyclerView rvView;
    private RecyclerView.LayoutManager layoutManager;

    List<Integer> listLevelAvailable = new ArrayList<>();

    List<TestModel> data = new ArrayList();


    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();

    ResponseScorePerLevel dataSummary;

    private SpotsDialog spotsDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fun_stunting);
        spotsDialog = new SpotsDialog(this, "Mohon Tunggu...");
        getLevelAvailable();

        TextView tvTitle = findViewById(R.id.tvTitle);

        adapter = new FunAdapter();
        RecyclerView rvData = findViewById(R.id.rvFunData);
        rvData.setAdapter(adapter);
        rvData.setLayoutManager(new GridLayoutManager(this, 4));

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

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


//        getUserSummary();
//        Log.d("HAI", "onCreate: " + data.toString());
    }

    private void getLevelAvailable(){
        try {
            spotsDialog.show();
            JSONStringer json = new JSONStringer();
            json.object();
            json.key("get_type").value("levels_has_question");
            json.endObject();

            Call<ResponseLevelAvailable> levelListCall = endpoint.getFunStuntUser( json.toString());
            levelListCall.enqueue(new Callback<ResponseLevelAvailable>() {

                @Override
                public void onResponse(Call<ResponseLevelAvailable> call, Response<ResponseLevelAvailable> response) {
                    spotsDialog.dismiss();
                    if (response.isSuccessful() && response.body().getLevels() != null) {
                        if (response.body().getLevels().size() == 0){
                            Toast.makeText(getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }else{
                            listLevelAvailable = response.body().getLevels();
                            getUserSummary(listLevelAvailable);
//                            Log.d("ASASDAS", "onCreate: " + response.body().getLevels());
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

    private void getUserSummary(List<Integer> listLevelAvailable){
        try {
            spotsDialog.show();
            JSONStringer json = new JSONStringer();
            json.object();
            json.key("get_type").value("user_answers");
            json.endObject();

            Call<ResponseScorePerLevel> funUserSummary = endpoint.getFunUserSummary( json.toString());
            funUserSummary.enqueue(new Callback<ResponseScorePerLevel>() {

                @Override
                public void onResponse(Call<ResponseScorePerLevel> call, Response<ResponseScorePerLevel> response) {
                    spotsDialog.dismiss();
                    if (response.isSuccessful() && response.body().getUserAnswers() != null && response.body().getUserScorePerLevel() != null) {
                        if (response.body().getUserAnswers().size() == 0){
                            Toast.makeText(getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d("ASASDAS", "onCreate: " + listLevelAvailable.get(0));
                            for (int i = 0; i < listLevelAvailable.size(); i++){
                                data.add(new TestModel(listLevelAvailable.get(i), response.body().getUserScorePerLevel().get(i)));
                            }
//                            dataSummary = response.body();
//                            Log.d("HAI", "onCreate: " + data.toString());
                            adapter.insertDataList(data);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseScorePerLevel> call, Throwable t) {
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
