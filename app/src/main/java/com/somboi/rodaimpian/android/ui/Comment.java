package com.somboi.rodaimpian.android.ui;


public class Comment {
    String picUri;
    String comment;
    String name;
    String commentID;
    String playerWhoSendID;

    public String getPlayerWhoSendID() {
        return playerWhoSendID;
    }

    public void setPlayerWhoSendID(String playerWhoSendID) {
        this.playerWhoSendID = playerWhoSendID;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getPicUri() {
        return picUri;
    }

    public void setPicUri(String picUri) {
        this.picUri = picUri;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}