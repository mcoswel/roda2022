package com.somboi.rodaimpian.android;

import com.somboi.rodaimpian.gdx.entities.Gifts;

import java.util.ArrayList;
import java.util.List;

public class PlayerOnline implements Comparable<PlayerOnline> {
    public boolean logged;
    public String id;
    public String name;
    public String picUri;
    public int bestScore;
    public List<Integer> giftsList = new ArrayList<>();
    public List<Integer> bonusList = new ArrayList<>();
    public int rank;
    public int bankrupt;
    public int timesplayed;
    public String fcm_token;
    @Override
    public int compareTo(PlayerOnline o) {
        return (o.bestScore-this.bestScore);
    }
}
