package com.somboi.rodaimpian.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.base.BaseScreen;
import com.somboi.rodaimpian.gdx.entities.BgImg;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.modes.OnlinePlay;
import com.somboi.rodaimpian.gdx.modes.SinglePlayer;
import com.somboi.rodaimpian.gdx.online.Network;
import com.somboi.rodaimpian.gdx.online.PlayerState;
import com.somboi.rodaimpian.gdx.online.RodaClient;

import java.io.IOException;

public class MatchScreen extends BaseScreen {
    private SinglePlayer singlePlayer;
    private OnlinePlay onlinePlay;
    public MatchScreen(RodaImpian rodaImpian) {
        super(rodaImpian);
        stage.addActor(new BgImg(assetManager.get(AssetDesc.BG)));
        if (rodaImpian.getGameModes().equals(GameModes.SINGLE)) {
            singlePlayer = new SinglePlayer(rodaImpian, stage);
        }else if (rodaImpian.getGameModes().equals(GameModes.ONLINE)){
            onlinePlay = new OnlinePlay(rodaImpian,stage);
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


    public OnlinePlay getOnlinePlay() {
        return onlinePlay;
    }
}
