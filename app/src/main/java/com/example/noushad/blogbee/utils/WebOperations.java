package com.example.noushad.blogbee.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.noushad.blogbee.Interface.ApiInterface;
import com.example.noushad.blogbee.Retrofit.ServiceGenerator;
import com.example.noushad.blogbee.activity.FragmentContainerActivity;
import com.example.noushad.blogbee.model.CPResponseModel.CPSuccessResponse;
import com.example.noushad.blogbee.model.CPResponseModel.Error;
import com.example.noushad.blogbee.model.ViewModel.UserDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by noushad on 10/9/17.
 */

public class WebOperations {
    private static ApiInterface mService = ServiceGenerator.createService(ApiInterface.class);


    public static void loadImage(final Context context, final ImageView imageView, final String url) {

        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().crossFade().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Log.d(TAG, "onException: " + url);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);
    }

    public static boolean hasValidPath(String coverPhotoPath) {

        if (coverPhotoPath.contains(".jpg"))
            return true;

        return false;
    }

    public static void createPost(final Context context, String authToken, String title, String description, File file) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Posting...");
        progressDialog.show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("cover_image", file.getName(), requestFile);

        Call<CPSuccessResponse> responseCall = mService.createPost(authToken, title, description, body);
        responseCall.enqueue(new Callback<CPSuccessResponse>() {
            @Override
            public void onResponse(Call<CPSuccessResponse> call, Response<CPSuccessResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CPSuccessResponse successResponse = response.body();
                    Toast.makeText(context, "Post Created Successfully", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, FragmentContainerActivity.class));
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
                progressDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public static void updateUserInformation(final Activity activity, final String key, String value) {
        Call<UserDetails> responseCall = null;
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Updating " + key.toUpperCase() + "...");
        progressDialog.show();

        if (key.equals("name")) {
            responseCall = mService.updateUserName(SharedPrefManager.
                    getInstance(activity).getUserId(), SharedPrefManager.getInstance(activity).getAuthToken(), value);
        } else if (key.equals("email")) {
            responseCall = mService.updateUserEmail(SharedPrefManager.
                    getInstance(activity).getUserId(), SharedPrefManager.getInstance(activity).getAuthToken(), value);
        } else if (key.equals("phone_no")) {
            responseCall = mService.updateUserPhone(SharedPrefManager.
                    getInstance(activity).getUserId(), SharedPrefManager.getInstance(activity).getAuthToken(), value);
        } else if (key.equals("password")) {
            responseCall = mService.updateUserPassword(SharedPrefManager.
                    getInstance(activity).getUserId(), SharedPrefManager.getInstance(activity).getAuthToken(), value, value);
        }

        assert responseCall != null;
        responseCall.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    UserDetails user = response.body();
                    Toast.makeText(activity, key.toUpperCase() + " Updated Successfully", Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(activity).userOwnDataUpdate(user);

                } else {
                    Toast.makeText(activity, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateUserPhoto(final Activity context, final String key, File file) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Updating " + key.toUpperCase() + "...");
        progressDialog.show();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("cover_image", file.getName(), requestFile);
        Call<UserDetails> call = mService.updateUserPhoto(SharedPrefManager.
                getInstance(context).getUserId(), SharedPrefManager.getInstance(context).getAuthToken(), body);


        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    UserDetails user = response.body();
                    Log.d(TAG, "COVER_UPLOAD_ERROR : " + response.body());
                    Toast.makeText(context, key.toUpperCase() + " Updated Successfully", Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(context).userOwnDataUpdate(user);
                } else {
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
                    try {
                        Log.d(TAG, "COVER_UPLOAD_ERROR : " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
