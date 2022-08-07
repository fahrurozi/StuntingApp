package com.example.stunting.ui.child.management;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.R;
import com.example.stunting.data.model.child.DataChild;
import com.example.stunting.data.model.children.DataChildren;
import com.example.stunting.ui.MainActivity;
import com.example.stunting.ui.child.ChildFragment;

import java.util.ArrayList;
import java.util.List;

public class ChildManagementAdapter extends RecyclerView.Adapter<ChildManagementAdapter.ViewHolder> {
    private List<DataChildren> rvData = new ArrayList();;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvdescription;
        public CardView cvRoot;

        public ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvChildName);
            tvdescription = v.findViewById(R.id.tvChildDescription);
            cvRoot = v.findViewById(R.id.cvRoot);

        }
    }

    @Override
    public ChildManagementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_anak, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataChildren data = rvData.get(position);
        holder.tvName.setText(data.getName());
        holder.tvdescription.setText(data.getBornDate());

        holder.cvRoot.setOnClickListener(r -> {
            Bundle bundle = new Bundle();
            bundle.putInt("childId", data.getId());
// set Fragmentclass Arguments
            ChildFragment fragmentobj = new ChildFragment();
            fragmentobj.setArguments(bundle);
//            FragmentManager manager = ((MainActivity)context).getSupportFragmentManager();
            FragmentManager manager = ((MainActivity)r.getContext()).getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.flHome, fragmentobj).addToBackStack(null).commit();
            Toast.makeText(holder.cvRoot.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public void insertDataList(List<DataChildren> inputData) {
        this.rvData.clear();
        this.rvData.addAll(inputData);
        notifyDataSetChanged();
    }
}
