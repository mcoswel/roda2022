package com.somboi.gdx.base;

import android.util.Log;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.config.GameConfig;

public class BaseScreen extends ScreenAdapter {
    protected final Viewport viewport = new FitViewport(GameConfig.SCWIDTH, GameConfig.SCHEIGHT);
    protected final Stage stage = new Stage(viewport);
    protected final OrthographicCamera worldCamera = new OrthographicCamera();
    protected final Viewport worldViewport = new FitViewport(9f, 16f, worldCamera);
    protected final Stage worldStage = new Stage(worldViewport);
    protected final RodaImpian rodaImpian;
    protected final Logger logger = new Logger(this.getClass().getName(), 3);
    //protected final AssetManager assetManager;
    //protected final Skin skin;

    public BaseScreen(RodaImpian rodaImpian) {
        this.rodaImpian = rodaImpian;
    }

    @Override
    public void render(float delta) {
        GameConfig.clearScreen();
        update(delta);
        worldStage.draw();
        stage.draw();

    }

    public void update(float delta){
        worldStage.act();
        stage.act();
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
        worldViewport.update(width, height,true);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        stage.dispose();
        worldStage.dispose();
    }
}
