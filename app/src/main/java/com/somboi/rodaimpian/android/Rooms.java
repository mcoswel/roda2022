package com.somboi.rodaimpian.android;

import com.somboi.rodaimpian.activities.PlayerOnline;

public class Rooms {
    private PlayerOnline hostPlayer;
    private PlayerOnline player_one;
    private PlayerOnline player_two;
    private String status;
    private String id;
    private boolean start;

    public Rooms() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PlayerOnline getHostPlayer() {
        return hostPlayer;
    }

    public void setHostPlayer(PlayerOnline hostPlayer) {
        this.id = hostPlayer.id;
        this.hostPlayer = hostPlayer;
    }

    public PlayerOnline getPlayer_one() {
        return player_one;
    }

    public void setPlayer_one(PlayerOnline player_one) {
        this.player_one = player_one;
    }

    public PlayerOnline getPlayer_two() {
        return player_two;
    }

    public void setPlayer_two(PlayerOnline player_two) {
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
