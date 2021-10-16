package com.somboi.rodaimpian.android;

import com.somboi.rodaimpian.gdx.modes.OnlinePlay;
import com.somboi.rodaimpian.gdx.online.RodaClient;
import com.somboi.rodaimpian.gdx.entities.MainMenuCreator;
import com.somboi.rodaimpian.gdx.entities.Player;

public interface AndroidInterface {
    void choosePhoto(int playerInt);
    void setPlayer(Player player);
    void setPlayerOnline(PlayerOnline playerOnline);
    void setMenuCreator(MainMenuCreator mainMenuCreator);
    void loginFB();
    void leaderBoard();
    void onlineChat(PlayerOnline playerOnline);
    void chat(int guiIndex, OnlinePlay onlinePlay);
}
