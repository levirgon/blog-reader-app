package com.example.noushad.blogbee.model.singlePostResponseModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class SinglePostResponse {

    @SerializedName("cover_photo")
    private String coverPhoto;

    @SerializedName("creator_info")
    private BloggerInfo creatorInfo;

    @SerializedName("comments")
    private List<CommentsItem> comments;

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

    @SerializedName("title")
    private String title;

    @SerializedName("creationDate")
    private String creationDate;

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public BloggerInfo getCreatorInfo() {
        return creatorInfo;
    }

    public List<CommentsItem> getComments() {
        return comments;
    }

    public int getUserId() {
        return userId;
    }

    public Object getDeletedDate() {
        return deletedDate;
    }

    public String getLastChange() {
        return lastChange;
    }

    public String getDetails() {
        return details;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public int getTotalCommentsCount() {
        return getComments() != null ? getComments().size() : 0;
    }

    @Override
    public String toString() {
        return
                "SinglePostResponse{" +
                        "cover_photo = '" + coverPhoto + '\'' +
                        ",creator_info = '" + creatorInfo + '\'' +
                        ",comments = '" + comments + '\'' +
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