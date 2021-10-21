package com.somboi.rodaimpian.gdx.online.newentities;

import com.somboi.rodaimpian.gdx.assets.QuestionsReady;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.WheelParam;
import com.somboi.rodaimpian.gdx.online.entities.TilesOnline;

import java.util.List;

public class RodaSession {
    public String id;
    public QuestionsReady questionsReady;
    public WheelParam wheelParam;
    public List<Player> playerList;
    public List<TilesOnline>tilesOnlineList;
}
