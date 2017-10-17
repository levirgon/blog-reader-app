package com.example.noushad.blogbee.Interface;

import com.example.noushad.blogbee.model.CPResponseModel.CPSuccessResponse;
import com.example.noushad.blogbee.model.CommentSuccessResponse;
import com.example.noushad.blogbee.model.ViewModel.UserDetails;
import com.example.noushad.blogbee.model.allPostsResponseModel.AllpostsResponse;
import com.example.noushad.blogbee.model.loginResponseModel.LogInSuccessResponse;
import com.example.noushad.blogbee.model.registerResponseModel.RegResponse;
import com.example.noushad.blogbee.model.singlePostResponse.PostDetails;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by noushad on 8/14/17.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("registration")
    Call<RegResponse> createUser(
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
    Call<AllpostsResponse> getPostsByPage(
            @Query("page") int pageIndex);

    @GET("posts/{index}")
    Call<PostDetails> getSpecifiedPost(
            @Path("index") int index);


    @GET("userinfo")
    Call<UserDetails> GetLoggedInUserData(
            @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("posts/{index}/comments")
    Call<CommentSuccessResponse> postComment(@Path("index") int index, @Header("Authorization")
            String authorization, @Field("description") String comment);


    @Multipart
    @POST("posts")
    Call<CPSuccessResponse> createPost(@Header("Authorization") String authorization,
                                       @Part("title") String title,
                                       @Part("description") String description,
                                       @Part MultipartBody.Part file);

}
