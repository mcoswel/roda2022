package com.somboi.rodaimpian.gdxnew.onlineclasses;

import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

import java.util.List;

public class RoomSession {
    private List<PlayerNew> playerList;
    private String roomUri;
    private int roomNo;
    private List<QuestionNew> questionNews;

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

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public List<QuestionNew> getQuestionNews() {
        return questionNews;
    }

    public void setQuestionNews(List<QuestionNew> questionNews) {
        this.questionNews = questionNews;
    }
}
