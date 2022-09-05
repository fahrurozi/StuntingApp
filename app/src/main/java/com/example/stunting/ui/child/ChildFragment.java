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
    private Float zScore;
    private Integer growthLevel;

    private Integer glSangatPendek = -2;
    private Integer glPendek = -1;
    private Integer glTengah = 0;
    private Integer glNormal = 1;
    private Integer glTinggi = 2;

    public TextView tvHealthStatus;
    public TextView tvTitleScoreInfo;
    public TextView tvDescScoreInfo;
    public TextView tvName;

//    public HashMap<String, Integer> mapYear0 = new HashMap<String, Integer>(){
//        {put("month", 0); put("year", 0);}
//    };
//    public HashMap<String, Integer> mapYear0 = new HashMap<String, Integer>();
//    public HashMap<String, Integer> mapYear1 = new HashMap<String, Integer>();
//    public HashMap<String, Integer> mapYear2 = new HashMap<String, Integer>();
//    public HashMap<String, Integer> mapYear3 = new HashMap<String, Integer>();
//    public HashMap<String, Integer> mapYear4 = new HashMap<String, Integer>();

//    new array list with value
    public List<Integer> listYear0 = new ArrayList<Integer>(Arrays.asList(4,8,12,16,20,24,28,32,36,40,44));
    public List<Integer> listYear1 = new ArrayList<Integer>(Arrays.asList(48,52,56,60,64,68,72,76,80,84,88,92));
    public List<Integer> listYear2 = new ArrayList<Integer>(Arrays.asList(96,100,104,108,112,116,120,124,128,132,136,140));
    public List<Integer> listYear3 = new ArrayList<Integer>(Arrays.asList(144,148,152,156,160,164,168,172,176,180,184,188));
    public List<Integer> listYear4 = new ArrayList<Integer>(Arrays.asList(192,196,200,204,208,212,216,220,224,228,232,236));


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPref = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        adapter = new ChildAdapter(this);


        Integer childId = getArguments().getInt("childId");
        Integer catYear = getArguments().getInt("catYear");
        Log.d("catYear", "onViewCreated: "+catYear);

        tvName = view.findViewById(R.id.tvName);
        TextView tvDate = view.findViewById(R.id.tvDate);
        tvHealthStatus = view.findViewById(R.id.tvHealthStatus);
        tvTitleScoreInfo = view.findViewById(R.id.tvTitleScoreInfo);
        tvDescScoreInfo = view.findViewById(R.id.tvDescScoreInfo);
        TextView tvReadmore = view.findViewById(R.id.tvReadmore);

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        Calendar cal = Calendar.getInstance();

        getChildrenData(childId);


//        tvName.setText(namaAnak);
        tvDate.setText(dateFormat.format(cal.getTime()));
//        initData(childId);
        initDataBasedYear(childId, catYear);

        RecyclerView rvData = view.findViewById(R.id.rvData);
        rvData.setAdapter(adapter);
        rvData.setLayoutManager(new GridLayoutManager(getContext(), 4));

        adapter.insertDataList(data);

        getTrace(childId);
    }

    public void initDataBasedYear(Integer childId, Integer catYear){
        if(catYear == 0){
            data.add(new DataChild("", "Lahir", 0, childId));
            for(int i = 0; i < listYear0.size(); i++) {
                data.add(new DataChild("Bulan", String.valueOf(i+1), listYear0.get(i), childId));
            }
        }else if(catYear == 1){
            for(int i = 0; i < listYear1.size(); i++) {
                data.add(new DataChild("Bulan", String.valueOf(i), listYear1.get(i), childId));
            }
        }else if(catYear == 2) {
            for (int i = 0; i < listYear2.size(); i++) {
                data.add(new DataChild("Bulan", String.valueOf(i), listYear2.get(i), childId));
            }
        }else if(catYear == 3) {
            for (int i = 0; i < listYear3.size(); i++) {
                data.add(new DataChild("Bulan", String.valueOf(i), listYear3.get(i), childId));
            }
        }else if(catYear == 4) {
            for (int i = 0; i < listYear4.size(); i++) {
                data.add(new DataChild("Bulan", String.valueOf(i), listYear4.get(i), childId));
            }
            data.add(new DataChild("Tahun", "5", 240, childId));
        }
    }

    private void initData(Integer childId) {
        data.add(new DataChild("", "Lahir", 0, childId));
        data.add(new DataChild("Bulan", "1", 4, childId));
        data.add(new DataChild("Bulan", "2", 8, childId));
        data.add(new DataChild("Bulan", "3", 12, childId));
        data.add(new DataChild("Bulan", "4", 16, childId));
        data.add(new DataChild("Bulan", "5", 20, childId));
        data.add(new DataChild("Bulan", "6", 24, childId));
        data.add(new DataChild("Bulan", "7", 28, childId));
        data.add(new DataChild("Bulan", "8", 32, childId));
        data.add(new DataChild("Bulan", "9", 36, childId));
        data.add(new DataChild("Bulan", "10", 40, childId));
        data.add(new DataChild("Bulan", "11", 44, childId));
        data.add(new DataChild("Bulan", "12", 48, childId));
        data.add(new DataChild("Bulan", "13", 52, childId));
        data.add(new DataChild("Bulan", "14", 56, childId));
        data.add(new DataChild("Bulan", "15", 60, childId));
        data.add(new DataChild("Bulan", "16", 64, childId));
        data.add(new DataChild("Bulan", "17", 68, childId));
        data.add(new DataChild("Bulan", "18", 72, childId));
        data.add(new DataChild("Bulan", "19", 76, childId));
        data.add(new DataChild("Bulan", "20", 80, childId));
        data.add(new DataChild("Bulan", "21", 84, childId));
        data.add(new DataChild("Bulan", "22", 88, childId));
        data.add(new DataChild("Bulan", "23", 92, childId));
        data.add(new DataChild("Bulan", "24", 96, childId));
    }

    private void getChildrenData(Integer childId) {
            endpoint.getChildren("by_id",childId).enqueue(new Callback<ResponseChildren>() {
            @Override
            public void onResponse(Call<ResponseChildren> call, Response<ResponseChildren> response) {
                if (response.isSuccessful()){
                    namaAnak = response.body().getChildrens().get(0).getDataChildren().getName();
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

    private void getTrace(Integer childId) {
        Call<ResponseChild> getTraceCall = endpoint.getTrace(childId);
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
        EditText etUsiaShow = dialogView.findViewById(R.id.etUsiaShow);
        etUsiaShow.setEnabled(false);
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
//            etBerat.setText(String.valueOf(Math.round(child.getWeight())));
//            etTinggi.setText(String.valueOf(Math.round(child.getHeight())));
            etBerat.setText(String.valueOf(child.getWeight()));
            etTinggi.setText(String.valueOf(child.getHeight()));
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

//            String custom_hari = child.get
            etUsia.setText(String.valueOf(0));
            float testCountYearByWeek = child.getWeek_count()/4;

            if( testCountYearByWeek== 12 || testCountYearByWeek == 24 || testCountYearByWeek == 36 || testCountYearByWeek == 48 || testCountYearByWeek == 60 || testCountYearByWeek==0) {
                if(testCountYearByWeek == 0 ){
                    etUsiaShow.setText("0 Bulan");
                }else{
                    etUsiaShow.setText(Math.round(testCountYearByWeek/12) + " Tahun");
                }
            }else if(child.getWeek_count()/4/12 < 1){
                etUsiaShow.setText("0 Tahun "+ child.getWeek_count()/4 + " Bulan");
            }else if(child.getWeek_count()/4/12 < 2){
                etUsiaShow.setText("1 Tahun "+ child.getWeek_title() + " Bulan");
            }else if(child.getWeek_count()/4/12 < 3){
                etUsiaShow.setText("2 Tahun "+ child.getWeek_title() + " Bulan");
            }else if(child.getWeek_count()/4/12 < 4){
                etUsiaShow.setText("3 Tahun "+ child.getWeek_title() + " Bulan");
            }else if(child.getWeek_count()/4/12 < 5){
                etUsiaShow.setText("4 Tahun "+ child.getWeek_title() + " Bulan");
            }


            Log.d("HAI", "onChildClick: "+child.getWeek_name());
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
                getChildrenData(child.getChildren());
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
                getChildrenData(child.getChildren());
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