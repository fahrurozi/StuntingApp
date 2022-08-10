package com.example.stunting.ui.fun;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.R;
import com.example.stunting.data.model.child.DataChild;
import com.example.stunting.ui.child.ChildAdapter;
import com.example.stunting.ui.child.ChildInterface;

import java.util.ArrayList;
import java.util.List;

public class FunAdapter extends RecyclerView.Adapter<FunAdapter.ViewHolder>{

    List<Integer> rvData = new ArrayList<Integer>();
//    private FunInterface funInterface;
//
//    public FunAdapter(FunInterface funInterface) {
//        this.funInterface = funInterface;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLevel;
        public TextView tvWeek;
        public LinearLayout llRoot;

        public ViewHolder(View v) {
            super(v);
            tvLevel = v.findViewById(R.id.tvLevel);
//            tvWeek = v.findViewById(R.id.tvWeek);
//            llRoot = v.findViewById(R.id.llRoot);
        }
    }

    @Override
    public FunAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_fun, parent, false);
        return new FunAdapter.ViewHolder(v);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(FunAdapter.ViewHolder holder, int position) {
//        DataChild data = rvData.get(position);
        holder.tvLevel.setText(rvData.get(position).toString());
        Log.d("HOI", String.valueOf(rvData.get(position)+1));
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public void insertDataList(List<Integer> inputData) {
        this.rvData.clear();
        this.rvData.addAll(inputData);
        notifyDataSetChanged();
    }

}
