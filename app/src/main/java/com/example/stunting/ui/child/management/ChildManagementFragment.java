package com.example.stunting.ui.child.management;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.BuildConfig;
import com.example.stunting.R;
import com.example.stunting.data.model.care.ResponseCare;
import com.example.stunting.data.model.child.DataChild;
import com.example.stunting.data.model.children.DataChildren;
import com.example.stunting.data.model.children.ResponseChildren;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;
import com.example.stunting.ui.child.ChildAdapter;
import com.example.stunting.ui.child.management.ChildManagementAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;

public class ChildManagementFragment extends Fragment {

    List<DataChildren> data = new ArrayList();
    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();
    private SharedPreferences sharedPref;
    private ChildManagementAdapter adapter;
    private FloatingActionButton fabButton;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        sharedPref = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        adapter = new ChildManagementAdapter();

        FloatingActionButton fabButton = view.findViewById(R.id.fabAddChildren);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ChildAddFragment());
            }
        });


        RecyclerView rvData = view.findViewById(R.id.rvChildData);
        rvData.setAdapter(adapter);
        rvData.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter.insertDataList(data);
        getAnak();
    }

    private void getAnak(){
            endpoint.getChildrenList().enqueue(new retrofit2.Callback<ResponseChildren>() {
                @Override
                public void onResponse(Call<ResponseChildren> call, retrofit2.Response<ResponseChildren> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getChildrens() != null) {
                        Log.e("TTSSTTS", "onResponse: " + response.body().getChildrens().size());
                        adapter.insertDataList(response.body().getChildrens());
                    }
                }

                @Override
                public void onFailure(Call<ResponseChildren> call, Throwable t) {
                    if (Objects.equals(t.getMessage(), "closed")) {
                        getAnak();
                    } else {
                        Log.e("ERRRORORO", "onFailure: " + t.getMessage());
                        Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anak, container, false);
    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.flHome, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}
