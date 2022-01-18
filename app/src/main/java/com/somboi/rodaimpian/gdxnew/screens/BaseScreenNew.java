package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdxnew.actors.ActorFactory;
import com.somboi.rodaimpian.gdxnew.actors.MenuFactory;
import com.somboi.rodaimpian.gdxnew.actors.YesNoDiag;
import com.somboi.rodaimpian.saves.PlayerSaves;

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
    protected final ActorFactory actorFactory;
    protected final PlayerSaves saves = new PlayerSaves();

    public BaseScreenNew(RodaImpianNew rodaImpianNew) {
        Gdx.app.setLogLevel(3);
        this.rodaImpianNew = rodaImpianNew;
        this.assetManager = rodaImpianNew.getAssetManager();
        this.atlas = assetManager.get(AssetDesc.ATLAS);
        this.skin = assetManager.get(AssetDesc.NEWSKIN);
        this.actorFactory = new ActorFactory(assetManager, worldStage, stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

    }

    @Override
    public void render(float delta) {
        GameConfig.clearScreen();
        update(delta);
        world.step(1 / 60f, 6, 2);
        stage.act();
        stage.draw();
        worldStage.act();
        worldStage.draw();
        backKey();
    }

    public void backKey(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            YesNoDiag yesNoDiag = new YesNoDiag(StringRes.EXIT2+"?", skin){
                @Override
                public void yesFunc() {
                    Gdx.app.exit();
                }
            };
            yesNoDiag.show(stage);
        }
    }

    public void update(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        hudViewport.update(width, height,true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        worldStage.dispose();
        world.dispose();
    }
}
