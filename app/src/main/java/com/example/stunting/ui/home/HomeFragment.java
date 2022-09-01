package com.example.stunting.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.BuildConfig;
import com.example.stunting.R;
import com.example.stunting.data.model.children.ResponseChildren;
import com.example.stunting.data.model.children.ResponseDetailAllChildren;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;
import com.example.stunting.ui.MainInterface;
import com.example.stunting.ui.care_nutrition.CareNutritionActivity;
import com.example.stunting.ui.child.ChildFragment;
import com.example.stunting.ui.child.management.ChildManagementAdapter;
import com.example.stunting.ui.child.management.ChildManagementFragment;
import com.example.stunting.ui.food_help.FoodHelpActivity;
import com.example.stunting.ui.fun.FunActivity;
import com.example.stunting.ui.hello_stunting.HelloStuntingActivity;
import com.example.stunting.ui.info.StuntingInfoActivity;
import com.example.stunting.ui.info.StuntingInfoActivityBak;
import com.example.stunting.ui.reminder.ReminderActivity;
import com.example.stunting.ui.reminderv2.ReminderV2Activity;
import com.example.stunting.ui.stunting_health.StuntingHealthActivity;
import com.example.stunting.ui.stunting_map.StuntingMapActivity;
import com.example.stunting.ui.stunting_tribute.StuntingTributeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

public class HomeFragment extends Fragment {

    private SharedPreferences sharedPref;
    private MainInterface parent;
    private HomeTraceAdapter adapter;

    private List<ResponseDetailAllChildren> rvData = new ArrayList();;

    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnTest = view.findViewById(R.id.btn_new_account_create);

        Button btn_go_to_trace = view.findViewById(R.id.btn_go_to_trace);
        LinearLayout btnStuntingTrace = view.findViewById(R.id.btn_menu_trace);
        LinearLayout btnStuntingMap = view.findViewById(R.id.btn_stunting_map);
        LinearLayout btnCare = view.findViewById(R.id.btn_menu_care);
        LinearLayout btnReminder = view.findViewById(R.id.btn_menu_remind);
        LinearLayout btnHealth = view.findViewById(R.id.btn_menu_health);
        LinearLayout btnFoodHelp = view.findViewById(R.id.btn_food_help);
        LinearLayout btnMenuInfo = view.findViewById(R.id.btn_menu_info);
        LinearLayout btnMenuHello = view.findViewById(R.id.btn_hello_stunting);
        LinearLayout btnStuntingTribute = view.findViewById(R.id.btn_stunting_tribute);
        LinearLayout btnFun = view.findViewById(R.id.btn_fun_stunting);
//        LinearLayout btnTrace = view.findViewById(R.id.btn_menu_trace);

        //Session
        sharedPref = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvUsername = view.findViewById(R.id.tvUsername);

        tvName.setText(sharedPref.getString(getString(R.string.name), ""));
        tvUsername.setText(sharedPref.getString(getString(R.string.username), ""));

        btnStuntingTrace.setOnClickListener(v -> parent.openMenuNav(R.id.nav_child));

        btn_go_to_trace.setOnClickListener(v -> parent.openMenuNav(R.id.nav_child));

        btnMenuInfo.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), StuntingInfoActivity.class))
        );

        btnStuntingMap.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), StuntingMapActivity.class))
        );

        btnCare.setOnClickListener(c ->
                startActivity(new Intent(requireContext(), CareNutritionActivity.class))
        );

        btnReminder.setOnClickListener(c ->
                startActivity(new Intent(requireContext(), ReminderV2Activity.class))
        );

        btnHealth.setOnClickListener(c ->
                startActivity(new Intent(requireContext(), StuntingHealthActivity.class))
        );

        btnFoodHelp.setOnClickListener(c ->
                startActivity(new Intent(requireContext(), FoodHelpActivity.class))
        );

        btnMenuHello.setOnClickListener(c ->
                startActivity(new Intent(requireContext(), HelloStuntingActivity.class))
        );

        btnStuntingTribute.setOnClickListener(c ->
                startActivity(new Intent(requireContext(), StuntingTributeActivity.class))
        );

        btnFun.setOnClickListener(c ->
                startActivity(new Intent(requireContext(), FunActivity.class))
        );

//        btnTrace.setOnClickListener(c ->
//                startActivity(new Intent(requireContext(), StuntingInfoActivityBak.class))
//        );

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ChildManagementFragment());
            }
        });

        adapter = new HomeTraceAdapter();
        RecyclerView rvData = view.findViewById(R.id.rvTraceData);
        rvData.setAdapter(adapter);
        rvData.setLayoutManager(new LinearLayoutManager(getContext()));
        getAnak();
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

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        parent = (MainInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void getAnak(){
        endpoint.getChildrenList("all").enqueue(new retrofit2.Callback<ResponseChildren>() {
            @Override
            public void onResponse(Call<ResponseChildren> call, retrofit2.Response<ResponseChildren> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getChildrens() != null) {
                    Log.e("TTSSTTS", "onResponse: " + response.body().getChildrens());
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


}