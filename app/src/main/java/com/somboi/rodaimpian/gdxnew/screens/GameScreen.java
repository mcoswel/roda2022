package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdxnew.actors.CpuFactory;
import com.somboi.rodaimpian.gdxnew.actors.VannaHost;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

public class GameScreen extends BaseScreenNew {
    public GameScreen(RodaImpianNew rodaImpianNew) {
        super(rodaImpianNew);

        if (rodaImpianNew.getGameModes() != null) {
            if (!rodaImpianNew.getGameModes().equals(GameModes.ONLINE)) {
                if (rodaImpianNew.getHumanPlayerCount() < 3) {
                    CpuFactory cpuFactory = new CpuFactory(atlas);
                    cpuFactory.createCpu(rodaImpianNew.getHumanPlayerCount());
                }
            }
        }

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        actorFactory.createGameBg(rodaImpianNew.isGoldTheme());
        actorFactory.createGameTables();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.justTouched()) {
            //vannaHost.walk();
        }
    }
}
