package com.somboi.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.assets.AssetDesc;
import com.somboi.gdx.base.BaseScreen;
import com.somboi.gdx.entities.MainMenuCreator;

public class MenuScreen extends BaseScreen {
    private final MainMenuCreator mainMenuCreator;
    public MenuScreen(RodaImpian rodaImpian) {
        super(rodaImpian);
        mainMenuCreator = new MainMenuCreator(rodaImpian, stage);
        rodaImpian.setMenuCreator(mainMenuCreator);
        Image bg = new Image(assetManager.get(AssetDesc.BLURBG));
        bg.setSize(900f,1600f);
        bg.setPosition(0,0);
        Image titleBg = new Image(textureAtlas.findRegion("title"));
        titleBg.setSize(900f,365f);
        titleBg.setPosition(0, 1200f );
        stage.addActor(bg);
        stage.addActor(titleBg);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        mainMenuCreator.show();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        mainMenuCreator.loadLocalPic();
    }
}
