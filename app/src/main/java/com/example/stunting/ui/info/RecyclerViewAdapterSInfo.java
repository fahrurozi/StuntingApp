package com.example.stunting.ui.info;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.R;

import java.util.ArrayList;

public class RecyclerViewAdapterSInfo extends RecyclerView.Adapter<RecyclerViewAdapterSInfo.ViewHolder> {
    private ArrayList<String> rvData;

    public RecyclerViewAdapterSInfo(ArrayList<String> inputData) {
        this.rvData = inputData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvSubtitle;

        public ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_title);
            tvSubtitle = v.findViewById(R.id.tv_subtitle);
        }
    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_item_stunting_info, parent, false);
//        ViewHolder vh = new ViewHolder(v);
//        return vh;
//    }

    @Override
    public RecyclerViewAdapterSInfo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_item_stunting_info, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String name = rvData.get(position);
        holder.tvTitle.setText(name);
        holder.tvSubtitle.setText("Frau " + position + " DescripitonDescripitonDescripitonDescripitonDescripitonDescripitonDescripitonDescripitonDescripitonDescripiton");
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }
}
