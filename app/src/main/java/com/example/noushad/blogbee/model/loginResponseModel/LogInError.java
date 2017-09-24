package com.example.noushad.blogbee.model.loginResponseModel;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class LogInError {

    @SerializedName("code")
    private int code;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return
                "LogInError{" +
                        "code = '" + code + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}