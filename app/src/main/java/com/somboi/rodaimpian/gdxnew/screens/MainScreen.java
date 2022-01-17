package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdxnew.actors.LoginDiag;
import com.somboi.rodaimpian.gdxnew.actors.MenuFactory;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

public class MainScreen extends BaseScreenNew {
    private final MenuFactory menuFactory;
    public MainScreen(RodaImpianNew rodaImpianNew) {
        super(rodaImpianNew);
        this.menuFactory = new MenuFactory(assetManager, skin, stage, this);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        menuFactory.createBackground(rodaImpianNew.isGoldTheme());
        menuFactory.createPlayerProfile(rodaImpianNew.getPlayer(), rodaImpianNew.isGoldTheme());
        menuFactory.createMainMenu(rodaImpianNew.getPlayer().isLogged());
        if (!rodaImpianNew.getPlayer().isLogged()){
            LoginDiag loginDiag = new LoginDiag(skin, rodaImpianNew);
            loginDiag.show(stage);
        }
    }

    public void loginFacebook() {
        rodaImpianNew.loginFacebook();
    }

    public void reloadOnlineProfile() {
        saves.savePlayerNew(rodaImpianNew.getPlayer());
        stage.clear();
        show();
    }

    public void uploadPhoto(int playerNo) {
        rodaImpianNew.uploadPhoto(playerNo);
    }

    public void savePlayer(PlayerNew playerNew) {
        saves.savePlayerNew(playerNew);
    }

    public void singlePlay() {
        rodaImpianNew.setScreen(new GameScreen(rodaImpianNew, GameModes.SINGLE));
        rodaImpianNew.setGameModes(GameModes.SINGLE);
    }

    public void reloadPhoto() {
        menuFactory.reloadProfilePic(rodaImpianNew.getPlayer().getPicUri());
    }
}
