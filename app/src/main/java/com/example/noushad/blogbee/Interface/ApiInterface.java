package com.example.noushad.blogbee.Interface;

import com.example.noushad.blogbee.model.allPostsResponseModel.AllpostsResponse;
import com.example.noushad.blogbee.model.loginResponseModel.LogInSuccessResponse;
import com.example.noushad.blogbee.model.registerResponseModel.RegSuccessResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by noushad on 8/14/17.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("registration")
    Call<RegSuccessResponse> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone_no") String phoneNumber,
            @Field("password_confirmation") String passwordConfirmation);

    @FormUrlEncoded
    @POST("login")
    Call<LogInSuccessResponse> userLogin(
            @Field("username") String email,
            @Field("password") String password
    );

    @GET("posts")
    Call<AllpostsResponse> getAllposts();



}
