package com.somboi.rodaimpian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.somboi.rodaimpian.android.AndroidInterface;
import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdx.assets.QuestionsReady;
import com.somboi.rodaimpian.gdx.entities.MainMenuCreator;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.modes.OnlinePlay;
import com.somboi.rodaimpian.gdx.online.RodaClient;
import com.somboi.rodaimpian.gdx.online.SessionList;
import com.somboi.rodaimpian.gdx.online.SessionRoom;
import com.somboi.rodaimpian.gdx.screen.LoadingScreen;
import com.somboi.rodaimpian.gdx.screen.MatchScreen;
import com.somboi.rodaimpian.gdx.screen.MenuScreen;
import com.somboi.rodaimpian.gdx.screen.RoomScreen;
import com.somboi.rodaimpian.gdx.screen.WheelScreen;

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
    private PlayerOnline playerOnline;
    private SessionRoom sessionRoom;
    private RoomScreen roomScreen;
    private SessionList sessionList = new SessionList();
    private RodaClient rodaClient;

    public RodaImpian(AndroidInterface androidInterface) {
        this.androidInterface = androidInterface;
    }

    @Override
    public void create() {
        //   PopulateQuestions populateQuestions = new PopulateQuestions();

        setScreen(new LoadingScreen(this));
        Gdx.app.setLogLevel(3);

    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void spinWheel() {
        this.setScreen(wheelScreen);
    }

    public void chat(int guiIndex, OnlinePlay onlinePlay) {
        androidInterface.chat(guiIndex, onlinePlay);
    }

    public void setWheelScreen(WheelScreen wheelScreen) {
        this.wheelScreen = wheelScreen;
    }

    public WheelScreen getWheelScreen() {
        return wheelScreen;
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

    public void setPlayerOnline(PlayerOnline playerOnline) {
        this.playerOnline = playerOnline;
        androidInterface.setPlayerOnline(playerOnline);
    }

    public void setMenuCreator(MainMenuCreator mainMenuCreator) {
        androidInterface.setMenuCreator(mainMenuCreator);
    }

    public void choosePhoto(int playerInt, MainMenuCreator mainMenuCreator) {
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

    public void loginFB() {
        androidInterface.loginFB();
    }


    public PlayerOnline getPlayerOnline() {
        return playerOnline;
    }

    public void startOnline() {
        androidInterface.onlineChat();
    }

    public SessionRoom getSessionRoom() {
        return sessionRoom;
    }

    public void setSessionRoom(SessionRoom sessionRoom) {
        this.sessionRoom = sessionRoom;
    }

    public SessionList getSessionList() {
        return sessionList;
    }

    public RoomScreen getRoomScreen() {
        return roomScreen;
    }

    public void setRoomScreen(RoomScreen roomScreen) {
        this.roomScreen = roomScreen;
    }

    public void updateSessionList(SessionList sessionList) {

        roomScreen.updateSessionList(sessionList);
        this.sessionList = sessionList;
    }

    public void gotoRoom() {
        setScreen(roomScreen);
    }


    public OnlinePlay getOnlinePlay() {
        if (matchScreen != null) {
            return matchScreen.getOnlinePlay();
        }
        return null;
    }
}
