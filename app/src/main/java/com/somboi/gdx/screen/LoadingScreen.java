package com.somboi.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Logger;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.assets.QuestionSingle;
import com.somboi.gdx.assets.QuestionsGenerator;
import com.somboi.gdx.assets.QuestionsReady;
import com.somboi.gdx.assets.StringRes;
import com.somboi.gdx.saves.QuestionsSaves;

import java.io.BufferedReader;

public class LoadingScreen extends ScreenAdapter {
    private final RodaImpian rodaImpian;
    private final AssetManager assetManager;
    private final Logger logger = new Logger(this.getClass().getName(), 3);

    public LoadingScreen(RodaImpian rodaImpian) {
        this.rodaImpian = rodaImpian;
        this.assetManager = rodaImpian.getAssetManager();
        Gdx.app.setLogLevel(3);
        logger.debug("show");
        QuestionsSaves questionsSaves = new QuestionsSaves();

        /**
         * online sideload here
         */
        QuestionsGenerator questionsGenerator = questionsSaves.loadFromLocal();
        FileHandle fileHandle = Gdx.files.internal(StringRes.QUESTIONS);
        if (fileHandle.exists()) {
            questionsGenerator = questionsSaves.loadFromInternal(fileHandle);
        }
        questionsSaves.saveQuestion(questionsGenerator);
        logger.debug("Question Generator "+questionsGenerator.getSubjects().toString());

    /*    questionsGenerator.getSubjects().add("PERISIAN");
        questionsGenerator.getSubjects().add("ADIWIRA");
        FileHandle file = Gdx.files.internal("adiwira.txt");
        try {
            String word = null;
            BufferedReader br = new BufferedReader(file.reader());
            while ((word = br.readLine()) != null) {
                QuestionSingle questionSingle = new QuestionSingle();
                questionSingle.setSubject("ADIWIRA");
                questionSingle.setQuestion(word.toUpperCase());
                questionsGenerator.getQuestionSingles().add(questionSingle);
                logger.debug(word);
            }

        } catch (Exception e) {

        }
        FileHandle file2 = Gdx.files.internal("perisian.txt");
        try {
            String word = null;
            BufferedReader br = new BufferedReader(file2.reader());
            while ((word = br.readLine()) != null) {
                QuestionSingle questionSingle = new QuestionSingle();
                questionSingle.setSubject("PERISIAN");
                questionSingle.setQuestion(word.toUpperCase());
                questionsGenerator.getQuestionSingles().add(questionSingle);
                logger.debug(word);
            }

        } catch (Exception e) {

        }*/

      /*  Json json = new Json();
        String qq = json.prettyPrint(questionsGenerator);
        FileHandle f = Gdx.files.local("qq.txt");
        f.writeString(qq, false);*/


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
        super.show();
        logger.debug("loading screen ");
        if (rodaImpian.getMatchScreen()==null){
            rodaImpian.setMatchScreen(new MatchScreen(rodaImpian));
        }
        rodaImpian.spinWheel();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}



