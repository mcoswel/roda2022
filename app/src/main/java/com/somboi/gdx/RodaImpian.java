package com.somboi.gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.somboi.gdx.screen.LoadingScreen;
import com.somboi.gdx.screen.MatchScreen;
import com.somboi.rodaimpian.AndroidLauncher;

import java.util.HashMap;
import java.util.Map;

public class RodaImpian extends Game {
    private final AssetManager assetManager = new AssetManager();
    private final Map<String, String> questSubject = new HashMap<>();
    private  MatchScreen matchScreen;
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
        this.setScreen(matchScreen);
    }

    public MatchScreen getMatchScreen() {
        return matchScreen;
    }

    public void setMatchScreen(MatchScreen matchScreen) {
        this.matchScreen = matchScreen;
    }
}
