package com.stuntech.stunting.ui.child.management;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stuntech.stunting.BuildConfig;
import com.stuntech.stunting.R;
import com.stuntech.stunting.data.model.children.DataChildren;
import com.stuntech.stunting.data.model.children.ResponseChildren;
import com.stuntech.stunting.data.network.ApiEndpoint;
import com.stuntech.stunting.data.network.ApiService;
import com.stuntech.stunting.ui.MainActivity;
import com.stuntech.stunting.ui.child.ChildFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stuntech.stunting.ui.health_status.HealthStatusReadMoreActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildSelectYearFragment extends Fragment {

    List<DataChildren> data = new ArrayList();
    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();
    private SharedPreferences sharedPref;


    public TextView tvName, tvHealthStatus, tvTitleScoreInfo, tvDescScoreInfo, tvReadmore;
    public CardView cvYear0, cvYear1, cvYear2, cvYear3, cvYear4;

    private SpotsDialog spotsDialog;

    private String namaAnak;
    private Float zScore;
    private Integer growthLevel;

    private Integer glSangatPendek = -2;
    private Integer glPendek = -1;
    private Integer glTengah = 0;
    private Integer glNormal = 1;
    private Integer glTinggi = 2;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.tvName);
        tvHealthStatus = view.findViewById(R.id.tvHealthStatus);
        tvTitleScoreInfo = view.findViewById(R.id.tvTitleScoreInfo);
        tvDescScoreInfo = view.findViewById(R.id.tvDescScoreInfo);
        tvReadmore = view.findViewById(R.id.tvReadmore);

        cvYear0 = view.findViewById(R.id.cvYear0);
        cvYear1 = view.findViewById(R.id.cvYear1);
        cvYear2 = view.findViewById(R.id.cvYear2);
        cvYear3 = view.findViewById(R.id.cvYear3);
        cvYear4 = view.findViewById(R.id.cvYear4);

        Integer childId = getArguments().getInt("childId");
        getChildrenData(childId);

        cvYear0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Year 0", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("catYear", 0);
                bundle.putInt("childId", childId);
                ChildFragment fragmentobj = new ChildFragment();
                fragmentobj.setArguments(bundle);
                FragmentManager manager = ((MainActivity)v.getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.flHome, fragmentobj).addToBackStack(null).commit();
            }
        });

        cvYear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Year 1", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("catYear", 1);
                bundle.putInt("childId", childId);
                ChildFragment fragmentobj = new ChildFragment();
                fragmentobj.setArguments(bundle);
                FragmentManager manager = ((MainActivity)v.getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.flHome, fragmentobj).addToBackStack(null).commit();
            }
        });

        cvYear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Year 2", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("catYear", 2);
                bundle.putInt("childId", childId);
                ChildFragment fragmentobj = new ChildFragment();
                fragmentobj.setArguments(bundle);
                FragmentManager manager = ((MainActivity)v.getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.flHome, fragmentobj).addToBackStack(null).commit();
            }
        });

        cvYear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Year 3", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("catYear", 3);
                bundle.putInt("childId", childId);
                ChildFragment fragmentobj = new ChildFragment();
                fragmentobj.setArguments(bundle);
                FragmentManager manager = ((MainActivity)v.getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.flHome, fragmentobj).addToBackStack(null).commit();
            }
        });

        cvYear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Year 4", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("catYear", 4);
                bundle.putInt("childId", childId);
                ChildFragment fragmentobj = new ChildFragment();
                fragmentobj.setArguments(bundle);
                FragmentManager manager = ((MainActivity)v.getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.flHome, fragmentobj).addToBackStack(null).commit();
            }
        });

        tvReadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HealthStatusReadMoreActivity.class);
                startActivity(intent);
            }
        });


        sharedPref = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_select_year, container, false);
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

    private void getChildrenData(Integer childId) {
        endpoint.getChildren("by_id",childId).enqueue(new Callback<ResponseChildren>() {
            @Override
            public void onResponse(Call<ResponseChildren> call, Response<ResponseChildren> response) {
                if (response.isSuccessful()){
                    namaAnak = response.body().getChildrens().get(0).getDataChildren().getName();
//                    Log.d("HAI", "onResponse: "+namaAnak);
                    tvName.setText(namaAnak);

                    if(response.body().getChildrens().get(0).getDataChildTrace()!= null){
                        zScore = response.body().getChildrens().get(0).getDataChildTrace().getZ_score();
                        growthLevel = response.body().getChildrens().get(0).getDataChildTrace().getGrowth_level();
                        Log.d("HAI", String.valueOf(growthLevel));
                        Log.d("HAI", String.valueOf(zScore));
                        Log.d("HAI", String.valueOf(childId));
                        if(growthLevel == glSangatPendek){
                            tvHealthStatus.setText("Sangat Pendek");
                            tvTitleScoreInfo.setText("Sangat Pendek");
                            tvDescScoreInfo.setText(String.valueOf(zScore));
                            tvHealthStatus.setBackgroundResource(R.drawable.cyrcle_danger);
                        }else if(growthLevel == glPendek){
                            tvHealthStatus.setText("Pendek");
                            tvTitleScoreInfo.setText("Pendek");
                            tvDescScoreInfo.setText(String.valueOf(zScore));
                            tvHealthStatus.setBackgroundResource(R.drawable.cyrcle_warning);
                        }else if(growthLevel == glTengah){
                            tvHealthStatus.setText("Normal");
                            tvTitleScoreInfo.setText("Normal");
                            tvDescScoreInfo.setText(String.valueOf(zScore));
                            tvHealthStatus.setBackgroundResource(R.drawable.cyrcle_success);
                        }else if(growthLevel == glNormal){
                            tvHealthStatus.setText("Normal");
                            tvTitleScoreInfo.setText("Normal");
                            tvDescScoreInfo.setText(String.valueOf(zScore));
                            tvHealthStatus.setBackgroundResource(R.drawable.cyrcle_success);
                        }else if(growthLevel == glTinggi){
                            tvHealthStatus.setText("Tinggi");
                            tvTitleScoreInfo.setText("Tinggi");
                            tvDescScoreInfo.setText(String.valueOf(zScore));
                            tvHealthStatus.setBackgroundResource(R.drawable.cyrcle_danger);
                        }
                    }else{
                        tvHealthStatus.setText("Tidak ada data");
                        tvTitleScoreInfo.setText("Tidak ada data");
                        tvDescScoreInfo.setText("Tidak ada data");
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseChildren> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        }); }
}
