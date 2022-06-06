package com.example.stunting.data.network;

import com.example.stunting.data.model.child.ResponseChild;
import com.example.stunting.data.model.child.ResponsePutChild;
import com.example.stunting.data.model.login.ResponseLogin;
import com.example.stunting.data.model.maps.ResponseMaps;
import com.example.stunting.data.model.register.ResponseRegister;
import com.example.stunting.data.model.token.ResponseToken;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiEndpoint {
    @POST("api/v1/user")
    Call<ResponseRegister> register(
            @Body RequestBody body
    );

    @POST("token_authentication/get_token")
    Call<ResponseToken> login(
            @Body RequestBody body
    );

    @GET("api/v1/user")
    Call<ResponseLogin> getProfile(
            @Header("token") String token
    );

    @GET("api/v1/trace")
    Call<ResponseChild> getTrace(
            @Header("token") String token
    );

    @POST("api/v1/trace")
    Call<ResponsePutChild> putTrace(
            @Header("token") String token,
            @Body RequestBody body
    );


    @POST("api/v1/maps")
    Call<ResponseMaps> getMaps(
            @Header("token") String token,
            @Body RequestBody body
    );
}
