package com.example.stunting.data.network;

import com.example.stunting.data.model.care.ResponseCare;
import com.example.stunting.data.model.child.ResponseChild;
import com.example.stunting.data.model.child.ResponsePutChild;
import com.example.stunting.data.model.child.ResponseUpdateChild;
import com.example.stunting.data.model.children.ResponseAddChildren;
import com.example.stunting.data.model.children.ResponseChildren;
import com.example.stunting.data.model.fun.ResponseFun;
import com.example.stunting.data.model.fun.ResponseLevelAvailable;
import com.example.stunting.data.model.fun.ResponseScorePerLevel;
import com.example.stunting.data.model.fun.ResponseSubmitFun;
import com.example.stunting.data.model.login.ResponseLogin;
import com.example.stunting.data.model.maps.ResponseMaps;
import com.example.stunting.data.model.maps.ResponseMapsById;
import com.example.stunting.data.model.register.ResponseRegister;
import com.example.stunting.data.model.reminder.ResponseAddReminder;
import com.example.stunting.data.model.reminder.ResponseReminder;
import com.example.stunting.data.model.review.ResponseAddReview;
import com.example.stunting.data.model.review.ResponseReview;
import com.example.stunting.data.model.token.ResponseToken;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiEndpoint {

    String CUSTOM_HEADER = "@";
    String NO_AUTH = "NoAuth";

    @Headers(CUSTOM_HEADER + ": " + NO_AUTH)
    @POST("api/v1/user")
    Call<ResponseRegister> register(
            @Body RequestBody body
    );

    @Headers(CUSTOM_HEADER + ": " + NO_AUTH)
    @POST("token_authentication/get_token")
    Call<ResponseToken> login(
            @Body RequestBody body
    );

    @Headers(CUSTOM_HEADER + ": " + NO_AUTH)
    @POST("token_authentication/refresh_token")
    Call<ResponseToken> refreshToken(
            @Header("token") String token
    );

    @GET("api/v1/user")
    Call<ResponseLogin> getProfile(
    );

    @GET("api/v1/trace")
    Call<ResponseChild> getTrace(
            @Query("child_id") Integer child_id
    );

    @POST("api/v1/trace")
    Call<ResponsePutChild> putTrace(
            @Body RequestBody body
    );

    @PATCH("api/v1/trace")
    Call<ResponseUpdateChild> patchTrace(
            @Body RequestBody body
    );


//    @POST("api/v1/maps")
//    Call<ResponseMaps> getMaps(
//            @Body RequestBody body
//    );

    @GET("api/v1/maps")
    Call<ResponseMaps> getMaps(
            @Query("json_body") String body
    );

    @GET("api/v1/maps")
    Call<ResponseMapsById> getMapsById(
            @Query("json_body") String body
    );

    @POST("api/v1/article")
    Call<ResponseCare> getArticle(
            @Body RequestBody body
    );

    @GET("api/v1/reminder")
    Call<ResponseReminder> getReminder(
    );

    @POST("api/v1/reminder")
    Call<ResponseAddReminder> addReminder(
            @Body RequestBody body
    );

    @POST("api/v1/article")
    Call<ResponseCare> getStuntingInfo(
            @Body RequestBody body
    );

    @GET("api/v1/review")
    Call<ResponseReview> getReview(
            @Query("json_body") String body
    );

    @POST("api/v1/review")
    Call<ResponseAddReview> addReview(
            @Body RequestBody body
    );

    @GET("api/v1/children_management")
    Call<ResponseChildren> getChildrenList();

    @POST("api/v1/children_management")
    Call<ResponseAddChildren> addChildren(
            @Body RequestBody body
    );

    @GET("api/v1/children_management")
    Call<ResponseChildren> getChildren(
            @Query("child_id") Integer child_id
    );

    @GET("/api/v1/fun_stunt_user")
    Call<ResponseLevelAvailable> getFunStuntUser(
            @Query("json_body") String json_body
    );

    @GET("/api/v1/fun_stunt_user")
    Call<ResponseScorePerLevel> getFunUserSummary(
            @Query("json_body") String json_body
    );

    @GET("/api/v1/fun_stunt_user")
    Call<ResponseFun> getQuestionPerLevel(
            @Query("json_body") String json_body
    );

    @POST("/api/v1/fun_stunt_user")
    Call<ResponseSubmitFun> postSubmitAnswer(
            @Body RequestBody body
    );
}
