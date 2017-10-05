package com.example.noushad.blogbee.model.singlePostResponseModel;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CommentsItem{

	@SerializedName("creator_info")
	private BloggerInfo creatorInfo;

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

	public BloggerInfo getCreatorInfo(){
		return creatorInfo;
	}

	public int getPostId(){
		return postId;
	}

	public int getUserId(){
		return userId;
	}

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

	public String getCreationDate(){
		return creationDate;
	}

	@Override
 	public String toString(){
		return 
			"CommentsItem{" + 
			"creator_info = '" + creatorInfo + '\'' + 
			",post_id = '" + postId + '\'' + 
			",user_id = '" + userId + '\'' + 
			",deletedDate = '" + deletedDate + '\'' + 
			",lastChange = '" + lastChange + '\'' + 
			",details = '" + details + '\'' + 
			",id = '" + id + '\'' + 
			",creationDate = '" + creationDate + '\'' + 
			"}";
		}
}