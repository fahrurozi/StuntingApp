package com.example.stunting.root;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.stunting.BuildConfig;
import com.example.stunting.data.db.RoomDB;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;

public class App extends Application {
    public static App instance;
    public static ApiEndpoint apiService;
    public static SharedPreferences sharedPref;
    public static RoomDB roomDB;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        apiService = ApiService.getRetrofitInstance();
        sharedPref = this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        roomDB = RoomDB.Companion.getInstance(this);
    }
}
