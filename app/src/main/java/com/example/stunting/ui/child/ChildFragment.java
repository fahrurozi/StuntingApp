package com.example.stunting.ui.child;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.BuildConfig;
import com.example.stunting.R;
import com.example.stunting.data.model.child.DataChild;
import com.example.stunting.data.model.child.ResponseChild;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ChildFragment extends Fragment {

    List<DataChild> data = new ArrayList();
    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();
    private SharedPreferences sharedPref;
    private ChildAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPref = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        adapter = new ChildAdapter();

        initData();

        RecyclerView rvData = view.findViewById(R.id.rvData);
        rvData.setAdapter(adapter);
        rvData.setLayoutManager(new GridLayoutManager(getContext(), 4));

        adapter.insertDataList(data);

        getTrace();
    }

    private void initData() {
        data.add(new DataChild("Minggu", "1", 1));
        data.add(new DataChild("Minggu", "2", 2));
        data.add(new DataChild("Minggu", "3", 3));
        data.add(new DataChild("Bulan", "1", 4));
        data.add(new DataChild("Bulan", "2", 8));
        data.add(new DataChild("Bulan", "4", 16));
        data.add(new DataChild("Bulan", "6", 24));
        data.add(new DataChild("Bulan", "8", 32));
        data.add(new DataChild("Bulan", "10", 40));
        data.add(new DataChild("Bulan", "12", 48));
        data.add(new DataChild("Bulan", "14", 56));
        data.add(new DataChild("Bulan", "16", 64));
        data.add(new DataChild("Bulan", "18", 72));
        data.add(new DataChild("Bulan", "20", 80));
        data.add(new DataChild("Bulan", "22", 88));
        data.add(new DataChild("Bulan", "24", 96));
    }

    private void getTrace() {
        Call<ResponseChild> getTraceCall = endpoint.getTrace(sharedPref.getString(getString(R.string.token), ""));
        getTraceCall.enqueue(new retrofit2.Callback<ResponseChild>() {

            @Override
            public void onResponse(Call<ResponseChild> call, Response<ResponseChild> response) {
                if (response.isSuccessful()) {
                    ResponseChild responseChild = response.body();
                    if (responseChild.getAll_traces() != null) {
                        for (int i = 0; i < responseChild.getAll_traces().size(); i++) {
                            for (int j = 0; j < data.size(); j++) {
                                if (responseChild.getAll_traces().get(i).getWeek().equals(data.get(j).getWeek_count())) {
                                    data.get(j).setDataChildServer(responseChild.getAll_traces().get(i));
                                }
                            }
                        }
                        adapter.insertDataList(data);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseChild> call, Throwable t) {
            }
        });
    }

    public ChildFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child, container, false);
    }
}