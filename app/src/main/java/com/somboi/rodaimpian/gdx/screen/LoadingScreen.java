package com.somboi.rodaimpian.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Logger;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.QuestionsGenerator;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.saves.PlayerSaves;
import com.somboi.rodaimpian.saves.QuestionsSaves;

import java.util.UUID;

public class LoadingScreen extends ScreenAdapter {
    private final RodaImpian rodaImpian;
    private final AssetManager assetManager;
    private final PlayerSaves playerSaves = new PlayerSaves();
    private final Logger logger = new Logger(this.getClass().getName(), 3);

    public LoadingScreen(RodaImpian rodaImpian) {
        this.rodaImpian = rodaImpian;
        this.assetManager = rodaImpian.getAssetManager();
        Gdx.app.setLogLevel(3);
        QuestionsSaves questionsSaves = new QuestionsSaves();

        /**
         * online sideload here
         */
        QuestionsGenerator questionsGenerator = questionsSaves.loadFromLocal();
        FileHandle fileHandle = Gdx.files.internal(StringRes.QUESTIONS);
        if (fileHandle.exists()) {
            questionsGenerator = questionsSaves.loadFromInternal(fileHandle);
        }


        Player player = playerSaves.load();
        PlayerOnline playerOnline = playerSaves.loadPlayerOnline();
        if (playerOnline != null) {
            rodaImpian.setPlayerOnline(playerOnline);
            rodaImpian.setBestScore(playerOnline.bestScore);
        }

        if (player == null) {
            player = new Player();
            player.name = StringRes.ANON;
            player.id = UUID.randomUUID().toString();
            playerOnline = new PlayerOnline();
            playerOnline.name = player.name;
            playerOnline.id = player.id;
            rodaImpian.setPlayerOnline(playerOnline);
            playerSaves.savePlayerOnline(playerOnline);
        }
        player.name = player.name.replaceAll("[^a-zA-Z0-9]", "");
        if (player.name.length()>10){
            player.name = player.name.substring(0,10);
        }
        playerSaves.save(player);
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

        questionsSaves.saveQuestion(questionsGenerator);
        rodaImpian.setQuestionsReady(questionsGenerator.run());

    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        if (rodaImpian.getAssetManager().update()) {
            rodaImpian.setMenuScreen(new MenuScreen(rodaImpian));
            rodaImpian.gotoMenu();
        }
    }

}


