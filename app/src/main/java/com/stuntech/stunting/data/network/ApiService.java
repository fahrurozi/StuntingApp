package com.stuntech.stunting.data.network;

import com.stuntech.stunting.data.network.interceptor.AuthInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiService {
    private static ApiEndpoint retrofit;
    public final static String BASE_URL = "http://104.197.9.244:8000/";
//    public final static String BASE_URL = "http://192.168.1.6:8000/";

    public static ApiEndpoint getRetrofitInstance() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new AuthInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(client)
                    .build()
                    .create(ApiEndpoint.class);
        }

        return retrofit;
    }
}
