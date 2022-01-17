package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdxnew.assets.LoadFromCSV;
import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
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
    private final QuestionsSaves questionsSaves = new QuestionsSaves();
    private final AsyncResult<Void> task;

    public LoadingScreenNew(RodaImpianNew rodaImpian) {
        this.rodaImpian = rodaImpian;
        this.assetManager = rodaImpian.getAssetManager();
        Gdx.app.setLogLevel(3);
        // rodaImpian.loadRewardedAds();
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.SCWIDTH, GameConfig.SCHEIGHT, camera);
        PlayerNew player = playerSaves.loadPlayerNew();
        if (player == null) {
            player = new PlayerNew();
            player.setName(StringRes.ANON);
            player.setPlayerBonus(new ArrayList<>());
            player.setPlayerGifts(new ArrayList<>());
            player.setUid(UUID.randomUUID().toString());
            if (rodaImpian.getFcmToken() != null) {
                player.setFcmToken(rodaImpian.getFcmToken());
            }
            playerSaves.savePlayerNew(player);
        }

        rodaImpian.setPlayerSaves(playerSaves);
        rodaImpian.setPlayer(player);
        if (player.getPlayerGifts() != null) {
            logger.debug("gifts size " + player.getPlayerGifts().size() + " gifts " + player.getPlayerGifts().toString());
        }
        if (player.getPlayerBonus() != null) {
            logger.debug("bonus size " + player.getPlayerBonus().size() + " bonuses " + player.getPlayerBonus().toString());
        }
        logger.debug("bankrupt size " + player.getBankrupt());
        logger.debug("times played " + player.getTimesPlayed());

        logger.debug("best score " + player.getBestScore());
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
        assetManager.load(AssetDesc.POLICE);
        assetManager.load(AssetDesc.AMBULANCE);
        assetManager.load(AssetDesc.FIRETRUCK);

        task = new AsyncExecutor(1).submit(new AsyncTask<Void>() {
            @Override
            public Void call() throws Exception {

                final Array<QuestionNew> mainGroup = questionsSaves.loadMainGroup();
                final Array<QuestionNew> bonusGroup = questionsSaves.loadBonusGroup();
                final Array<String> mainSubjects = questionsSaves.loadMainGroupSubjects();
                final Array<String> bonusSubjects = questionsSaves.loadBonusGroupSubjects();
                mainSubjects.shuffle();
                bonusSubjects.shuffle();
                mainGroup.shuffle();
                bonusGroup.shuffle();

                mainloop:
                for (int i = 0; i < 3; i++) {
                    for (QuestionNew questionNew : mainGroup) {
                        if (questionNew.getSubject().equals(mainSubjects.get(i))) {
                            rodaImpian.getPreparedQuestions().add(questionNew);
                            bonusSubjects.removeValue(questionNew.getSubject(), false);
                            if (i == 2) {
                                break mainloop;
                            }
                            break;
                        }
                    }
                }


                String bonusSubj = bonusSubjects.random();
                for (QuestionNew questionNew : bonusGroup) {
                    if (questionNew.getSubject().equals(bonusSubj)) {
                        rodaImpian.getPreparedQuestions().add(questionNew);
                        break;
                    }
                }

                logger.debug("questions size " + rodaImpian.getPreparedQuestions().size);

                /*for (int i = 0; i < 5; i++) {
                    rodaImpian.getPreparedQuestions().add(questionNewArray.random());
                }*/
                return null;
            }
        });
    }


    @Override
    public void show() {
     //   LoadFromCSV.execute();
    }


    @Override
    public void render(float delta) {
        GameConfig.clearScreen();
        //viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(loading, 200f, 550, 250f, 250f, 500f, 500f, 1, 1, rotation -= delta * 100, 0, 0, loading.getWidth(),
                loading.getHeight(), false, false);
        batch.end();

        if (rodaImpian.getAssetManager().update() && task.isDone()) {
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



