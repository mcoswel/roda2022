package com.somboi.rodaimpian.gdx.online;

import com.somboi.rodaimpian.gdx.assets.QuestionsReady;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.WheelParam;

import java.util.List;

public class SessionRoom {
    private List<Player> playerList;
    private String roomID;
    private QuestionsReady questionsReady;
    private String roomName;
    private boolean isPlaying;
    public List<Player> getPlayerList() {
        return playerList;
    }

    public Player getHostPlayer(){
        for (Player p: playerList){
            if (p.id.equals(roomID)){
                return p;
            }
        }
        return null;
    }

    public Player getPlayerOne(){
        for (Player p: playerList){
            if (p.guiIndex == 1){
                return p;
            }
        }
        return null;
    }
    public Player getPlayerTwo(){
        for (Player p: playerList){
            if (p.guiIndex == 2){
                return p;
            }
        }
        return null;
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
