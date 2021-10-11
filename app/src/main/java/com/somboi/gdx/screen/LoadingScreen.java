package com.somboi.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Logger;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.assets.AssetDesc;
import com.somboi.gdx.assets.QuestionSingle;
import com.somboi.gdx.assets.QuestionsGenerator;
import com.somboi.gdx.assets.StringRes;
import com.somboi.gdx.entities.Player;
import com.somboi.gdx.saves.PlayerSaves;
import com.somboi.gdx.saves.QuestionsSaves;

import java.io.BufferedReader;
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
        if (player == null) {
            player = new Player();
            player.name = StringRes.ANON;
            player.id = UUID.randomUUID().toString();
            player.logged = true;
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

/*
        questionsGenerator.getSubjects().add("NAMA SYARIKAT");
        questionsGenerator.getSubjects().add("SAINS TEKNOLOGI");

        FileHandle file = Gdx.files.internal("namasyarikat.txt");
        try {
            String word = null;
            BufferedReader br = new BufferedReader(file.reader());
            while ((word = br.readLine()) != null) {
                QuestionSingle questionSingle = new QuestionSingle();
                questionSingle.setSubject("NAMA SYARIKAT");
                questionSingle.setQuestion(word.toUpperCase());
                questionsGenerator.getQuestionSingles().add(questionSingle);
                logger.debug(word);
            }

        } catch (Exception e) {

        }

        FileHandle file2 = Gdx.files.internal("sainteknologi.txt");
        try {
            String word = null;
            BufferedReader br = new BufferedReader(file2.reader());
            while ((word = br.readLine()) != null) {
                QuestionSingle questionSingle = new QuestionSingle();
                questionSingle.setSubject("SAINS TEKNOLOGI");
                questionSingle.setQuestion(word.toUpperCase());
                questionsGenerator.getQuestionSingles().add(questionSingle);
                logger.debug(word);
            }

        } catch (Exception e) {

        }

        Json json = new Json();
        String qq = json.prettyPrint(questionsGenerator);
        FileHandle f = Gdx.files.local("qq.txt");
        f.writeString(qq, false);*/

        questionsSaves.saveQuestion(questionsGenerator);
        rodaImpian.setQuestionsReady(questionsGenerator.run());

/*
        QuestionSingle questionSingle = new QuestionSingle();
        questionSingle.setSubject("PEKERJAAN");
        questionSingle.setQuestion("WARTAWAN");
        questionsGenerator.getQuestionSingles().add(questionSingle);

        questionSingle = new QuestionSingle();
        questionSingle.setSubject("SUKAN");
        questionSingle.setQuestion("GUSTI");
        questionsGenerator.getQuestionSingles().add(questionSingle);

        dataSaveSecurity.saveQuestion(questionsGenerator);*/

    /*    QuestionsReady questionsReady = questionsGenerator.run();
        logger.debug("Subject round one: " + questionsReady.getSubjectRoundOne());
        for (String q : questionsReady.getRoundOne()) {
            logger.debug("Round one: " + q);
        }

        logger.debug("Subject round two: " + questionsReady.getSubjectRoundTwo());
        for (String q : questionsReady.getRoundTwo()) {
            logger.debug("Round two: " + q);
        }

        logger.debug("Subject round three: " + questionsReady.getSubjectRoundThree());
        for (String q : questionsReady.getRoundThree()) {
            logger.debug("Round three: " + q);
        }

        logger.debug("Subject round four: " + questionsReady.getSubjectRoundFour());
        logger.debug("Round four: " + questionsReady.getBonusRound());*/

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



