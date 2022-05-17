package com.example.stunting.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.stunting.R;
import com.example.stunting.StuntingInfoActivity;
import com.example.stunting.stunting_map.StuntingMapActivity;

public class HomeFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnInfo = view.findViewById(R.id.btn_goto_info);
        LinearLayout btnStuntingMap = view.findViewById(R.id.btn_stunting_map);

        btnInfo.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), StuntingInfoActivity.class))
        );

        btnStuntingMap.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), StuntingMapActivity.class))
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