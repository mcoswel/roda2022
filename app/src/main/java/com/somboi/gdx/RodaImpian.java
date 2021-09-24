package com.somboi.gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.somboi.gdx.screen.LoadingScreen;
import com.somboi.gdx.screen.WheelScreen;
import com.somboi.rodaimpian.AndroidLauncher;

import java.util.HashMap;
import java.util.Map;

public class RodaImpian extends Game {
    private final AssetManager assetManager = new AssetManager();
    private final Map<String, String> questSubject = new HashMap<>();
    private WheelScreen wheelScreen;
    public RodaImpian(AndroidLauncher androidLauncher) {

    }

    @Override
    public void create() {
        setScreen(new LoadingScreen(this));
        Gdx.app.setLogLevel(3);

    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Map<String, String> getQuestSubject() {
        return questSubject;
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
