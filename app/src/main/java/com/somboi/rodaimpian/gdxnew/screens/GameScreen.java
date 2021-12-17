package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdxnew.actors.VannaHost;

public class GameScreen extends BaseScreenNew{
    private final VannaHost vannaHost;
    public GameScreen(RodaImpianNew rodaImpianNew) {
        super(rodaImpianNew);
        vannaHost = new VannaHost(atlas);
    }

    @Override
    public void show() {
        actorFactory.createGameBg();
        actorFactory.createGameTables();
        stage.addActor(vannaHost);
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.justTouched()){
            vannaHost.walk();
        }
    }
}
