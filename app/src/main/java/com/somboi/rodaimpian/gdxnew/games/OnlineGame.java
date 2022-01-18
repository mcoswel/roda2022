package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.config.GameConfig;

public class OnlineGame extends BaseGame{
    public OnlineGame(Stage stage, RodaImpianNew rodaImpianNew) {
        super(stage, rodaImpianNew);
        vannaHost.remove();

    }

    public void createGameBg() {
        if (rodaImpianNew.isGoldTheme()) {
            stage.addActor(new Image(rodaImpianNew.getAssetManager().get(AssetDesc.GAMEBGGOLD)));
        } else {
            stage.addActor(new Image(rodaImpianNew.getAssetManager().get(AssetDesc.GAMEBGRED)));
        }
    }

    public void createGameTables() {
        GameConfig.TABLECOLOR.shuffle();
        for (int i = 0; i < 3; i++) {
            Image image = new Image(atlas.findRegion("tabledown"));
            image.setColor(GameConfig.TABLECOLOR.get(i));
            Image top = new Image(atlas.findRegion("tabletop"));
            image.setPosition(i * 300, 0);
            top.setPosition(i * 300, 0);
            stage.addActor(image);
            stage.addActor(top);
        }
    }

    @Override
    public void addPlayers() {
        super.addPlayers();
    }
}
