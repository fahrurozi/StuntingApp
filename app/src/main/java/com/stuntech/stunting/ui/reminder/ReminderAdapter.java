package com.stuntech.stunting.ui.reminder;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.stuntech.stunting.R;
import com.stuntech.stunting.data.model.reminder.DataReminder;
import com.stuntech.stunting.ui.care_nutrition.CareAdapter;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.HolderData> {
    private Context ctx;
    private List<DataReminder> listData;
    private int idReminder;
    private ReminderAdapter adapter;


    public ReminderAdapter(Context ctx, List<DataReminder> listData) {
        this.ctx = ctx;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reminder, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataReminder dr = listData.get(position);

        holder.tvRId.setText(String.valueOf(dr.getId()));
        holder.tvRTime.setText(dr.getClock());
        holder.tvRDate.setText(dr.getRepeat_each());

        if(dr.isOn() == true){
        holder.sReminder.setChecked(true);
        }


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvRId, tvRTime, tvRDate, tvRTitle;
        Switch sReminder;


        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvRId = itemView.findViewById(R.id.tvRId);
            tvRTime = itemView.findViewById(R.id.tvRTime);
            tvRDate = itemView.findViewById(R.id.tvRDate);
            tvRTitle = itemView.findViewById(R.id.tvRTitle);
            sReminder = itemView.findViewById(R.id.sReminder);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Apakah Anda Ingin Menghapus Data?");
                    dialogPesan.setTitle("Perhatian");
                    dialogPesan.setIcon(R.mipmap.ic_launcher_round);
                    dialogPesan.setCancelable(true);

                    idReminder = Integer.parseInt(tvRId.getText().toString());

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialogPesan.show();
                    return false;
                }
            });
        }

        private void deleteData(){

        }
    }

}
