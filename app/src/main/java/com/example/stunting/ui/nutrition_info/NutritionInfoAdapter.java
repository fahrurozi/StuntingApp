package com.example.stunting.ui.nutrition_info;

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

import com.example.stunting.R;
import com.example.stunting.data.model.care.DataCare;
import com.example.stunting.data.network.ApiService;
import com.example.stunting.ui.care_nutrition.CareAdapter;
import com.example.stunting.ui.care_nutrition.CareInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NutritionInfoAdapter extends RecyclerView.Adapter<NutritionInfoAdapter.ViewHolder>{
    private List<DataCare> rvData = new ArrayList();
    ;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public LinearLayout llLabel;
        public ImageView ivBg;
        public CardView cvRoot;

        public ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            llLabel = v.findViewById(R.id.llLabel);
            ivBg = v.findViewById(R.id.ivBackground);
            cvRoot = v.findViewById(R.id.cvRoot);
        }
    }

    @Override
    public NutritionInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_care, parent, false);
        return new NutritionInfoAdapter.ViewHolder(v);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(NutritionInfoAdapter.ViewHolder holder, int position) {
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
