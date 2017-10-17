package com.example.noushad.blogbee.model.singlePostResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentsItem{
	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("details")
	@Expose
	private String details;
	@SerializedName("user_id")
	@Expose
	private Integer userId;
	@SerializedName("post_id")
	@Expose
	private Integer postId;
	@SerializedName("creationDate")
	@Expose
	private String creationDate;
	@SerializedName("lastChange")
	@Expose
	private String lastChange;
	@SerializedName("deletedDate")
	@Expose
	private Object deletedDate;
	@SerializedName("creator_info")
	@Expose
	private CreatorInfo creatorInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastChange() {
		return lastChange;
	}

	public void setLastChange(String lastChange) {
		this.lastChange = lastChange;
	}

	public Object getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Object deletedDate) {
		this.deletedDate = deletedDate;
	}

	public CreatorInfo getCreatorInfo() {
		return creatorInfo;
	}

	public void setCreatorInfo(CreatorInfo creatorInfo) {
		this.creatorInfo = creatorInfo;
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
