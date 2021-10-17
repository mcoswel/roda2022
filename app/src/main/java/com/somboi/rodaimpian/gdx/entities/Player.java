package com.somboi.rodaimpian.gdx.entities;

import com.somboi.rodaimpian.gdx.online.PlayerState;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public String name;
    public String id;
    public String picUri;
    public int currentScore;
    public int boardScore;
    public int fullScore;
    public boolean logged;
    public int bestScore;
    public boolean freeTurn;
    public boolean isAi;
    public boolean turn;
    public int guiIndex;
    public int bonusIndex;
    public int bankrupt;
    public List<Integer>gifts = new ArrayList<>();
    public PlayerState playerState;
    public int conID;
}
