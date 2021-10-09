package com.somboi.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.actor.ConsonantKeyboard;
import com.somboi.gdx.actor.VocalKeyboard;
import com.somboi.gdx.assets.AssetDesc;
import com.somboi.gdx.base.BaseScreen;
import com.somboi.gdx.entities.BgImg;
import com.somboi.gdx.entities.MatchRound;
import com.somboi.gdx.entities.MenuButtons;
import com.somboi.gdx.modes.SinglePlayer;

public class MatchScreen extends BaseScreen {
    private final Group menuGroup = new Group();
    private SinglePlayer singlePlayer;

    public MatchScreen(RodaImpian rodaImpian) {
        super(rodaImpian);
        stage.addActor(new BgImg(assetManager.get(AssetDesc.BG)));
        stage.addActor(menuGroup);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        if (singlePlayer == null) {
            singlePlayer = new SinglePlayer(rodaImpian, stage);
        }
    }

  
}
