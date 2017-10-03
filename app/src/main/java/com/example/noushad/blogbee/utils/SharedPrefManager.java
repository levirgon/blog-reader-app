package com.example.noushad.blogbee.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.noushad.blogbee.model.CreatorInfo;
import com.example.noushad.blogbee.model.ViewModel.UserViewModel;
import com.example.noushad.blogbee.model.loginResponseModel.LogInSuccessResponse;

/**
 * Created by tapos on 10/3/17.
 */

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedprefretrofit";

    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_NAME = "keyusername";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_PROFILE_PIC = "keyuseremail";
    private static final String KEY_USER_TOKEN_TYPE = "keyusertokentype";
    private static final String KEY_USER_ACCESS_TOKEN = "keyuseraccesstoken";
    private static final String KEY_USER_REFRESH_TOKEN = "keyuserrefreshtoken";
    private static final String KEY_USER_TOKEN_EXPIRE = "keyusertokenexpire";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
//
    public boolean userLoginDataUpdate(LogInSuccessResponse loginData) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_TOKEN_TYPE, loginData.getTokenType());
        editor.putString(KEY_USER_ACCESS_TOKEN, loginData.getAccessToken());
        editor.putString(KEY_USER_REFRESH_TOKEN, loginData.getRefreshToken());
        editor.putInt(KEY_USER_TOKEN_EXPIRE, loginData.getExpiresIn());
        editor.apply();
        return true;
    }
//
    public boolean userOwnDataUpdate(CreatorInfo user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_PROFILE_PIC, user.getCoverPhoto());
        editor.apply();
        return true;
    }
//
//
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_ACCESS_TOKEN, null) != null)
            return true;
        return false;
    }

    public String getAuthToken(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info = sharedPreferences.getString(KEY_USER_TOKEN_TYPE, null)
                +" "+ sharedPreferences.getString(KEY_USER_ACCESS_TOKEN, null);
        System.out.println(info);
        return info;
    }
//
//    public boolean isGetUserData() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        if (sharedPreferences.getString(KEY_USER_EMAIL, null) != null)
//            return true;
//        return false;
//    }
//
    public UserViewModel getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return new UserViewModel(
                sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_NAME, null),
                sharedPreferences.getString(KEY_USER_EMAIL, null),

                sharedPreferences.getString(KEY_USER_PROFILE_PIC, null)
        );
    }
//
    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}