package com.somboi.ui;

import com.somboi.gdx.entities.Player;

public class Chats {
    private  String content;
    private  Player player;
    private  String pushKey;
    private String replierName;
    private String replyToId;

    public Player getPlayer() {
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }
}
