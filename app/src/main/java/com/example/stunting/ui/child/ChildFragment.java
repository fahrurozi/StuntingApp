package com.example.stunting.ui.child;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.BuildConfig;
import com.example.stunting.R;
import com.example.stunting.data.model.child.DataChild;
import com.example.stunting.data.model.child.ResponseChild;
import com.example.stunting.data.model.child.ResponsePutChild;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;

import org.json.JSONException;
import org.json.JSONStringer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildFragment extends Fragment implements ChildInterface {

    List<DataChild> data = new ArrayList();
    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();
    private SharedPreferences sharedPref;
    private ChildAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPref = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        adapter = new ChildAdapter(this);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvDate = view.findViewById(R.id.tvDate);

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        Calendar cal = Calendar.getInstance();

        tvName.setText(sharedPref.getString(getString(R.string.name), ""));
        tvDate.setText(dateFormat.format(cal.getTime()));
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

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onChildClick(@NonNull DataChild child) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog_child, viewGroup, false);

        ImageView btnClose = dialogView.findViewById(R.id.btnClose);
        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        TextView btnEdit = dialogView.findViewById(R.id.btnEdit);

        TextView yesASI = dialogView.findViewById(R.id.tvYesASI);
        TextView noASI = dialogView.findViewById(R.id.tvNoASI);

        TextView yesPenyakit = dialogView.findViewById(R.id.tvYesPenyakit);
        TextView noPenyakit = dialogView.findViewById(R.id.tvNoPenyakit);

        EditText etUsia = dialogView.findViewById(R.id.etUsia);
        EditText etTinggi = dialogView.findViewById(R.id.etTinggiBadan);
        EditText etBerat = dialogView.findViewById(R.id.etBeratBadan);

        CheckBox rbImunisasi = dialogView.findViewById(R.id.rbImunisasi);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        tvTitle.setText(child.getWeek_title() + " " + child.getWeek_name());

        if (child.getId() != null) {
            etUsia.setText(child.getAge_day().toString());
            etBerat.setText(String.valueOf(Math.round(child.getWeight())));
            etTinggi.setText(String.valueOf(Math.round(child.getHeight())));
            yesASI.setBackground(child.getExclusive_asi() ? getResources().getDrawable(R.drawable.round_yes) : getResources().getDrawable(R.drawable.round_no));
            noASI.setBackground(child.getExclusive_asi() ? getResources().getDrawable(R.drawable.round_no) : getResources().getDrawable(R.drawable.round_yes));

            yesPenyakit.setBackground(child.getDisease_history() ? getResources().getDrawable(R.drawable.round_yes) : getResources().getDrawable(R.drawable.round_no));
            noPenyakit.setBackground(child.getDisease_history() ? getResources().getDrawable(R.drawable.round_no) : getResources().getDrawable(R.drawable.round_yes));
            rbImunisasi.setChecked(!child.getImmunization_history().equals(""));
        }else{
            child.setImmunization_history("");
        }

        yesASI.setOnClickListener(v -> {
            child.setExclusive_asi(true);
            yesASI.setBackground(child.getExclusive_asi() ? getResources().getDrawable(R.drawable.round_yes) : getResources().getDrawable(R.drawable.round_no));
            noASI.setBackground(child.getExclusive_asi() ? getResources().getDrawable(R.drawable.round_no) : getResources().getDrawable(R.drawable.round_yes));
        });

        noASI.setOnClickListener(v -> {
            child.setExclusive_asi(false);
            yesASI.setBackground(child.getExclusive_asi() ? getResources().getDrawable(R.drawable.round_yes) : getResources().getDrawable(R.drawable.round_no));
            noASI.setBackground(child.getExclusive_asi() ? getResources().getDrawable(R.drawable.round_no) : getResources().getDrawable(R.drawable.round_yes));
        });

        yesPenyakit.setOnClickListener(v -> {
            child.setDisease_history(true);
            yesPenyakit.setBackground(child.getDisease_history() ? getResources().getDrawable(R.drawable.round_yes) : getResources().getDrawable(R.drawable.round_no));
            noPenyakit.setBackground(child.getDisease_history() ? getResources().getDrawable(R.drawable.round_no) : getResources().getDrawable(R.drawable.round_yes));
        });

        noPenyakit.setOnClickListener(v -> {
            child.setDisease_history(false);
            yesPenyakit.setBackground(child.getDisease_history() ? getResources().getDrawable(R.drawable.round_yes) : getResources().getDrawable(R.drawable.round_no));
            noPenyakit.setBackground(child.getDisease_history() ? getResources().getDrawable(R.drawable.round_no) : getResources().getDrawable(R.drawable.round_yes));
        });

        rbImunisasi.setOnClickListener(v -> child.setImmunization_history(rbImunisasi.isChecked() ? "HepB, Campak" : ""));

        btnClose.setOnClickListener(v -> alertDialog.dismiss());

        btnEdit.setOnClickListener(v -> {
            if (!etUsia.getText().toString().isEmpty()) {
                child.setAge_day(Integer.parseInt(etUsia.getText().toString()));
            }
            if (!etBerat.getText().toString().isEmpty()) {
                child.setWeight(Float.parseFloat(etBerat.getText().toString()));
            }
            if (!etTinggi.getText().toString().isEmpty()) {
                child.setHeight(Float.parseFloat(etTinggi.getText().toString()));
            }
            if (child.getAge_day() != null &&
                    child.getWeight() != null &&
                    child.getHeight() != null &&
                    child.getExclusive_asi() != null &&
                    child.getDisease_history() != null &&
                    child.getImmunization_history() != null
            ) {
                alertDialog.dismiss();
                try {
                    putTrace(child);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), "Data tidak lengkap", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void putTrace(DataChild child) throws JSONException {
        RequestBody body;
        JSONStringer json = new JSONStringer();
        json.object();
        json.key("all_traces");
        json.array();
        json.object();
        json.key("week").value(child.getWeek_count());
        json.key("height").value(child.getHeight());
        json.key("weight").value(child.getWeight());
        json.key("age_day").value(child.getAge_day());
        json.key("exclusive_asi").value(child.getExclusive_asi());
        json.key("disease_history").value(child.getDisease_history());
        json.key("immunization_history").value(child.getImmunization_history());
        json.endObject();
        json.endArray();
        json.endObject();

        body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
        Call<ResponsePutChild> putChildCall = endpoint.putTrace(sharedPref.getString(getString(R.string.token), ""), body);

        putChildCall.enqueue(new Callback<ResponsePutChild>() {

            @Override
            public void onResponse(Call<ResponsePutChild> call, Response<ResponsePutChild> response) {
                getTrace();
            }

            @Override
            public void onFailure(Call<ResponsePutChild> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal mengirim data", Toast.LENGTH_SHORT).show();
            }
        });

    }

}