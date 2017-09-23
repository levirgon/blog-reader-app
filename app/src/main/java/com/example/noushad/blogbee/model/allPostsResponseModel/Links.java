package com.example.noushad.blogbee.model.allPostsResponseModel;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Links{

	@SerializedName("next")
	private String next;

	public String getNext(){
		return next;
	}

	@Override
 	public String toString(){
		return 
			"Links{" + 
			"next = '" + next + '\'' + 
			"}";
		}
}