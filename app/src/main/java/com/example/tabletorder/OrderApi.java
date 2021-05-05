package com.example.tabletorder;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderApi {

    @Headers(value = "Content-Type: application/json")
    @POST(value = "login")
    Call<auth> login(@Body RequestBody body);

    @GET(value = "ping")
    Call<Void> ping();

    @GET("/stores")
    Call<List<Map<String,Object>>> getStore(@Query("id") String id);

//    @POST("/create-table")
//    Call<Void> create_table(@Body RequestBody id,
//                          @Body RequestBody password,
//                          @Body RequestBody store_name,
//                          @Body RequestBody table_number,
//                          @Body RequestBody order_lists);

    @GET("/categories")
    Call<List<Map<String,Object>>> getCategories(@Query("id") String id,
                                                 @Query("store_name") String store_name);

    @GET("/menus")
    Call<List<Map<String,Object>>> getMenu(@Query("id") String id,
                                           @Query("store_name") String store_name,
                                           @Query("category") String category);

    @POST("/insert-order")
    Call<Void> insert_order(@Body RequestBody body);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://52.79.236.212:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}