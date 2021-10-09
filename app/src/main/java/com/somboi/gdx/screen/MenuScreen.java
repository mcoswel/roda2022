package com.somboi.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.actor.LargeButton;
import com.somboi.gdx.assets.AssetDesc;
import com.somboi.gdx.assets.StringRes;
import com.somboi.gdx.base.BaseScreen;
import com.somboi.gdx.entities.MenuCreator;

public class MenuScreen extends BaseScreen {
    private final MenuCreator menuCreator;
    public MenuScreen(RodaImpian rodaImpian) {
        super(rodaImpian);
        menuCreator = new MenuCreator(rodaImpian, stage);
        rodaImpian.setMenuCreator(menuCreator);
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
        menuCreator.show();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        menuCreator.loadLocalPic();
    }
}
