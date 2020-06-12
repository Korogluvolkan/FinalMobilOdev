package com.example.volkankoroglu.api;

import com.example.volkankoroglu.models.User;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("api/users")
    Call<ResponseBody> listData();

    @GET("api/users/{id}")
    Call<ResponseBody> getUser(@Path("id") int user_id);


    @Headers({"Accept:application/json", "Content-Type:application/json;"})
    @POST("api/users")
    Call<ResponseBody> setUser(@Body RequestBody user);

    @POST("api/login")
    Call<ResponseBody> login(@Body RequestBody user);


    @POST("api/register")
    Call<ResponseBody> register(@Body RequestBody user);
}
