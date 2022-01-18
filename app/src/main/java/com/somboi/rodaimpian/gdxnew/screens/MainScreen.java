package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdxnew.actors.LoginDiag;
import com.somboi.rodaimpian.gdxnew.actors.MenuFactory;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.games.MenuState;

public class MainScreen extends BaseScreenNew {
    private MenuFactory menuFactory;

    public MainScreen(RodaImpianNew rodaImpianNew) {
        super(rodaImpianNew);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        menuFactory = new MenuFactory(rodaImpianNew, stage);
        if (!rodaImpianNew.getPlayer().isLogged()) {
            LoginDiag loginDiag = new LoginDiag(skin, rodaImpianNew);
            loginDiag.show(stage);
        }
    }

    @Override
    public void backKey() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {

            if (menuFactory.getMenuState().equals(MenuState.MAIN)) {
                Gdx.app.exit();
            }

            if (menuFactory.getMenuState().equals(MenuState.MULTI)) {
                menuFactory.createMainMenu();
            }
            if (menuFactory.getMenuState().equals(MenuState.MULTISECOND)) {
                menuFactory.createMultiMenuFirst();
            }

            if (menuFactory.getMenuState().equals(MenuState.TWOPLAYER) || menuFactory.getMenuState().equals(MenuState.THREEPLAYER)) {
                menuFactory.createMultiSecond();
            }

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

    public void savePlayer(PlayerNew playerNew, int n) {
        if (n==1) {
            saves.savePlayerNew(playerNew);
        }else if (n==2){
            saves.savePlayerNewTwo(playerNew);
        }else {
            saves.savePlayerNewThree(playerNew);
        }
    }

    public void singlePlay() {
        rodaImpianNew.setScreen(new GameScreen(rodaImpianNew, GameModes.SINGLE));
        rodaImpianNew.setGameModes(GameModes.SINGLE);
    }

    public void reloadPhoto() {
        menuFactory.reloadProfilePic(rodaImpianNew.getPlayer().getPicUri());
    }
}
