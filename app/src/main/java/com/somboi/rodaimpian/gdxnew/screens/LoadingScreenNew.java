package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.saves.PlayerSaves;

import java.util.HashMap;
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
        PlayerNew player = playerSaves.loadPlayerNew();
        if (player==null){
            player = new PlayerNew();
            player.setName(StringRes.ANON);
            player.setPlayerBonus(new HashMap<>());
            player.setPlayerGifts(new HashMap<>());
            player.setUid(UUID.randomUUID().toString());
            if (rodaImpian.getFcmToken()!=null){
                player.setFcmToken(rodaImpian.getFcmToken());
            }
            playerSaves.savePlayerNew(player);
        }
        rodaImpian.setPlayer(player);
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
        assetManager.load(AssetDesc.GAMEBGGOLD);
        assetManager.load(AssetDesc.WHEELBG);
        assetManager.load(AssetDesc.GAMEBGRED);
        assetManager.load(AssetDesc.BLURRED);
        assetManager.load(AssetDesc.BLURGOLD);


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
            rodaImpian.setMainScreen(new MainScreen(rodaImpian));
            rodaImpian.mainMenu();
            //rodaImpian.setScreen(new GameScreen(rodaImpian));
            //rodaImpian.setScreen(new SkinTest(rodaImpian));
            //rodaImpian.setScreen(new GameScreen(rodaImpian));
            //rodaImpian.setScreen(new SpinScreen(rodaImpian));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }
}



