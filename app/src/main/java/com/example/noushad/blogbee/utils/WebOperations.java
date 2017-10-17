package com.example.noushad.blogbee.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.noushad.blogbee.Interface.ApiInterface;
import com.example.noushad.blogbee.Retrofit.ServiceGenerator;
import com.example.noushad.blogbee.model.CPResponseModel.CPSuccessResponse;
import com.example.noushad.blogbee.model.CPResponseModel.Error;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by noushad on 10/9/17.
 */

public class WebOperations {
    private static ApiInterface mService = ServiceGenerator.createService(ApiInterface.class);

    public static void loadImage(final Context context, final ImageView imageView, final String url) {

        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().crossFade().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                return false;
            }
        }).into(imageView);
    }


    public static boolean createPost(final Context context, String authToken, String title, String description, String filePath) {
        final boolean[] status = new boolean[1];
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("cover_image", file.getName(), requestFile);


        Call<CPSuccessResponse> responseCall = mService.createPost(authToken, title, description, body);
        responseCall.enqueue(new Callback<CPSuccessResponse>() {
            @Override
            public void onResponse(Call<CPSuccessResponse> call, Response<CPSuccessResponse> response) {
                if (response.isSuccessful()) {
                    CPSuccessResponse successResponse = response.body();
                    Toast.makeText(context, "Post Created Successfully", Toast.LENGTH_SHORT).show();
                    status[0] = true;
                } else {
                    Gson gson = new GsonBuilder().create();
                    Error pojo;
                    try {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_LONG).show();
                        JSONObject errorObj = new JSONObject(response.errorBody().string());
                        pojo = gson.fromJson(errorObj.getJSONObject("error").toString(), Error.class);
                        Toast.makeText(context, pojo.toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CPSuccessResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return status[0];
    }
}
