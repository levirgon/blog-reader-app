package com.example.noushad.blogbee.model.allPostsResponseModel;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("comment_count")
	private String commentCount;

	@SerializedName("cover_photo")
	private String coverPhoto;

	@SerializedName("creator_info")
	private CreatorInfo creatorInfo;

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

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	@SerializedName("title")
	private String title;

	@SerializedName("creationDate")
	private String creationDate;

	public String getCommentCount(){
		return commentCount;
	}

	public String getCoverPhoto(){
		return coverPhoto;
	}

	public CreatorInfo getCreatorInfo(){
		return creatorInfo;
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

	public List<CategoriesItem> getCategories(){
		return categories;
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
			"DataItem{" + 
			"comment_count = '" + commentCount + '\'' + 
			",cover_photo = '" + coverPhoto + '\'' + 
			",creator_info = '" + creatorInfo + '\'' + 
			",user_id = '" + userId + '\'' + 
			",deletedDate = '" + deletedDate + '\'' + 
			",lastChange = '" + lastChange + '\'' + 
			",details = '" + details + '\'' + 
			",id = '" + id + '\'' + 
			",categories = '" + categories + '\'' + 
			",title = '" + title + '\'' + 
			",creationDate = '" + creationDate + '\'' + 
			"}";
		}
}