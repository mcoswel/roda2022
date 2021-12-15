package com.somboi.rodaimpian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.somboi.rodaimpian.gdxnew.screens.GameScreen;
import com.somboi.rodaimpian.gdxnew.screens.LoadingScreenNew;
import com.somboi.rodaimpian.gdxnew.wheels.WheelParams;

public class RodaImpianNew extends Game {
    private final AssetManager assetManager = new AssetManager();
    private final WheelParams wheelParams = new WheelParams();
    @Override
    public void create() {
        setScreen(new LoadingScreenNew(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public WheelParams getWheelParams() {
        return wheelParams;
    }
}
