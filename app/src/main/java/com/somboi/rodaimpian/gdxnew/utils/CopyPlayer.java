package com.somboi.rodaimpian.gdxnew.utils;

import android.util.Log;

import com.somboi.rodaimpian.activities.PlayerOnline;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

import java.util.ArrayList;
import java.util.List;

public class CopyPlayer {
    public static PlayerOnline getPlayerOnline(PlayerNew player){
        PlayerOnline playerOnline = new PlayerOnline();
        playerOnline.name = player.getName();
        playerOnline.id = player.getUid();
        playerOnline.giftsList = new ArrayList<>();
        playerOnline.bonusList = new ArrayList<>();
        if (player.getPlayerGifts()!=null) {
            playerOnline.giftsList.addAll(player.getPlayerGifts());
        }
        if (player.getPlayerBonus()!=null) {
            playerOnline.bonusList.addAll(player.getPlayerBonus());
        }
        playerOnline.picUri = player.getPicUri();
        playerOnline.bankrupt += player.getBankrupt();
        playerOnline.logged = player.isLogged();
        playerOnline.fcm_token = player.getFcmToken();
        playerOnline.bestScore = player.getBestScore();
        Log.d("copy player", "bestscore" +playerOnline.bestScore);
        playerOnline.timesplayed = player.getTimesPlayed();
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
