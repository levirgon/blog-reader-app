package com.example.noushad.blogbee.model.allPostsResponseModel;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CategoriesItem{

	@SerializedName("deletedDate")
	private Object deletedDate;

	@SerializedName("lastChange")
	private String lastChange;

	@SerializedName("details")
	private String details;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("creationDate")
	private String creationDate;

	public Object getDeletedDate(){
		return deletedDate;
	}

	public String getLastChange(){
		return lastChange;
	}

	public String getDetails(){
		return details;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getCreationDate(){
		return creationDate;
	}

	@Override
 	public String toString(){
		return 
			"CategoriesItem{" + 
			"deletedDate = '" + deletedDate + '\'' + 
			",lastChange = '" + lastChange + '\'' + 
			",details = '" + details + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",creationDate = '" + creationDate + '\'' + 
			"}";
		}
}