package com.example.noushad.blogbee.model.allPostsResponseModel;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class AllpostsResponse {

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("meta")
	private Meta meta;

	public List<DataItem> getData(){
		return data;
	}

	public Meta getMeta(){
		return meta;
	}

	@Override
 	public String toString(){
		return 
			"AllpostsResponse{" +
			"data = '" + data + '\'' + 
			",meta = '" + meta + '\'' + 
			"}";
		}
}