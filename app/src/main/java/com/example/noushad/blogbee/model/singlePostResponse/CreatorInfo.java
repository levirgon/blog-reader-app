package com.example.noushad.blogbee.model.singlePostResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatorInfo{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("isVerified")
    @Expose
    private Integer isVerified;
    @SerializedName("isAdmin")
    @Expose
    private Boolean isAdmin;
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
    @SerializedName("creationDate")
    @Expose
    private String creationDate;
    @SerializedName("lastChange")
    @Expose
    private String lastChange;
    @SerializedName("deletedDate")
    @Expose
    private Object deletedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
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

	@Override
 	public String toString(){
		return 
			"UserDetails{" + 
			"cover_photo = '" + coverPhoto + '\'' + 
			",isVerified = '" + isVerified + '\'' + 
			",deletedDate = '" + deletedDate + '\'' + 
			",name = '" + name + '\'' + 
			",large_cover = '" + largeCover + '\'' + 
			",medium_cover = '" + mediumCover + '\'' + 
			",lastChange = '" + lastChange + '\'' + 
			",id = '" + id + '\'' + 
			",isAdmin = '" + isAdmin + '\'' + 
			",small_cover = '" + smallCover + '\'' + 
			",creationDate = '" + creationDate + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}
