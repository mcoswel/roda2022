package com.somboi.rodaimpian.android;

import com.somboi.rodaimpian.gdx.entities.MainMenuCreator;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.online.NewOnline;

public interface AndroidInterface {
    void choosePhoto(int playerInt);

    void setPlayer(Player player);

    void setPlayerOnline(PlayerOnline playerOnline);

    void setMenuCreator(MainMenuCreator mainMenuCreator);

    void loginFB();

    void leaderBoard();

    void onlineChat();

    void chat(int guiIndex, NewOnline onlinePlay);

    void uploadScore(PlayerOnline playerOnline);

    void comments();

    void loadAllAds();

    void showAds(int i);
}
