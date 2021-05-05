package com.example.tabletorder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetRetrofit {

    private static NetRetrofit outInstance = new NetRetrofit();
    public static NetRetrofit getInstance(){
        return outInstance;
    }
    private NetRetrofit(){
    }



    OrderApi service = OrderApi.retrofit.create(OrderApi.class);

    public OrderApi getService(){
        return service;
    }

}