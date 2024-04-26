package com.hq.projectads.retrofit;

import com.hq.projectads.utils.valLocal;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFunc {
    public static RetrofitAPI createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(valLocal.urlLocal)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitAPI.class);
    }
}