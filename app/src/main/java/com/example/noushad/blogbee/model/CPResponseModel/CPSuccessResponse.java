package com.example.noushad.blogbee.model.CPResponseModel;

public class CPSuccessResponse{
	private String coverPhoto;
	private int userId;
	private Object deletedDate;
	private String lastChange;
	private String details;
	private int id;
	private String title;
	private String creationDate;

	public void setCoverPhoto(String coverPhoto){
		this.coverPhoto = coverPhoto;
	}

	public String getCoverPhoto(){
		return coverPhoto;
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

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
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
			"CPSuccessResponse{" + 
			"cover_photo = '" + coverPhoto + '\'' + 
			",user_id = '" + userId + '\'' + 
			",deletedDate = '" + deletedDate + '\'' + 
			",lastChange = '" + lastChange + '\'' + 
			",details = '" + details + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",creationDate = '" + creationDate + '\'' + 
			"}";
		}
}
