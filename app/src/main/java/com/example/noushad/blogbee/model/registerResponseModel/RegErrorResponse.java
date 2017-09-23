package com.example.noushad.blogbee.model.registerResponseModel;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class RegErrorResponse {

	@SerializedName("code")
	private int code;

	@SerializedName("error")
	private Error error;

	public int getCode(){
		return code;
	}

	public Error getError(){
		return error;
	}


}