package com.example.noushad.blogbee.model.CPResponseModel;

public class CPErrorResponse{
	private int code;
	private Error error;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setError(Error error){
		this.error = error;
	}

	public Error getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"CPErrorResponse{" + 
			"code = '" + code + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}
