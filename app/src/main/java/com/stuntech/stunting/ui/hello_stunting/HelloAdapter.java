package com.stuntech.stunting.ui.hello_stunting;

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
import com.squareup.picasso.Picasso;
import com.stuntech.stunting.data.model.care.DataCare;
import com.stuntech.stunting.data.network.ApiService;

import java.util.ArrayList;
import java.util.List;

public class HelloAdapter extends RecyclerView.Adapter<HelloAdapter.ViewHolder>{
    private List<DataCare> rvData = new ArrayList();
    ;
    private HelloInterface helloInterface;

    public HelloAdapter(HelloInterface helloInterface) {
        this.helloInterface = helloInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvDescription;
        public LinearLayout llLabel;
        public ImageView ivBg;
        public CardView cvRoot;

        public ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvHelloName);
            tvDescription = v.findViewById(R.id.tv_subtitle);
            llLabel = v.findViewById(R.id.llHelloLabel);
            ivBg = v.findViewById(R.id.ivHelloBackground);
            cvRoot = v.findViewById(R.id.cvHelloRoot);
        }
    }

    @Override
    public HelloAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hello_stunting, parent, false);
        return new HelloAdapter.ViewHolder(v);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(HelloAdapter.ViewHolder holder, int position) {
        DataCare data = rvData.get(position);
        holder.tvName.setText(data.getTitle());
        holder.tvDescription.setText(data.getArticleTags());
//        String[] label = data.getArticleTags().split("\\|");
        Log.e("TAG", "onBindViewHolder: " + ApiService.BASE_URL +"/static/"+ data.getArticleCoverFile());
        Picasso.get().load(ApiService.BASE_URL +"static/"+ data.getArticleCoverFile()).into(holder.ivBg);

        holder.cvRoot.setOnClickListener(r -> {
            if (helloInterface != null) {
                helloInterface.onChildClick(data);
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
