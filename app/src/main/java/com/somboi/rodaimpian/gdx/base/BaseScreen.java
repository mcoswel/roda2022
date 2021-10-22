package com.somboi.rodaimpian.gdx.base;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.GameSound;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdx.entities.BgImg;
import com.somboi.rodaimpian.gdx.entities.Player;

public class BaseScreen extends ScreenAdapter {
    protected final Viewport viewport = new FitViewport(GameConfig.SCWIDTH, GameConfig.SCHEIGHT);
    protected final Stage stage = new Stage(viewport);
    protected final OrthographicCamera worldCamera = new OrthographicCamera();
    protected final Viewport worldViewport = new FitViewport(9f, 16f, worldCamera);
    protected final Stage worldStage = new Stage(worldViewport);
    protected final RodaImpian rodaImpian;
    protected final Logger logger = new Logger(this.getClass().getName(), 3);
    protected final AssetManager assetManager;
    protected final Skin skin;
    protected final TextureAtlas textureAtlas;
    protected final GameSound gameSound;
    protected final Player player;
    protected final BgImg bgImg;
    protected boolean playing = true;

    public BaseScreen(RodaImpian rodaImpian) {
        this.rodaImpian = rodaImpian;
        this.player = rodaImpian.getPlayer();
        assetManager = rodaImpian.getAssetManager();
        skin = assetManager.get(AssetDesc.SKIN);
        textureAtlas = assetManager.get(AssetDesc.TEXTUREATLAS);
        gameSound = new GameSound(assetManager);
        bgImg = new BgImg(assetManager.get(AssetDesc.BLURBG));
        stage.addActor(bgImg);

    }

    @Override
    public void render(float delta) {
        GameConfig.clearScreen();
        if (playing) {
            update(delta);
            stage.draw();
            worldStage.draw();
            stage.act();
            worldStage.act();
        }
    }

    public void update(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        worldViewport.update(width, height, true);
    }


    @Override
    public void dispose() {
        stage.dispose();
        worldStage.dispose();
    }

}
