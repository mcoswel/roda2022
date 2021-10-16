package com.somboi.rodaimpian.gdx.utils;

import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.gdx.entities.Player;

public class CopyPlayer {
    public static Player getPlayer(PlayerOnline playerOnline){
        Player player = new Player();
        player.id = playerOnline.id;
        player.name = playerOnline.name;
        player.bestScore = playerOnline.bestScore;
        player.picUri = playerOnline.picUri;
        return player;
    }
}
