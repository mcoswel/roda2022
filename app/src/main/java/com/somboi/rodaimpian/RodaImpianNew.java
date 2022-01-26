package com.somboi.rodaimpian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.interfaces.OnInterface;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ApplyForce;
import com.somboi.rodaimpian.gdxnew.screens.GameScreen;
import com.somboi.rodaimpian.gdxnew.screens.LoadingScreenNew;
import com.somboi.rodaimpian.gdxnew.screens.MainScreen;
import com.somboi.rodaimpian.gdxnew.screens.OnlineScreen;
import com.somboi.rodaimpian.gdxnew.screens.SpinOnline;
import com.somboi.rodaimpian.gdxnew.wheels.WheelParams;
import com.somboi.rodaimpian.saves.PlayerSaves;

public class RodaImpianNew extends Game {
    private final AssetManager assetManager = new AssetManager();
    private WheelParams wheelParams = new WheelParams();
    private MainScreen mainScreen;
    private GameModes gameModes;
    private boolean goldTheme;
    private PlayerNew player;
    private PlayerNew playerTwo;
    private PlayerNew playerThree;
    private final AndroInterface androInterface;
    private GameScreen gameScreen;
    private boolean bonusMode;
    private final Array<QuestionNew> preparedQuestions = new Array<>();
    private PlayerSaves playerSaves;
    private Array<String> bannedRoom = new Array<>();
    private OnlineScreen onlineScreen;
    private SpinOnline spinOnline;
    private Music music;
    public RodaImpianNew(AndroInterface androInterface) {
        this.androInterface = androInterface;
    }

    @Override
    public void create() {
        if (MathUtils.random(0, 1) == 0) {
            goldTheme = true;
        }
        setScreen(new LoadingScreenNew(this));
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public void loginFacebook() {
        if (mainScreen == null) {
            return;
        }
        androInterface.loginFacebook();
    }

    public void loginGmail() {
        if (mainScreen == null) {
            return;
        }
        androInterface.loginGmail();
    }

    public void getFcmToken() {
        androInterface.getToken();
    }

    public void setFcmToken(String fcmToken) {
        if (player != null) {
            player.setFcmToken(fcmToken);
        }
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public WheelParams getWheelParams() {
        return wheelParams;
    }

    public PlayerNew getPlayer() {
        return player;
    }

    public void setPlayer(PlayerNew player) {
        this.player = player;
    }


    public GameModes getGameModes() {
        return gameModes;
    }

    public void setGameModes(GameModes gameModes) {
        this.gameModes = gameModes;
    }

    public void setMainScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    public PlayerNew getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(PlayerNew playerTwo) {
        this.playerTwo = playerTwo;
    }

    public PlayerNew getPlayerThree() {
        return playerThree;
    }

    public void setPlayerThree(PlayerNew playerThree) {
        this.playerThree = playerThree;
    }

    public void mainMenu() {
        if (mainScreen != null) {
            setScreen(mainScreen);
        } else {
            setScreen(new MainScreen(this));
        }
    }

    public boolean isGoldTheme() {
        return goldTheme;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void finishSpin() {
        if (gameScreen != null) {
            setScreen(gameScreen);
            gameScreen.spinResult();
        }
    }


    public boolean isBonusMode() {
        return bonusMode;
    }

    public void setBonusMode(boolean bonusMode) {
        this.bonusMode = bonusMode;
    }


    public Array<QuestionNew> getPreparedQuestions() {
        return preparedQuestions;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public void setPlayerSaves(PlayerSaves playerSaves) {
        this.playerSaves = playerSaves;
    }

    public void savePlayer() {
        if (playerSaves != null) {
            playerSaves.savePlayerNew(player);
        }
    }

    public void reloadMenu() {
        if (mainScreen != null) {
            mainScreen.reloadPhoto();
        }
    }

    public Array<String> getBannedRoom() {
        return bannedRoom;
    }

    public void restart() {
        androInterface.restart();
    }

    public void uploadPhoto(int playerNo) {
        androInterface.choosePhoto(playerNo);
    }

    public void chatOnline(OnInterface onInterface, int guiIndex) {
        androInterface.chatOnline(onInterface, guiIndex);
    }

    public void setWheelParams(WheelParams wheelParams) {
        this.wheelParams = wheelParams;
    }

    public void setOnlineScreen(OnlineScreen onlineScreen) {
        this.onlineScreen = onlineScreen;
    }


    public void spinOnline(boolean bonusMode, OnInterface onInterface) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                spinOnline = new SpinOnline(RodaImpianNew.this, bonusMode, onInterface);
                if (spinOnline != null) {
                    if (!getScreen().equals(spinOnline)) {
                        setScreen(spinOnline);
                    }
                }
            }
        });

    }

    public void forceWheel(ApplyForce applyForce) {
        if (spinOnline != null) {
            spinOnline.spinWheel(applyForce);
        }
    }

    public SpinOnline getSpinOnline() {
        return spinOnline;
    }

    public void backOnlineScreen() {
        if (onlineScreen != null) {
            setScreen(onlineScreen);
        }
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
