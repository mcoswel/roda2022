package com.somboi.gdx.assets;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class QuestionsGenerator {
    private Array<String> subjects;
    private Array<QuestionSingle> questionSingles;

    public QuestionsReady run() {
        QuestionsReady questionsReady = new QuestionsReady();
        subjects.shuffle();
        questionSingles.shuffle();

        List<List<String>> allRounds = new ArrayList<>();
        allRounds.add(new ArrayList<>());
        allRounds.add(new ArrayList<>());
        allRounds.add(new ArrayList<>());

        questionsReady.setSubjectRoundOne(subjects.get(0));
        questionsReady.setSubjectRoundTwo(subjects.get(1));
        questionsReady.setSubjectRoundThree(subjects.get(2));

        for (int i = 0; i < 3; i++) {
            while (allRounds.get(i).size() != 3) {
                for (QuestionSingle questionSingle : questionSingles) {
                    if (questionSingle.getSubject().equals(subjects.get(i))) {
                        String question = questionSingle.getQuestion();
                        if (allRounds.get(i).isEmpty() && question.length() <= 12 &&question.length()>0) {
                            allRounds.get(i).add(question);
                        }
                        if (allRounds.get(i).size() == 1 && question.length() <= 14 && question.length()>0 && !question.equals(allRounds.get(i).get(0))) {
                            allRounds.get(i).add(question);
                        }
                        if (allRounds.get(i).size() == 2 && question.length() <= 14&& question.length()>0 && !question.equals(allRounds.get(i).get(1))) {
                            allRounds.get(i).add(question);
                        }
                    }
                }
            }
        }

        questionsReady.setRoundOne(allRounds.get(0));
        questionsReady.setRoundTwo(allRounds.get(1));
        questionsReady.setRoundThree(allRounds.get(2));

        questionsReady.setSubjectRoundFour(subjects.get(subjects.size-1));
        for (QuestionSingle questionSingle : questionSingles) {
            if (questionSingle.getSubject().equals(questionsReady.getSubjectRoundFour())) {
                questionsReady.setBonusRound(questionSingle.getQuestion());
            }
        }

        return questionsReady;
    }

    public Array<String> getSubjects() {
        return subjects;
    }

    public Array<QuestionSingle> getQuestionSingles() {
        return questionSingles;
    }

    public void setSubjects(Array<String> subjects) {
        this.subjects = subjects;
    }

    public void setQuestionSingles(Array<QuestionSingle> questionSingles) {
        this.questionSingles = questionSingles;
    }
}
