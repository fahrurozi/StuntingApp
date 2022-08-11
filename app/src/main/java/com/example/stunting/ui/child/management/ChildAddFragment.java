package com.example.stunting.ui.child.management;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.stunting.BuildConfig;
import com.example.stunting.R;
import com.example.stunting.data.model.children.ResponseAddChildren;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;
import com.example.stunting.ui.child.ChildFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONStringer;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildAddFragment extends Fragment {
    private SharedPreferences sharedPref;
    private EditText etDOB;
    private FloatingActionButton fabSimpan;

    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        etDOB = view.findViewById(R.id.etDOB);
        fabSimpan = view.findViewById(R.id.fabSimpan);

        fabSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addChildren();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void addChildren() throws JSONException {
        RequestBody body;
        JSONStringer json = new JSONStringer();
        json.object();
        json.key("child_info");
        json.object();
        json.key("name").value("halo");
        json.key("born_date");
        json.array();
        json.value(2020);
        json.value(01);
        json.value(11);
        json.endArray();
        json.key("gender").value(1);
        json.key("active").value(true);
        json.endObject();
        json.endObject();
        Log.e("HALOO", json.toString() );

        body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
        Call<ResponseAddChildren> addChildrenCall = endpoint.addChildren(body);

        addChildrenCall.enqueue(new Callback<ResponseAddChildren>() {

            @Override
            public void onResponse(Call<ResponseAddChildren> call, Response<ResponseAddChildren> response) {
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
}
