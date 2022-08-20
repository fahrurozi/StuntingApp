package com.example.stunting.ui.child;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import com.example.stunting.data.model.child.ResponseUpdateChild;
import com.example.stunting.data.model.children.ResponseChildren;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;

import org.json.JSONException;
import org.json.JSONStringer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildFragment extends Fragment implements ChildInterface {

    List<DataChild> data = new ArrayList();
    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();
    private SharedPreferences sharedPref;
    private ChildAdapter adapter;
    private String namaAnak;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPref = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        adapter = new ChildAdapter(this);

        Integer childId = getArguments().getInt("childId");

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvDate = view.findViewById(R.id.tvDate);

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        Calendar cal = Calendar.getInstance();

        getChildrenData(childId, tvName);


//        tvName.setText(namaAnak);
        tvDate.setText(dateFormat.format(cal.getTime()));
        initData(childId);

        RecyclerView rvData = view.findViewById(R.id.rvData);
        rvData.setAdapter(adapter);
        rvData.setLayoutManager(new GridLayoutManager(getContext(), 4));

        adapter.insertDataList(data);

        getTrace(childId);
    }

    private void initData(Integer childId) {
        data.add(new DataChild("Bulan", "1", 1, childId));
        data.add(new DataChild("Bulan", "2", 4, childId));
        data.add(new DataChild("Bulan", "3", 8, childId));
        data.add(new DataChild("Bulan", "4", 12, childId));
        data.add(new DataChild("Bulan", "5", 16, childId));
        data.add(new DataChild("Bulan", "6", 20, childId));
        data.add(new DataChild("Bulan", "7", 24, childId));
        data.add(new DataChild("Bulan", "8", 28, childId));
        data.add(new DataChild("Bulan", "9", 32, childId));
        data.add(new DataChild("Bulan", "10", 36, childId));
        data.add(new DataChild("Bulan", "11", 40, childId));
        data.add(new DataChild("Bulan", "12", 44, childId));
        data.add(new DataChild("Bulan", "13", 48, childId));
        data.add(new DataChild("Bulan", "14", 52, childId));
        data.add(new DataChild("Bulan", "15", 56, childId));
        data.add(new DataChild("Bulan", "16", 60, childId));
    }

    private void getChildrenData(Integer childId, TextView tvName) {
            endpoint.getChildren(childId).enqueue(new Callback<ResponseChildren>() {
            @Override
            public void onResponse(Call<ResponseChildren> call, Response<ResponseChildren> response) {
                if (response.isSuccessful()){
                    namaAnak = response.body().getChildrens().get(0).getDataChildren().getName();
                    tvName.setText(namaAnak);
                }
            }
            @Override
            public void onFailure(Call<ResponseChildren> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        }); }

    private void getTrace(Integer childId) {
        Call<ResponseChild> getTraceCall = endpoint.getTrace(childId);
        Log.e("TAG", "getTrace() returned: ");
        getTraceCall.enqueue(new retrofit2.Callback<ResponseChild>() {

            @Override
            public void onResponse(Call<ResponseChild> call, Response<ResponseChild> response) {
                if (response.isSuccessful()) {
                    Log.e("TAG", "getTrace() berhasil: " + response.body().toString());
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
                if (Objects.equals(t.getMessage(), "closed")){
                    getTrace(childId);
                }else{
                    Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
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

    private DataChild dataChildReq = null;

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

//        CheckBox rbImunisasi = dialogView.findViewById(R.id.rbImunisasi);
        CheckBox rbHepB = dialogView.findViewById(R.id.rbHepB);
        CheckBox rbCampak = dialogView.findViewById(R.id.rbCampak);

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

            String[] imunisasi = child.getImmunization_history().split("\\|");
//            boolean haveCampak = Arrays.asList(immunisasi).contains("Campak");
//            boolean haveHepB = Arrays.asList(immunisasi).contains("HepB");

            if(imunisasi[1].equals("Campak")) {
                rbCampak.setChecked(true);
            }
            if (imunisasi[0].equals("HepB")) {
                rbHepB.setChecked(true);
            }
            //            rbImunisasi.setChecked(!child.getImmunization_history().equals(""));

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

//        rbImunisasi.setOnClickListener(v -> child.setImmunization_history(rbImunisasi.isChecked() ? "HepB, Campak" : ""));
//        String[] inputImunisasi = child.getImmunization_history().split("\\|");
        List<String> inputImunisasi =  new ArrayList<>();
        inputImunisasi.addAll(Arrays.asList(child.getImmunization_history().split("\\|")));

        if(inputImunisasi.size() == 1){
            inputImunisasi.add(0,null);
        }
//                Log.e("inputImunisasi", String.valueOf(inputImunisasi.length));
        rbHepB.setOnClickListener(v -> {
            if (rbHepB.isChecked()) {
                inputImunisasi.set(0, "HepB");
//                inputImunisasi[0] = "HepB";
                Log.e("Cok1", "onChildClick: " + inputImunisasi.get(0));
            } else {
                inputImunisasi.set(0, null);
//                inputImunisasi[0] = null;
                Log.e("Cok1", "onChildClick: " + inputImunisasi.get(0));
            }
        });

        rbCampak.setOnClickListener(v -> {
            if (rbCampak.isChecked()) {
                inputImunisasi.set(1, "Campak");
//                inputImunisasi[1] = "Campak";
                Log.e("Cok1", "onChildClick: " + inputImunisasi.get(1));
            } else {
                inputImunisasi.set(1, null);
//                inputImunisasi[1] = null;
                Log.e("Cok1", "onChildClick: " + inputImunisasi.get(1));
            }
        });


        btnClose.setOnClickListener(v -> alertDialog.dismiss());

        btnEdit.setOnClickListener(v -> {
            String inputImunisasiString = String.join("|",inputImunisasi);
            child.setImmunization_history(inputImunisasiString);
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
                    dataChildReq = child;
                    if(child.getId() != null){
                        patchTrace(child);
                    }else{
                        putTrace(child);
                    }
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
        json.key("child_id").value(child.getChildren());
        json.endObject();

        body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
        Call<ResponsePutChild> putChildCall = endpoint.putTrace( body);

        putChildCall.enqueue(new Callback<ResponsePutChild>() {

            @Override
            public void onResponse(Call<ResponsePutChild> call, Response<ResponsePutChild> response) {
                getTrace(child.getChildren());
            }

            @Override
            public void onFailure(Call<ResponsePutChild> call, Throwable t) {
                if (Objects.equals(t.getMessage(), "closed")){
                    try {
                        putTrace(child);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void patchTrace(DataChild child) throws JSONException {
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
        json.key("child_id").value(child.getChildren());
        json.endObject();

        body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
        Call<ResponseUpdateChild> patchChildCall = endpoint.patchTrace( body);

        patchChildCall.enqueue(new Callback<ResponseUpdateChild>() {

            @Override
            public void onResponse(Call<ResponseUpdateChild> call, Response<ResponseUpdateChild> response) {
                getTrace(child.getChildren());
            }

            @Override
            public void onFailure(Call<ResponseUpdateChild> call, Throwable t) {
                if (Objects.equals(t.getMessage(), "closed")){
                    try {
                        patchTrace(child);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}