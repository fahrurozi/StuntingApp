package com.stuntech.stunting.ui.account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stuntech.stunting.BuildConfig;
import com.stuntech.stunting.R;
import com.stuntech.stunting.root.App;
import com.stuntech.stunting.ui.GetStartedActivity;
import com.stuntech.stunting.ui.care_nutrition.CareNutritionActivity;
import com.stuntech.stunting.ui.food_help.FoodHelpActivity;
import com.stuntech.stunting.ui.info.StuntingInfoActivity;
import com.stuntech.stunting.ui.info.StuntingInfoActivityBak;
import com.stuntech.stunting.ui.reminder.ReminderActivity;
import com.stuntech.stunting.ui.stunting_health.StuntingHealthActivity;
import com.stuntech.stunting.ui.stunting_map.StuntingMapActivity;


public class AccountFragment extends Fragment {

    private SharedPreferences sharedPref;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Session
        sharedPref = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvUsername = view.findViewById(R.id.tvUsername);

        CardView btnLogout = view.findViewById(R.id.btn_logout);

        tvName.setText(sharedPref.getString(getString(R.string.name), ""));
        tvUsername.setText(sharedPref.getString(getString(R.string.username), ""));

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    public void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Logout");
        builder.setMessage("Are you sure want to logout?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(getContext(), GetStartedActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }
}