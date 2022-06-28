package com.example.stunting.ui.reminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stunting.BuildConfig;
import com.example.stunting.R;
import com.example.stunting.components.NotificationUtils;
import com.example.stunting.components.ScheduleUtils;
import com.example.stunting.data.model.child.DataChild;
import com.example.stunting.data.model.child.ResponsePutChild;
import com.example.stunting.data.model.reminder.DataReminder;
import com.example.stunting.data.model.reminder.ResponseAddReminder;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONStringer;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class ReminderAddActivity extends AppCompatActivity {

    private FloatingActionButton fabSimpan;
    private TimePicker tpInputTime;
    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_add);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        sharedPref = ReminderAddActivity.this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        fabSimpan = findViewById(R.id.fabSimpan);

        TimePicker tpInputTime=(TimePicker)findViewById(R.id.tpInputTime);
        tpInputTime.setIs24HourView(true);

        fabSimpan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int hour;
                int minute;
                String time;
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = tpInputTime.getHour();
                    minute = tpInputTime.getMinute();
                }
                else{
                    hour = tpInputTime.getCurrentHour();
                    minute = tpInputTime.getCurrentMinute();
                }
                time = hour+":"+minute;

                LocalTime timeNow = LocalTime.now();
                int seconds_now = timeNow.getHour()*3600+timeNow.getMinute()*60;
                int seconds_later = hour*3600+minute*60;
                System.out.println("SECONDS NEXT " + String.valueOf(seconds_later-seconds_now));
                new ScheduleUtils(getApplicationContext()).scheduleNotification(seconds_later-seconds_now, getString(R.string.title_stunt_reminder), getString(R.string.title_stunt_reminder_description));
                try {
                    addReminder(time);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void addReminder(String time) throws JSONException {
        RequestBody body;
        JSONStringer json = new JSONStringer();
        json.object();
        json.key("hour_minute").value(time);
        json.key("repeat_each").value("[\"MON\", \"MON\", \"MON\", \"MON\", \"MON\", \"MON\", \"MON\"]");
        json.key("on").value(true);
        json.endObject();
        Log.e("GAGAL", "addReminder: "+json );

        body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
        Call<ResponseAddReminder> addReminderCall = endpoint.addReminder(body);

        addReminderCall.enqueue(new Callback<ResponseAddReminder>() {

            @Override
            public void onResponse(Call<ResponseAddReminder> call, Response<ResponseAddReminder> response) {
                startActivity(new Intent(ReminderAddActivity.this, ReminderActivity.class));
            }

            @Override
            public void onFailure(Call<ResponseAddReminder> call, Throwable t) {
                Toast.makeText(ReminderAddActivity.this, "Gagal mengirim data"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("GAGAL", "onFailure: "+t.getMessage());
            }
        });
    }
}
