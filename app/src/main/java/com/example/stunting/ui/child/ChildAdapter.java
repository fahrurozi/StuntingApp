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

import java.util.ArrayList;
import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {
    private List<DataChild> rvData = new ArrayList();;
    private ChildInterface childInterface;

    public ChildAdapter(ChildInterface childInterface) {
        this.childInterface = childInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCount;
        public TextView tvWeek;
        public LinearLayout llRoot;

        public ViewHolder(View v) {
            super(v);
            tvCount = v.findViewById(R.id.tvCount);
            tvWeek = v.findViewById(R.id.tvWeek);
            llRoot = v.findViewById(R.id.llRoot);
        }
    }

    @Override
    public ChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_child, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataChild data = rvData.get(position);
        holder.tvCount.setText(data.getWeek_title());
        holder.tvWeek.setText(data.getWeek_name());
        if (data.getId() == null) {
            holder.llRoot.setBackground(holder.llRoot.getContext().getResources().getDrawable(R.drawable.round_week_null));
        } else {
            holder.llRoot.setBackground(holder.llRoot.getContext().getResources().getDrawable(R.drawable.round_week));
        }

        holder.llRoot.setOnClickListener(r -> {
            if (childInterface != null) {
                childInterface.onChildClick(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public void insertDataList(List<DataChild> inputData) {
        this.rvData = inputData;
        notifyDataSetChanged();
    }
}
