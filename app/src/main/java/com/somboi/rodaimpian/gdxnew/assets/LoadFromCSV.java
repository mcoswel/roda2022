package com.somboi.rodaimpian.gdxnew.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.saves.QuestionsSaves;

import java.io.BufferedReader;

public class LoadFromCSV {
    public static void execute() {
        final Array<QuestionNew> questionArray = new Array<>();
        FileHandle file = Gdx.files.internal("test.csv");
        try {
            BufferedReader br = new BufferedReader(file.reader());
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                questionArray.add(createQuestions(values));
            }
        } catch (Exception e) {

        }
        QuestionsSaves questionsSaves = new QuestionsSaves();
        questionsSaves.saveQuestionsNew(questionArray);
    }

    public static QuestionNew createQuestions(String[] values) {
        QuestionNew questionNew = new QuestionNew();
        questionNew.setSubject(values[0].toUpperCase());
        questionNew.setLine1(values[1].toUpperCase());
        questionNew.setTotalline(1);

        if (values.length >= 3) {
            questionNew.setLine2(values[2].toUpperCase());
            questionNew.setTotalline(2);
        }

        if (values.length >= 4) {
            questionNew.setLine3(values[3].toUpperCase());
            questionNew.setTotalline(3);
        }
        if (values.length >= 5) {
            questionNew.setLine4(values[4].toUpperCase());
            questionNew.setTotalline(4);
        }
        return questionNew;


    }
}
