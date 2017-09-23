package com.example.noushad.blogbee.model.allPostsResponseModel;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CreatorInfo{

	@SerializedName("cover_photo")
	private String coverPhoto;

	@SerializedName("isVerified")
	private int isVerified;

	@SerializedName("deletedDate")
	private Object deletedDate;

	@SerializedName("name")
	private String name;

	@SerializedName("lastChange")
	private String lastChange;

	@SerializedName("id")
	private int id;

	@SerializedName("isAdmin")
	private boolean isAdmin;

	@SerializedName("creationDate")
	private String creationDate;

	@SerializedName("email")
	private String email;

	public String getCoverPhoto(){
		return coverPhoto;
	}

	public int getIsVerified(){
		return isVerified;
	}

	public Object getDeletedDate(){
		return deletedDate;
	}

	public String getName(){
		return name;
	}

	public String getLastChange(){
		return lastChange;
	}

	public int getId(){
		return id;
	}

	public boolean isIsAdmin(){
		return isAdmin;
	}

	public String getCreationDate(){
		return creationDate;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"CreatorInfo{" + 
			"cover_photo = '" + coverPhoto + '\'' + 
			",isVerified = '" + isVerified + '\'' + 
			",deletedDate = '" + deletedDate + '\'' + 
			",name = '" + name + '\'' + 
			",lastChange = '" + lastChange + '\'' + 
			",id = '" + id + '\'' + 
			",isAdmin = '" + isAdmin + '\'' + 
			",creationDate = '" + creationDate + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}