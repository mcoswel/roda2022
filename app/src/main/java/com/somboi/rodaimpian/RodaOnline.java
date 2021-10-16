package com.somboi.rodaimpian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.somboi.rodaimpian.gdx.screen.WheelScreen;

import java.util.HashMap;
import java.util.Map;

public class RodaOnline extends Game {
    private final AssetManager assetManager = new AssetManager();
    private final Map<String, String> questSubject = new HashMap<>();
    private WheelScreen wheelScreen;
    public RodaOnline(RodaOnlineInterface rodaOnlineInterface) {
      //  RodaClient rodaClient = new RodaClient(rodaOnlineInterface);

    }



    @Override
    public void create() {
        //setScreen(new LoadingScreen(this));
        Gdx.app.setLogLevel(3);

    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void spinWheel() {
        this.setScreen(wheelScreen);
    }

    public WheelScreen getMatchScreen() {
        return wheelScreen;
    }

    public void setMatchScreen(WheelScreen wheelScreen) {
        this.wheelScreen = wheelScreen;
    }
}