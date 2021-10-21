package com.somboi.rodaimpian.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.MenuPrompt;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.base.BaseScreen;
import com.somboi.rodaimpian.gdx.entities.BgImg;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.modes.MultiPlayLocal;
import com.somboi.rodaimpian.gdx.modes.OnlinePlay;
import com.somboi.rodaimpian.gdx.modes.SinglePlayer;

public class MatchScreen extends BaseScreen {
    private SinglePlayer singlePlayer;
    private MultiPlayLocal multiPlayLocal;

    public MatchScreen(RodaImpian rodaImpian) {
        super(rodaImpian);
        stage.addActor(new BgImg(assetManager.get(AssetDesc.BG)));
        if (rodaImpian.getGameModes().equals(GameModes.SINGLE)) {
            singlePlayer = new SinglePlayer(rodaImpian, stage);
        } else if (rodaImpian.getGameModes().equals(GameModes.ONLINE)) {

        } else if (rodaImpian.getGameModes().equals(GameModes.LOCALMULTI)) {
            multiPlayLocal = new MultiPlayLocal(rodaImpian, stage);
        }
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        rodaImpian.setMatchScreen(this);
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
        if (multiPlayLocal != null) {
            multiPlayLocal.update(delta);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            MenuPrompt menuPrompt = new MenuPrompt(rodaImpian, skin);
            menuPrompt.show(stage);
        }
    }
}
