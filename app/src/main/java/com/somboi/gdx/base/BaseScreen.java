package com.somboi.gdx.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.assets.AssetDesc;
import com.somboi.gdx.assets.GameSound;
import com.somboi.gdx.config.GameConfig;
import com.somboi.gdx.entities.BgImg;
import com.somboi.gdx.entities.Player;

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
    public BaseScreen(RodaImpian rodaImpian) {
        this.rodaImpian = rodaImpian;
        this.player = rodaImpian.getPlayer();
        assetManager = rodaImpian.getAssetManager();
        skin = assetManager.get(AssetDesc.SKIN);
        textureAtlas = assetManager.get(AssetDesc.TEXTUREATLAS);
        gameSound = new GameSound(assetManager);
        stage.addActor(new BgImg(assetManager.get(AssetDesc.BLURBG)));
    }

    @Override
    public void render(float delta) {
        GameConfig.clearScreen();
        update(delta);
        stage.draw();
        worldStage.draw();

    }

    public void update(float delta) {
        stage.act();
        worldStage.act();

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
