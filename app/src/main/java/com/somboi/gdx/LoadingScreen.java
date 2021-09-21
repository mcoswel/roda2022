package com.somboi.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Logger;
import com.somboi.gdx.assets.QuestionsGenerator;
import com.somboi.gdx.assets.QuestionsReady;
import com.somboi.gdx.saves.QuestionsSaves;

public class LoadingScreen extends ScreenAdapter {
    private final RodaImpian rodaImpian;
    private final AssetManager assetManager;
    private final Logger logger = new Logger(this.getClass().getName(), 3);

    public LoadingScreen(RodaImpian rodaImpian) {
        this.rodaImpian = rodaImpian;
        this.assetManager = rodaImpian.getAssetManager();
        Gdx.app.setLogLevel(3);
        logger.debug("show");
        String word;
        QuestionsSaves questionsSaves = new QuestionsSaves();
        FileHandle fileHandle = Gdx.files.local("questions");
        /**
         * online sideload here
         */
        QuestionsGenerator questionsGenerator = questionsSaves.loadFromInternal(Gdx.files.internal("questions"));
        if (fileHandle.exists()){
            questionsGenerator = questionsSaves.loadFromInternal();
        }
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

       QuestionsReady questionsReady =  questionsGenerator.run();
       logger.debug("Subject round one: "+questionsReady.getSubjectRoundOne());
       for (String q: questionsReady.getRoundOne()){
           logger.debug("Round one: "+q);
       }

        logger.debug("Subject round two: "+questionsReady.getSubjectRoundTwo());
        for (String q: questionsReady.getRoundTwo()){
            logger.debug("Round two: "+q);
        }

        logger.debug("Subject round three: "+questionsReady.getSubjectRoundThree());
        for (String q: questionsReady.getRoundThree()){
            logger.debug("Round three: "+q);
        }

        logger.debug("Subject round four: "+questionsReady.getSubjectRoundFour());
            logger.debug("Round four: "+questionsReady.getBonusRound());




    }

    @Override
    public void show() {
        super.show();
        logger.debug("show");

    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}



