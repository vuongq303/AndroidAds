package com.hq.projectads.retrofit;

import com.hq.projectads.model.Request;
import com.hq.projectads.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("/login/signUp")
    Call<Integer> signUp(@Body User user);

    @POST("/login/signIn")
    Call<Integer> signIn(@Body User user);

    @POST("/login/congTien")
    Call<Integer> congTien(@Body User user);

    @POST("/send/sendRequest")
    Call<Integer> sendRequest(@Body Request request);

    @POST("/send/getUserObject")
    Call<User> getUserObject(@Body User user);

    @POST("/send/getRequestObject")
    Call<Request> getRequestObject(@Body Request request);

}
