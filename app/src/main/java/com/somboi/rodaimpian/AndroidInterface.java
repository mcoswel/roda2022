package com.somboi.rodaimpian;

import com.somboi.gdx.entities.MainMenuCreator;
import com.somboi.gdx.entities.Player;

public interface AndroidInterface {
    void choosePhoto(int playerInt);
    void setPlayer(Player player);
    void setMenuCreator(MainMenuCreator mainMenuCreator);
    void loginFB();
}
