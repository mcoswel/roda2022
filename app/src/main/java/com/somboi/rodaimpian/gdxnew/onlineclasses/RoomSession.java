package com.somboi.rodaimpian.gdxnew.onlineclasses;

import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

import java.util.List;

public class RoomSession {
    private List<PlayerNew> playerList;
    private String roomUri;
    private String roomID;
    private List<QuestionNew> questionNews;
    private boolean occupied;
    public List<PlayerNew> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<PlayerNew> playerList) {
        this.playerList = playerList;
    }

    public String getRoomUri() {
        return roomUri;
    }

    public void setRoomUri(String roomUri) {
        this.roomUri = roomUri;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public List<QuestionNew> getQuestionNews() {
        return questionNews;
    }

    public void setQuestionNews(List<QuestionNew> questionNews) {
        this.questionNews = questionNews;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
