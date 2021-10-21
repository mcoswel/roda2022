package com.somboi.rodaimpian.gdx.online.newentities;

import com.somboi.rodaimpian.gdx.assets.QuestionsReady;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.WheelParam;

import java.util.List;

public class SessionRoom {
    private String roomID;
    private List<Player> playerList;
    private QuestionsReady questionsReady;
    private String roomName;
    private boolean isPlaying;

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public QuestionsReady getQuestionsReady() {
        return questionsReady;
    }

    public void setQuestionsReady(QuestionsReady questionsReady) {
        this.questionsReady = questionsReady;
    }


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
