package com.example.noushad.blogbee.model.registerResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("id")
@Expose
private int id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("email")
@Expose
private String email;
@SerializedName("isVerified")
@Expose
private int isVerified;
@SerializedName("isAdmin")
@Expose
private boolean isAdmin;
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

/**
* No args constructor for use in serialization
* 
*/
public Data() {
}

/**
* 
* @param id
* @param creationDate
* @param isVerified
* @param deletedDate
* @param email
* @param coverPhoto
* @param name
* @param isAdmin
* @param lastChange
*/
public Data(int id, String name, String email, int isVerified, boolean isAdmin, String coverPhoto, String creationDate, String lastChange, Object deletedDate) {
super();
this.id = id;
this.name = name;
this.email = email;
this.isVerified = isVerified;
this.isAdmin = isAdmin;
this.coverPhoto = coverPhoto;
this.creationDate = creationDate;
this.lastChange = lastChange;
this.deletedDate = deletedDate;
}

public int getId() {
return id;
}

public void setId(int id) {
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

public int getIsVerified() {
return isVerified;
}

public void setIsVerified(int isVerified) {
this.isVerified = isVerified;
}

public boolean isIsAdmin() {
return isAdmin;
}

public void setIsAdmin(boolean isAdmin) {
this.isAdmin = isAdmin;
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

}

