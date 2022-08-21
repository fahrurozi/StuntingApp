package com.example.stunting.ui.child.management;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.stunting.BuildConfig;
import com.example.stunting.R;
import com.example.stunting.components.DatePickerFragment;
import com.example.stunting.data.model.children.ResponseAddChildren;
import com.example.stunting.data.model.children.ResponseUpdateChildren;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONStringer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dmax.dialog.SpotsDialog;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildEditFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private SharedPreferences sharedPref;
    private TextView etDOB, etName;
    private FloatingActionButton fabSimpan;

    private SpotsDialog spotsDialog;

    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();

    public Integer dates, months, years;
    public String stringGender;
    public Integer intInputGender;
    public Boolean inputActive;
    public String date;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        etDOB = view.findViewById(R.id.etDOB);
        fabSimpan = view.findViewById(R.id.fabSimpan);
        etName = view.findViewById(R.id.etName);

        spotsDialog = new SpotsDialog(getContext(), "Mohon Tunggu...");

        Integer childId = getArguments().getInt("childId");
        String childName = getArguments().getString("childName");
        String childDOB = getArguments().getString("childDOB");
        Integer childGender = getArguments().getInt("childGender");
        Boolean childActive = getArguments().getBoolean("childActive");
        inputActive = childActive;
        Integer childParent = getArguments().getInt("childParent");
        RadioButton rbActive = view.findViewById(R.id.radioActive);

//        boolean checked = ((RadioButton) view).isChecked();
        final RadioGroup rgActive = view.findViewById(R.id.rgActive);
        // Check which radio button was clicked
        if(childActive == true){
            rgActive.check(R.id.radioActive);
        }else{
            rgActive.check(R.id.radioInactive);
        }


        rgActive.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioActive :
                        inputActive = true;
                        break;
                    case R.id.radioInactive :
                        inputActive = false;
                        break;
                }
            }
        });


        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(childDOB);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                format = new SimpleDateFormat("dd MMM YYYY");
            }
            date = format.format(newDate);

        }catch (Exception e){
            e.printStackTrace();
        }

        etName.setText(childName);
        etDOB.setText(date);
        Spinner drop_jenisKelamin=(Spinner) getView().findViewById(R.id.spinnerGender);
        String valJenisKelamin=drop_jenisKelamin.getSelectedItem().toString();

        if(childGender.equals(0)){
            stringGender = "Laki-laki";
        }else if(childGender.equals(1)){
            stringGender = "Perempuan";
        }

            if(childGender.equals(0)){
                drop_jenisKelamin.setSelection(0);
            }else if(childGender.equals(1)){
                drop_jenisKelamin.setSelection(1);
            }


        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(ChildEditFragment.this, 0);
                datePicker.show(getFragmentManager(), "date picker");

            }
        });

        fabSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dates==null || months==null || years==null) {
//                    Toast.makeText(getContext(), "Mohon isi tanggal lahir", Toast.LENGTH_SHORT).show();
                    String[] ary = childDOB.split("-");
                    years = Integer.parseInt(ary[0]);
                    months = Integer.parseInt(ary[1]);
                    dates = Integer.parseInt(ary[2]);

                    Log.d("HAI" , "SetNullDate: "+years+"-"+months+"-"+dates);
                }
                Log.d("HAI" , "FinalDate: "+years+"-"+months+"-"+dates);
                Log.d("HAI", "Nama: "+etName.getText().toString());
                Log.d("HAI", "Tanggal Lahir: "+etDOB.getText().toString());
                Spinner drop_jenisKelamin=(Spinner) getView().findViewById(R.id.spinnerGender);
                String valJenisKelamin=drop_jenisKelamin.getSelectedItem().toString();
                Log.d("HAI", "Jenis Kelamin: "+valJenisKelamin);





                Log.d("HAI", "Active: "+inputActive.toString());

                String inputName = etName.getText().toString();

                String name = etName.getText().toString();
                String dob = etDOB.getText().toString();
                if (name.isEmpty() || dob.isEmpty()) {
                    Toast.makeText(getContext(), "Silahkan lengkapi field yang ada!", Toast.LENGTH_SHORT).show();
                } else {
                    if(valJenisKelamin.equals("Perempuan")){
                        intInputGender = 1;
                    }else if(valJenisKelamin.equals("Laki-laki")) {
                        intInputGender = 0;
                    }
//
                    try {
                        spotsDialog.show();
                        updateChildren(childId, name, dates, months, years, intInputGender, inputActive);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioActive:
                if (checked)
                    inputActive=true;
                    break;
            case R.id.radioInactive:
                if (checked)
                    inputActive=false;
                    break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_anak, container, false);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance().format(c.getTime());

//        dates = Integer.toString(dayOfMonth);
//        months = Integer.toString(month);
//        years = Integer.toString(year);

        dates = dayOfMonth;
        months = month;
        years = year;

        Log.d("HAI", "onDateSetBawah: "+dates+"-"+months+"-"+years);

        etDOB.setText(currentDate);
    }

    private void updateChildren(Integer childId, String childName, Integer date, Integer month, Integer year, Integer gender, Boolean inputActive) throws JSONException {
        RequestBody body;
        JSONStringer json = new JSONStringer();
        json.object();
        json.key("child_info");
        json.object();
        json.key("name").value(childName);
        json.key("born_date");
        json.array();
        json.value(year);
        json.value(month);
        json.value(date);
        json.endArray();
        json.key("gender").value(gender);
        json.key("active").value(inputActive);
        json.endObject();
        json.key("child_id").value(childId);
        json.endObject();

        body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
        Call<ResponseUpdateChildren> updateChildrenCall = endpoint.updateChildren(body);

        updateChildrenCall.enqueue(new Callback<ResponseUpdateChildren>() {

            @Override
            public void onResponse(Call<ResponseUpdateChildren> call, Response<ResponseUpdateChildren> response) {
                spotsDialog.dismiss();
                try {
                    if (response.body().getDataChildren() != null) {
                        Toast.makeText(getContext(), "Berhasil Mengubah Anak!", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().remove(ChildEditFragment.this).commit();
                        loadFragment(new ChildManagementFragment());
                    } else {
                        Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Gagal Mengirim Data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateChildren> call, Throwable t) {
//                Toast.makeText(AddReviewActivity.this, "Gagal mengirim data"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("GAGAL", "onFailure: "+t.getMessage());
            }
        });
    }

}
