package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdxnew.actors.CpuFactory;
import com.somboi.rodaimpian.gdxnew.actors.VannaHost;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

public class GameScreen extends BaseScreenNew {
    private final VannaHost vannaHost;
    private final Array<PlayerNew> players = new Array<>();
    public GameScreen(RodaImpianNew rodaImpianNew) {
        super(rodaImpianNew);
        vannaHost = new VannaHost(atlas);

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
        stage.addActor(vannaHost);
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.justTouched()) {
            //vannaHost.walk();
        }
    }
}
