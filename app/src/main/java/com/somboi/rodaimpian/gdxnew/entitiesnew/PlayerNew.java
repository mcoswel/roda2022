package com.somboi.rodaimpian.gdxnew.entitiesnew;

import java.util.List;
import java.util.Map;

public class PlayerNew implements Comparable<PlayerNew> {
    private String name;
    private String uid;
    private String picUri;
    private String fcmToken;
    private int score;
    private int animateScore;
    private int fullScore;
    private int bestScore;
    private int timesPlayed;
    private int bankrupt;
    private int connectionID;
    private boolean turn;
    private boolean disconnect;
    private boolean ai;
    private boolean logged;
    private List<Integer> playerGifts;
    private List<Integer> playerBonus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPicUri() {
        return picUri;
    }

    public void setPicUri(String picUri) {
        this.picUri = picUri;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFullScore() {
        return fullScore;
    }

    public void setFullScore(int fullScore) {
        this.fullScore = fullScore;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public int getBankrupt() {
        return bankrupt;
    }

    public void setBankrupt(int bankrupt) {
        this.bankrupt = bankrupt;
    }


    public int getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(int connectionID) {
        this.connectionID = connectionID;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean isDisconnect() {
        return disconnect;
    }

    public void setDisconnect(boolean disconnect) {
        this.disconnect = disconnect;
    }

    public boolean isAi() {
        return ai;
    }

    public void setAi(boolean ai) {
        this.ai = ai;
    }

    public List<Integer> getPlayerGifts() {
        return playerGifts;
    }

    public void setPlayerGifts(List<Integer> playerGifts) {
        this.playerGifts = playerGifts;
    }

    public List<Integer> getPlayerBonus() {
        return playerBonus;
    }

    public void setPlayerBonus(List<Integer> playerBonus) {
        this.playerBonus = playerBonus;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public int getAnimateScore() {
        return animateScore;
    }

    public void setAnimateScore(int animateScore) {
        this.animateScore = animateScore;
    }

    @Override
    public int compareTo(PlayerNew playerNew) {
        return (playerNew.bestScore - this.bestScore);
    }
}
