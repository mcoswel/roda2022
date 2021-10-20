package com.somboi.rodaimpian.gdx.utils;

import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.gdx.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class CopyPlayer {
    public static Player getPlayer(PlayerOnline playerOnline){
        Player player = new Player();
        player.id = playerOnline.id;
        player.name = playerOnline.name;
        player.bestScore = playerOnline.bestScore;
        player.picUri = playerOnline.picUri;
        return player;
    }

    public static PlayerOnline getPlayerOnline(PlayerOnline playerOnline, Player player){
        playerOnline.name = player.name;
        playerOnline.id = player.id;
        playerOnline.giftsList.addAll(player.gifts);
        playerOnline.bonusList.add(player.bonusIndex);
        playerOnline.picUri = player.picUri;
        playerOnline.bankrupt += player.bankrupt;
        playerOnline.logged = player.logged;
        int index = 0;
        List<Integer> initialRemoval = new ArrayList<>();
        for (Integer integer : playerOnline.bonusList){
            if (integer==0){
                initialRemoval.add(integer);
            }
        }
        playerOnline.bonusList.removeAll(initialRemoval);
        initialRemoval = new ArrayList<>();
        for (Integer integer : playerOnline.giftsList){
            if (integer==0){
                initialRemoval.add(integer);
            }
        }
        playerOnline.giftsList.removeAll(initialRemoval);


        if (playerOnline.giftsList.size()>3){
            List<Integer> toberemoved = new ArrayList<>();
            int max = playerOnline.giftsList.size()-3;
            for (int i=(playerOnline.giftsList.size()-1); i>=0; i--){
                if (i<max){
                    toberemoved.add(playerOnline.giftsList.get(i));
                }
            }
            playerOnline.giftsList.removeAll(toberemoved);
        }

        if (playerOnline.bonusList.size()>3){
            List<Integer> toberemoved = new ArrayList<>();
            int max = playerOnline.bonusList.size()-3;
            for (int i=(playerOnline.bonusList.size()-1); i>=0; i--){
                if (i<max){
                    toberemoved.add(playerOnline.bonusList.get(i));
                }
            }
            playerOnline.bonusList.removeAll(toberemoved);
        }
        return playerOnline;

    }
}
