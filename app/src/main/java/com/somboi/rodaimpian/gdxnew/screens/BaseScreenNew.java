package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.config.GameConfig;

public class BaseScreenNew extends ScreenAdapter {
    protected final FitViewport worldViewport = new FitViewport(9f, 16f);
    protected final Stage worldStage = new Stage(worldViewport);
    protected final FitViewport hudViewport = new FitViewport(900f, 1600f);
    protected final Stage stage = new Stage(hudViewport);
    protected final RodaImpianNew rodaImpianNew;
    protected final World world = new World(new Vector2(0, -9), false);
    protected final AssetManager assetManager;
    protected final Skin skin;
    protected final TextureAtlas atlas;
    protected final Logger logger = new Logger(this.getClass().getName(), 3);

    public BaseScreenNew(RodaImpianNew rodaImpianNew) {
        Gdx.app.setLogLevel(3);
        this.rodaImpianNew = rodaImpianNew;
        this.assetManager = rodaImpianNew.getAssetManager();
        this.atlas = assetManager.get(AssetDesc.ATLAS);
        this.skin = assetManager.get(AssetDesc.NEWSKIN);
    }

    @Override
    public void render(float delta) {
        GameConfig.clearScreen();
        update(delta);
        world.step(1 / 60f, 6, 2);
        worldStage.act();
        worldStage.draw();
        stage.act();
        stage.draw();

    }

    public void update(float delta) {

    }
}
