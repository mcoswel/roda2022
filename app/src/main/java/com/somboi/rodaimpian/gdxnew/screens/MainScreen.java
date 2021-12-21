package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdxnew.actors.DialogChangeName;
import com.somboi.rodaimpian.gdxnew.actors.MenuFactory;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

public class MainScreen extends BaseScreenNew{
    private final MenuFactory menuFactory;
    public MainScreen(RodaImpianNew rodaImpianNew) {
        super(rodaImpianNew);
        this.menuFactory = new MenuFactory(assetManager, skin, stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        menuFactory.createBackground(rodaImpianNew.isGoldTheme());
        menuFactory.createPlayerProfile(rodaImpianNew.getPlayer(), rodaImpianNew.isGoldTheme(), this);
        menuFactory.createMenuButtons(this, rodaImpianNew.getPlayer().isLogged());
    }

    public void loginFacebook(){
        rodaImpianNew.loginFacebook();
    }

    public void loadFacebook(){
        saves.savePlayerNew(rodaImpianNew.getPlayer());
        stage.clear();
        show();
    }

    public void savePlayer(PlayerNew playerNew){
        saves.savePlayerNew(playerNew);
    }
    public void startGame(){
        rodaImpianNew.setScreen(new GameScreen(rodaImpianNew));
    }

}
