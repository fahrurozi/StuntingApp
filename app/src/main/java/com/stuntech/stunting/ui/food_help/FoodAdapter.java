package com.stuntech.stunting.ui.food_help;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.stuntech.stunting.R;
import com.stuntech.stunting.data.model.care.DataCare;
import com.stuntech.stunting.data.network.ApiService;
import com.stuntech.stunting.ui.info.InfoAdapter;
import com.stuntech.stunting.ui.info.InfoInterface;
import com.squareup.picasso.Picasso;
import com.stuntech.stunting.data.model.care.DataCare;
import com.stuntech.stunting.data.network.ApiService;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>{
    private List<DataCare> rvData = new ArrayList();
    ;
    private FoodInterface foodInterface;

    public FoodAdapter(FoodInterface foodInterface) {
        this.foodInterface = foodInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public LinearLayout llLabel;
        public ImageView ivBg;
        public CardView cvRoot;

        public ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvFoodName);
            llLabel = v.findViewById(R.id.llFoodLabel);
            ivBg = v.findViewById(R.id.ivFoodBackground);
            cvRoot = v.findViewById(R.id.cvFoodRoot);
        }
    }

    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_food, parent, false);
        return new FoodAdapter.ViewHolder(v);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(FoodAdapter.ViewHolder holder, int position) {
        DataCare data = rvData.get(position);
        holder.tvName.setText(data.getTitle());
        String[] label = data.getArticleTags().split("\\|");
        Log.e("TAG", "onBindViewHolder: " + ApiService.BASE_URL +"/static/"+ data.getArticleCoverFile());
        Picasso.get().load(ApiService.BASE_URL +"static/"+ data.getArticleCoverFile()).into(holder.ivBg);
        for (String textLabel : label) {
            TextView tvLabel = new TextView(holder.cvRoot.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd(4);
            tvLabel.setLayoutParams(params);
            tvLabel.setPadding(20, 10, 20, 10);
            tvLabel.setTextSize(12);
            tvLabel.setTextColor(holder.cvRoot.getContext().getResources().getColor(R.color.white));
            tvLabel.setBackground(holder.cvRoot.getContext().getResources().getDrawable(R.drawable.round_orange));
            tvLabel.setText(textLabel);
            holder.llLabel.addView(tvLabel);
        }

        holder.cvRoot.setOnClickListener(r -> {
            if (foodInterface != null) {
                foodInterface.onChildClick(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public void insertDataList(List<DataCare> inputData) {
        this.rvData.clear();
        this.rvData.addAll(inputData);
        notifyDataSetChanged();
    }
}
