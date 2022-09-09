package com.stuntech.stunting.ui.home;

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
import com.stuntech.stunting.data.model.children.ResponseDetailAllChildren;
import com.stuntech.stunting.data.network.ApiService;
import com.stuntech.stunting.ui.hello_stunting.HelloAdapter;
import com.stuntech.stunting.ui.hello_stunting.HelloInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HomeTraceAdapter extends RecyclerView.Adapter<HomeTraceAdapter.ViewHolder>{
    private List<ResponseDetailAllChildren> rvData = new ArrayList();
    ;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvChildName, tvChildGender, tvHealthStatus;
        public CardView cvRoot;


        public ViewHolder(View v) {
            super(v);
            tvChildName = v.findViewById(R.id.tvChildName);
            tvChildGender = v.findViewById(R.id.tvChildGender);
            tvHealthStatus = v.findViewById(R.id.tvHealthStatus);
            cvRoot = v.findViewById(R.id.cvRoot);
        }
    }

    @Override
    public HomeTraceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_anak_home, parent, false);
        return new HomeTraceAdapter.ViewHolder(v);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(HomeTraceAdapter.ViewHolder holder, int position) {
        ResponseDetailAllChildren data = rvData.get(position);
        holder.tvChildName.setText(data.getDataChildren().getName());
        holder.tvChildGender.setText(data.getDataChildren().getBornDate());

        Integer glSangatPendek = -2;
        Integer glPendek = -1;
        Integer glTengah = 0;
        Integer glNormal = 1;
        Integer glTinggi = 2;
        if(data.getDataChildTrace()!= null){
            Integer growthLevel = data.getDataChildTrace().getGrowth_level();
            Log.d("HAI", String.valueOf(growthLevel));
            if(growthLevel == glSangatPendek){
                holder.tvHealthStatus.setText("Sangat Pendek");
            }else if(growthLevel == glPendek){
                holder.tvHealthStatus.setText("Pendek");
            }else if(growthLevel == glTengah){
                holder.tvHealthStatus.setText("Normal");
            }else if(growthLevel == glNormal){
                holder.tvHealthStatus.setText("Normal");
            }else if(growthLevel == glTinggi){
                holder.tvHealthStatus.setText("Tinggi");
            }
        }else{
            holder.tvHealthStatus.setText("Tidak ada data");
        }

//        holder.cvRoot.setOnClickListener(r -> {
//            if (helloInterface != null) {
//                helloInterface.onChildClick(data);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public void insertDataList(List<ResponseDetailAllChildren> inputData) {
        this.rvData.clear();
        this.rvData.addAll(inputData);
        notifyDataSetChanged();
    }
}
