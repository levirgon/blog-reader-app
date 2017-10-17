package com.example.noushad.blogbee.model.singlePostResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostDetails {
	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("title")
	@Expose
	private String title;
	@SerializedName("details")
	@Expose
	private String details;
	@SerializedName("large_cover")
	@Expose
	private String largeCover;
	@SerializedName("medium_cover")
	@Expose
	private String mediumCover;
	@SerializedName("small_cover")
	@Expose
	private String smallCover;
	@SerializedName("cover_photo")
	@Expose
	private String coverPhoto;
	@SerializedName("user_id")
	@Expose
	private Integer userId;
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
	@SerializedName("comments")
	@Expose
	private List<CommentsItem> comments = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getLargeCover() {
		return largeCover;
	}

	public void setLargeCover(String largeCover) {
		this.largeCover = largeCover;
	}

	public String getMediumCover() {
		return mediumCover;
	}

	public void setMediumCover(String mediumCover) {
		this.mediumCover = mediumCover;
	}

	public String getSmallCover() {
		return smallCover;
	}

	public void setSmallCover(String smallCover) {
		this.smallCover = smallCover;
	}

	public String getCoverPhoto() {
		return coverPhoto;
	}

	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public List<CommentsItem> getComments() {
		return comments;
	}

	public void setComments(List<CommentsItem> comments) {
		this.comments = comments;
	}
	@Override
 	public String toString(){
		return 
			"PostDetails{" +
			"cover_photo = '" + coverPhoto + '\'' + 
			",creator_info = '" + creatorInfo + '\'' + 
			",comments = '" + comments + '\'' + 
			",large_cover = '" + largeCover + '\'' + 
			",title = '" + title + '\'' + 
			",small_cover = '" + smallCover + '\'' + 
			",creationDate = '" + creationDate + '\'' + 
			",user_id = '" + userId + '\'' + 
			",deletedDate = '" + deletedDate + '\'' + 
			",medium_cover = '" + mediumCover + '\'' + 
			",lastChange = '" + lastChange + '\'' + 
			",details = '" + details + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}