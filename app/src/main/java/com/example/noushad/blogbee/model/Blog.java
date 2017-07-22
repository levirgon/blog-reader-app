package com.example.noushad.blogbee.model;

import android.graphics.Bitmap;


import java.util.List;

/**
 * Created by noushad on 7/16/17.
 */

public class Blog {

    private Bitmap mCoverImage;
    private Bitmap mProfileImage;
    private String mBloggerName;
    private String mCommentsCount;
    private String mLastUpdateTime;
    private String mBlogTitle;
    private String mDescription;
    private List<Comments> mComments;



    public Blog(Bitmap coverImage, Bitmap profileImage, String bloggerName, String blogTitle, String commentsCount, String lastUpdateTime, String description, List<Comments> comments) {
        mCoverImage = coverImage;
        mProfileImage = profileImage;
        mBloggerName = bloggerName;
        mBlogTitle = blogTitle;

        mCommentsCount = commentsCount;
        mLastUpdateTime = lastUpdateTime;
        mDescription = description;
        mComments = comments;
    }

    public String getBlogTitle() {
        return mBlogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        mBlogTitle = blogTitle;
    }

    public List<Comments> getComments() {
        return mComments;
    }

    public void setComments(List<Comments> comments) {
        mComments = comments;
    }

    public Bitmap getCoverImage() {
        return mCoverImage;
    }

    public void setCoverImage(Bitmap coverImage) {
        mCoverImage = coverImage;
    }

    public Bitmap getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        mProfileImage = profileImage;
    }

    public String getBloggerName() {
        return mBloggerName;
    }

    public void setBloggerName(String bloggerName) {
        mBloggerName = bloggerName;
    }

    public String getCommentsCount() {
        return mCommentsCount;
    }

    public void setCommentsCount(String commentsCount) {
        mCommentsCount = commentsCount;
    }

    public String getLastUpdateTime() {
        return mLastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        mLastUpdateTime = lastUpdateTime;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
