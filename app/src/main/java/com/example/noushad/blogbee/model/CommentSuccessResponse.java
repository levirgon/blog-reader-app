package com.example.noushad.blogbee.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CommentSuccessResponse{

	@SerializedName("post_id")
	private int postId;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("deletedDate")
	private Object deletedDate;

	@SerializedName("lastChange")
	private String lastChange;

	@SerializedName("details")
	private String details;

	@SerializedName("id")
	private int id;

	@SerializedName("creationDate")
	private String creationDate;

	public void setPostId(int postId){
		this.postId = postId;
	}

	public int getPostId(){
		return postId;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setDeletedDate(Object deletedDate){
		this.deletedDate = deletedDate;
	}

	public Object getDeletedDate(){
		return deletedDate;
	}

	public void setLastChange(String lastChange){
		this.lastChange = lastChange;
	}

	public String getLastChange(){
		return lastChange;
	}

	public void setDetails(String details){
		this.details = details;
	}

	public String getDetails(){
		return details;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCreationDate(String creationDate){
		this.creationDate = creationDate;
	}

	public String getCreationDate(){
		return creationDate;
	}

	@Override
 	public String toString(){
		return 
			"CommentSuccessResponse{" + 
			"post_id = '" + postId + '\'' + 
			",user_id = '" + userId + '\'' + 
			",deletedDate = '" + deletedDate + '\'' + 
			",lastChange = '" + lastChange + '\'' + 
			",details = '" + details + '\'' + 
			",id = '" + id + '\'' + 
			",creationDate = '" + creationDate + '\'' + 
			"}";
		}
}