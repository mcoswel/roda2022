package com.somboi.rodaimpian;

import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.entities.MenuCreator;
import com.somboi.gdx.entities.Player;

public interface AndroidInterface {
    void choosePhoto(int playerInt);
    void setPlayer(Player player);
    void setMenuCreator(MenuCreator menuCreator);
    void loginFB();
}
