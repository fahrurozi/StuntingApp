package com.example.stunting.data.network.interceptor;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.stunting.R;
import com.example.stunting.data.model.token.ResponseToken;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.root.App;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

public class AuthInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request req = chain.request();

        if (req.headers().values(ApiEndpoint.CUSTOM_HEADER).contains(ApiEndpoint.NO_AUTH)) {
            return proceedWithToken(chain, req, null);
        }

        String token = App.sharedPref.getString("token", null);
        Response res = this.proceedWithToken(chain, req, token);
        if (res.code() != 401){
            return res;
        }else{
            Call<ResponseToken> loginCall = App.apiService.refreshToken(token);

            loginCall.enqueue(new retrofit2.Callback<ResponseToken>() {
                @Override
                public void onResponse(Call<ResponseToken> call, retrofit2.Response<ResponseToken> response) {
                    if (response.body().getToken() != null) {
                        App.sharedPref.edit().putString("token", response.body().getToken()).apply();
                        try {
                            proceedWithToken(chain, req, response.body().getToken());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        res.close();
                        try {
                            proceedWithToken(chain, req, null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseToken> call, Throwable t) {
                }
            });
        }
        return res;
    }

    private Response proceedWithToken(Chain chain, Request req, String token) throws IOException {
        Request.Builder builder = req.newBuilder();
        if (token != null) {
            builder.addHeader("Authorization", token);
        }

        Request request = builder.removeHeader("@").build();
        return chain.proceed(request);
    }

}
