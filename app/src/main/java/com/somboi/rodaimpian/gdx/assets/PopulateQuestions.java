package com.somboi.rodaimpian.gdx.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Logger;
import com.somboi.rodaimpian.saves.QuestionsSaves;

import java.io.BufferedReader;
import java.util.Locale;

public class PopulateQuestions {
    private QuestionsSaves questionsSaves = new QuestionsSaves();
    private final Logger logger = new Logger(this.getClass().getName(), 3);

    public PopulateQuestions() {
        QuestionsGenerator questionsGenerator = new QuestionsGenerator();
        Array<String> subjects = new Array<>(new String[]{"ADIWIRA", "NAMA ARTIS", "BARANGAN RUMAH", "BUAH-BUAHAN", "HAIWAN", "JENAMA",
                "KELAB BOLA", "KEMENTERIAN", "KUGIRAN", "MAKANAN", "NAMA POKOK", "NAMA SYARIKAT", "NAMA TEMPAT", "NEGARA", "PEKERJAAN",
                "PERISIAN", "SAINS & TEKNOLOGI", "ACARA SUKAN", "UNSUR KIMIA", "ATLET", "GALAKSI"
        });
        questionsGenerator.getSubjects().addAll(subjects);


        for (String s : subjects) {
            String filename = new String(s);
            filename = filename.replaceAll("[^a-zA-Z]+", "").toLowerCase(Locale.ROOT);
            FileHandle file2 = Gdx.files.internal("text/" + filename + ".txt");
            logger.debug("filename " + filename);
            try {
                String word = null;
                BufferedReader br = new BufferedReader(file2.reader());
                while ((word = br.readLine()) != null) {
                    if (word.length() > 0) {
                        QuestionSingle questionSingle = new QuestionSingle();
                        questionSingle.setSubject(s);
                        questionSingle.setQuestion(word.toUpperCase());
                        questionsGenerator.getQuestionSingles().add(questionSingle);
                    }
                }

            } catch (Exception e) {

            }
        }

        Json json = new Json();
        String qq = json.prettyPrint(questionsGenerator);
        FileHandle f = Gdx.files.local("qq.txt");
        f.writeString(qq, false);
        questionsGenerator.getQuestionSingles().shuffle();
        questionsGenerator.getQuestionSingles().shuffle();
        questionsGenerator.getQuestionSingles().shuffle();
        questionsGenerator.getQuestionSingles().shuffle();
        questionsGenerator.getQuestionSingles().shuffle();
        questionsGenerator.getQuestionSingles().shuffle();
        questionsGenerator.getQuestionSingles().shuffle();
        questionsGenerator.getQuestionSingles().shuffle();
        questionsGenerator.getQuestionSingles().shuffle();
        questionsGenerator.getQuestionSingles().shuffle();

        questionsSaves.saveQuestion(questionsGenerator);

    }
}
