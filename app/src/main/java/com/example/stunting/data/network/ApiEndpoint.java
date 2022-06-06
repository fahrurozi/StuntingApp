package com.example.stunting.data.network;

import com.example.stunting.data.model.register.ResponseRegister;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiEndpoint {
    @POST("api/v1/user")
    Call<ResponseRegister> register(
            @Body RequestBody body
    );


}
