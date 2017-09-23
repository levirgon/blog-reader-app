package com.example.noushad.blogbee.model.allPostsResponseModel;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Meta{

	@SerializedName("pagination")
	private Pagination pagination;

	public Pagination getPagination(){
		return pagination;
	}

	@Override
 	public String toString(){
		return 
			"Meta{" + 
			"pagination = '" + pagination + '\'' + 
			"}";
		}
}