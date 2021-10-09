package com.somboi.ui;

import com.somboi.gdx.entities.Player;

public class Rooms {
    private Player hostPlayer;
    private Player player_one;
    private Player player_two;
    private String status;
    private String id;
    private boolean start;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Player getHostPlayer() {
        return hostPlayer;
    }

    public void setHostPlayer(Player hostPlayer) {
        this.id = hostPlayer.id;
        this.hostPlayer = hostPlayer;
    }

    public Player getPlayer_one() {
        return player_one;
    }

    public void setPlayer_one(Player player_one) {
        this.player_one = player_one;
    }

    public Player getPlayer_two() {
        return player_two;
    }

    public void setPlayer_two(Player player_two) {
        this.player_two = player_two;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
