package com.example.stunting.ui.fun;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stunting.R;
import com.example.stunting.data.model.child.DataChild;
import com.example.stunting.data.model.fun.ResponseLevelAvailable;
import com.example.stunting.data.model.fun.TestModel;
import com.example.stunting.data.model.maps.DataPlace;
import com.example.stunting.ui.MainActivity;
import com.example.stunting.ui.child.ChildAdapter;
import com.example.stunting.ui.child.ChildFragment;
import com.example.stunting.ui.child.ChildInterface;

import java.util.ArrayList;
import java.util.List;

public class FunAdapter extends RecyclerView.Adapter<FunAdapter.ViewHolder>{

//    List<Integer> rvData = new ArrayList<Integer>();

    private List<TestModel> rvData = new ArrayList();;
    private FunInterface funInterface;

//
    public FunAdapter(FunInterface funInterface) {
        this.funInterface = funInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLevel;
        public TextView tvWeek;
        public LinearLayout llRoot;
        public RatingBar rbRating;
        public ImageView ivLock;

        public ViewHolder(View v) {
            super(v);
            tvLevel = v.findViewById(R.id.tvLevel);
            rbRating = v.findViewById(R.id.rbRating);
//            tvWeek = v.findViewById(R.id.tvWeek);
            llRoot = v.findViewById(R.id.llRoot);
            ivLock = v.findViewById(R.id.ivLock);
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
        TestModel data = rvData.get(position);
        holder.tvLevel.setText(rvData.get(position).getLevel().toString());
        if (rvData.get(position).getDataScorePerLevel().getCorrectAnswerCount() == null || rvData.get(position).getDataScorePerLevel().getCorrectAnswerCount() == 0) {
            holder.llRoot.setBackground(holder.llRoot.getContext().getResources().getDrawable(R.drawable.round_week_null));
        } else {
            holder.llRoot.setBackground(holder.llRoot.getContext().getResources().getDrawable(R.drawable.round_week));
        }
        holder.rbRating.setRating(rvData.get(position).getDataScorePerLevel().getCorrectAnswerCount());

        if(position==0){
            holder.llRoot.setOnClickListener(r -> {
                holder.llRoot.setOnClickListener(v -> funInterface.onChildClick(data));
            });
        }else{
            if(rvData.get(position-1).getDataScorePerLevel().getCorrectAnswerCount() == null || rvData.get(position-1).getDataScorePerLevel().getCorrectAnswerCount() == 0) {
                holder.llRoot.setBackground(holder.llRoot.getContext().getResources().getDrawable(R.drawable.round_week_disable));
                holder.tvLevel.setVisibility(View.GONE);
                holder.rbRating.setVisibility(View.GONE);
                holder.ivLock.setVisibility(View.VISIBLE);
                holder.llRoot.setOnClickListener(r -> {
                    Toast.makeText(holder.llRoot.getContext(), "Selesaikan level sebelumnya! ", Toast.LENGTH_SHORT).show();
                });
            }else{
                holder.llRoot.setOnClickListener(r -> {
                    holder.llRoot.setOnClickListener(v -> funInterface.onChildClick(data));
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public void insertDataList(List<TestModel> inputData) {
        this.rvData.clear();
        this.rvData.addAll(inputData);
        notifyDataSetChanged();
    }

}
