package com.somboi.rodaimpian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.screens.LoadingScreenNew;
import com.somboi.rodaimpian.gdxnew.screens.MainScreen;
import com.somboi.rodaimpian.gdxnew.wheels.WheelParams;

public class RodaImpianNew extends Game {
    private final AssetManager assetManager = new AssetManager();
    private final WheelParams wheelParams = new WheelParams();
    private int humanPlayerCount = 1;
    private MainScreen mainScreen;
    private GameModes gameModes;
    private boolean goldTheme;
    private PlayerNew player;
    private String fcmToken;
    private final AndroInterface androInterface;

    public RodaImpianNew(AndroInterface androInterface) {
        this.androInterface = androInterface;
    }

    @Override
    public void create() {
        if (MathUtils.random(0,1)==0){
            goldTheme = true;
        }
        setScreen(new LoadingScreenNew(this));
    }


    public void loginFacebook(){
        if (mainScreen == null){
            return;
        }
        androInterface.loginFacebook(mainScreen);
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

    public int getHumanPlayerCount() {
        return humanPlayerCount;
    }

    public void setHumanPlayerCount(int humanPlayerCount) {
        this.humanPlayerCount = humanPlayerCount;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
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
    public void mainMenu(){
        if (mainScreen!=null) {
            setScreen(mainScreen);
        }else{
            setScreen(new MainScreen(this));
        }
    }

    public boolean isGoldTheme() {
        return goldTheme;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
