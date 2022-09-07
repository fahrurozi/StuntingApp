package com.stuntech.stunting.root;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.stuntech.stunting.BuildConfig;
import com.stuntech.stunting.data.db.RoomDB;
import com.stuntech.stunting.data.network.ApiEndpoint;
import com.stuntech.stunting.data.network.ApiService;
import com.stuntech.stunting.data.db.RoomDB;
import com.stuntech.stunting.data.network.ApiEndpoint;
import com.stuntech.stunting.data.network.ApiService;

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
