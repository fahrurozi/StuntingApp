package com.example.stunting.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.stunting.BuildConfig;
import com.example.stunting.R;
import com.example.stunting.ui.care_nutrition.CareNutritionActivity;
import com.example.stunting.ui.child.ChildFragment;
import com.example.stunting.ui.info.StuntingInfoActivity;
import com.example.stunting.ui.reminder.ReminderActivity;
import com.example.stunting.ui.stunting_map.StuntingMapActivity;

public class HomeFragment extends Fragment {

    private SharedPreferences sharedPref;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnInfo = view.findViewById(R.id.btn_goto_info);
        LinearLayout btnStuntingMap = view.findViewById(R.id.btn_stunting_map);
        LinearLayout btnCare = view.findViewById(R.id.btn_menu_care);
        LinearLayout btnReminder = view.findViewById(R.id.btn_menu_remind);

        //Session
        sharedPref = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvUsername = view.findViewById(R.id.tvUsername);

        tvName.setText(sharedPref.getString(getString(R.string.name), ""));
        tvUsername.setText(sharedPref.getString(getString(R.string.username), ""));

        btnInfo.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), StuntingInfoActivity.class))
        );

        btnStuntingMap.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), StuntingMapActivity.class))
        );

        btnCare.setOnClickListener(c ->
                startActivity(new Intent(requireContext(), CareNutritionActivity.class))
        );

        btnReminder.setOnClickListener(c ->
                startActivity(new Intent(requireContext(), ReminderActivity.class))
        );


    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}