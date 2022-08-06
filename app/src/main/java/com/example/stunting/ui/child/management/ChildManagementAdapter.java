package com.example.stunting.ui.child;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.R;
import com.example.stunting.data.model.child.DataChild;
import com.example.stunting.data.model.children.DataChildren;

import java.util.ArrayList;
import java.util.List;

public class ChildManagementAdapter extends RecyclerView.Adapter<ChildManagementAdapter.ViewHolder> {
    private List<DataChildren> rvData = new ArrayList();;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvdescription;

        public ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvChildName);
            tvdescription = v.findViewById(R.id.tvChildDescription);

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

//        holder.llRoot.setOnClickListener(r -> {
//            if (childInterface != null) {
//                childInterface.onChildClick(data);
//            }
//        });
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
