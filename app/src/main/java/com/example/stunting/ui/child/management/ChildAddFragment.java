package com.example.stunting.ui.child.management;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;
import com.example.stunting.ui.child.ChildFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONStringer;

import java.text.DateFormat;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildAddFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private SharedPreferences sharedPref;
    private TextView etDOB, etName;
    private FloatingActionButton fabSimpan;

    private SpotsDialog spotsDialog;

    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();

    public Integer dates, months, years;
    public Integer intGender;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        etDOB = view.findViewById(R.id.etDOB);
        fabSimpan = view.findViewById(R.id.fabSimpan);
        etName = view.findViewById(R.id.etName);

        spotsDialog = new SpotsDialog(getContext(), "Mohon Tunggu...");

        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(ChildAddFragment.this, 0);
                datePicker.show(getFragmentManager(), "date picker");

            }
        });

        fabSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner drop_jenisKelamin=(Spinner) getView().findViewById(R.id.spinnerGender);
                String valJenisKelamin=drop_jenisKelamin.getSelectedItem().toString();
                String name = etName.getText().toString();
                String dob = etDOB.getText().toString();
                if (name.isEmpty() || dob.isEmpty()) {
                    Toast.makeText(getContext(), "Silahkan lengkapi field yang ada!", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getContext(), name+dob+valJenisKelamin, Toast.LENGTH_SHORT).show();
                    if(valJenisKelamin.equals("Perempuan")){
                        intGender = 1;
                    }else if(valJenisKelamin.equals("Laki-laki")) {
                        intGender = 0;
                    }

                    try {
                        spotsDialog.show();
                        addChildren(name, dates, months, years, intGender);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    private void addChildren(String childName, Integer date, Integer month, Integer year, Integer gender) throws JSONException {
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
        json.key("active").value(true);
        json.endObject();
        json.endObject();
        Log.e("HALOO", json.toString() );

        body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
        Call<ResponseAddChildren> addChildrenCall = endpoint.addChildren(body);

        addChildrenCall.enqueue(new Callback<ResponseAddChildren>() {

            @Override
            public void onResponse(Call<ResponseAddChildren> call, Response<ResponseAddChildren> response) {
                spotsDialog.dismiss();
                try {
                    if (response.body().getDataChildren() != null) {
                        Toast.makeText(getContext(), "Berhasil Menambah Anak!", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().remove(ChildAddFragment.this).commit();
                        loadFragment(new ChildManagementFragment());
                    } else {
                        Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Gagal Mengirim Data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAddChildren> call, Throwable t) {
//                Toast.makeText(AddReviewActivity.this, "Gagal mengirim data"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("GAGAL", "onFailure: "+t.getMessage());
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_anak, container, false);
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

        Log.d("HAI", "onDateSet: "+dates+"-"+months+"-"+years);

        etDOB.setText(currentDate);
    }
}
