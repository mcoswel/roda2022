package com.somboi.gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.somboi.gdx.actor.PlayerImage;
import com.somboi.gdx.assets.QuestionsReady;
import com.somboi.gdx.base.ModeBase;
import com.somboi.gdx.entities.MenuCreator;
import com.somboi.gdx.entities.Player;
import com.somboi.gdx.modes.GameModes;
import com.somboi.gdx.screen.LoadingScreen;
import com.somboi.gdx.screen.MatchScreen;
import com.somboi.gdx.screen.MenuScreen;
import com.somboi.gdx.screen.WheelScreen;
import com.somboi.rodaimpian.AndroidInterface;

import java.util.HashMap;
import java.util.Map;

public class RodaImpian extends Game {
    private final AssetManager assetManager = new AssetManager();
    private WheelScreen wheelScreen;
    private MenuScreen menuScreen;
    private MatchScreen matchScreen;
    private QuestionsReady questionsReady;
    private final AndroidInterface androidInterface;
    private Player player;
    private PlayerImage playerImage;
    private GameModes gameModes;
    public RodaImpian(AndroidInterface androidInterface) {
        this.androidInterface = androidInterface;
    }

    @Override
    public void create() {
        setScreen(new LoadingScreen(this));
        Gdx.app.setLogLevel(3);

    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void spinWheel() {
        this.setScreen(wheelScreen);
    }

    public void setWheelScreen(WheelScreen wheelScreen) {
        this.wheelScreen = wheelScreen;
    }

    public void setMenuScreen(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
    }

    public void gotoMenu() {
        this.setScreen(menuScreen);
    }

    public QuestionsReady getQuestionsReady() {
        return questionsReady;
    }

    public void setMatchScreen(MatchScreen matchScreen) {
        this.matchScreen = matchScreen;
    }

    public void gotoMatch() {
        if (matchScreen != null) {
            this.setScreen(matchScreen);
        }
    }


    public void setQuestionsReady(QuestionsReady questionsReady) {
        this.questionsReady = questionsReady;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        androidInterface.setPlayer(player);
    }

    public void setMenuCreator(MenuCreator menuCreator) {
        androidInterface.setMenuCreator(menuCreator);
    }

    public void choosePhoto(int playerInt, MenuCreator menuCreator) {
        androidInterface.choosePhoto(playerInt);
    }

    public GameModes getGameModes() {
        return gameModes;
    }

    public void setGameModes(GameModes gameModes) {
        this.gameModes = gameModes;
    }

    public PlayerImage getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(PlayerImage playerImage) {
        this.playerImage = playerImage;
    }



}
