package com.somboi.rodaimpian.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.base.BaseScreen;
import com.somboi.rodaimpian.gdx.entities.BgImg;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.modes.OnlinePlay;
import com.somboi.rodaimpian.gdx.modes.SinglePlayer;
import com.somboi.rodaimpian.gdx.online.Network;
import com.somboi.rodaimpian.gdx.online.RodaClient;

import java.io.IOException;

public class MatchScreen extends BaseScreen {
    private SinglePlayer singlePlayer;
    private RodaClient rodaClient;

    public MatchScreen(RodaImpian rodaImpian) {
        super(rodaImpian);
        stage.addActor(new BgImg(assetManager.get(AssetDesc.BG)));
        if (rodaImpian.getGameModes().equals(GameModes.SINGLE)) {
            singlePlayer = new SinglePlayer(rodaImpian, stage);

        } else if (rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
            OnlinePlay onlinePlay = new OnlinePlay(rodaImpian, stage);
            rodaClient = new RodaClient(rodaImpian, gameSound, onlinePlay);
            onlinePlay.setRodaClient(rodaClient);
            rodaClient.start();

            new Thread("Connect") {
                @Override
                public void run() {
                    try {
                        rodaClient.connect(5001, "192.168.8.100", Network.port);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logger.debug("run thread");
                }
            }.start();
        }


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (singlePlayer != null) {
            singlePlayer.update(delta);
        }
    }
}
