package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.QuestionsGenerator;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.screen.MenuScreen;
import com.somboi.rodaimpian.saves.PlayerSaves;
import com.somboi.rodaimpian.saves.QuestionsSaves;

import java.util.ArrayList;
import java.util.UUID;

public class LoadingScreenNew extends ScreenAdapter {
    private final RodaImpianNew rodaImpian;
    private final AssetManager assetManager;
    private final PlayerSaves playerSaves = new PlayerSaves();
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    private final Texture loading = new Texture(Gdx.files.internal("textures/loading.png"));
    private final Viewport viewport;
    private final SpriteBatch batch = new SpriteBatch();
    private float rotation;
    private final OrthographicCamera camera;
    public LoadingScreenNew(RodaImpianNew rodaImpian) {
        this.rodaImpian = rodaImpian;
        this.assetManager = rodaImpian.getAssetManager();
        Gdx.app.setLogLevel(3);
        // rodaImpian.loadRewardedAds();
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.SCWIDTH, GameConfig.SCHEIGHT,camera);


        assetManager.load(AssetDesc.TEXTUREATLAS);
        assetManager.load(AssetDesc.CONFETTI);
        assetManager.load(AssetDesc.WINANIMATION);
        assetManager.load(AssetDesc.SPARKLE);
        assetManager.load(AssetDesc.BLURBG);
        assetManager.load(AssetDesc.SKIN);
        assetManager.load(AssetDesc.AWWSOUND);
        assetManager.load(AssetDesc.CHEERSOUND);
        assetManager.load(AssetDesc.ROTATESOUND);
        assetManager.load(AssetDesc.CORRECTSOUND);
        assetManager.load(AssetDesc.WRONGSOUND);
        assetManager.load(AssetDesc.WINSOUND);
        assetManager.load(AssetDesc.CLOCKSOUND);
        assetManager.load(AssetDesc.SLAPSOUND);
        assetManager.load(AssetDesc.BG);
        assetManager.load(AssetDesc.HOURGLASS);
        assetManager.load(AssetDesc.ATLAS);
        assetManager.load(AssetDesc.NEWSKIN);
    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        GameConfig.clearScreen();
        //viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(loading, 200f, 550, 250f, 250f,500f,500f,1,1,rotation-=delta*100,0,0,loading.getWidth(),
                loading.getHeight(),false,false);
        batch.end();

        if (rodaImpian.getAssetManager().update()) {
            //rodaImpian.setScreen(new GameScreen(rodaImpian));
            rodaImpian.setScreen(new SkinTest(rodaImpian));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }
}



