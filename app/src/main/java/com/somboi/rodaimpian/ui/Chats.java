package com.somboi.rodaimpian.ui;

import com.somboi.rodaimpian.activities.PlayerOnline;

public class Chats {
    private String content;
    private PlayerOnline player;
    private String pushKey;
    private String replierName;
    private String replyToId;
    private String senderId;

    public PlayerOnline getPlayer() {
        return player;
    }

    public String getContent() {
        return content;
    }

    public String getPushKey() {
        return pushKey;
    }

    public String getReplierName() {
        return replierName;
    }

    public void setReplierName(String replierName) {
        this.replierName = replierName;
    }

    public String getReplyToId() {
        return replyToId;
    }

    public void setReplyToId(String replyToId) {
        this.replyToId = replyToId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPlayer(PlayerOnline player) {
        this.player = player;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
