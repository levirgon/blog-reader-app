package com.example.noushad.blogbee.model;

/**
 * Created by noushad on 7/18/17.
 */

public class Comments {

    String commentersName;
    String comment;

    public Comments(String commentersName, String comment) {
        this.commentersName = commentersName;
        this.comment = comment;
    }

    public String getCommentersName() {
        return commentersName;
    }

    public void setCommentersName(String commentersName) {
        this.commentersName = commentersName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
