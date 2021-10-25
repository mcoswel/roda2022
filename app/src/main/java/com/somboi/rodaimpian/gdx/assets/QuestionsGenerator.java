package com.somboi.rodaimpian.gdx.assets;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class QuestionsGenerator {
    private final Array<String> subjects = new Array<>();
    private final Array<QuestionSingle> questionSingles = new Array<>();
    private boolean complete;
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
        questionsReady.setSubjectRoundFour(subjects.get(3));


        int i = 0;
        int count = 0;
        while(allRounds.get(i).size()<3) {
            questionSingles.shuffle();
            for (QuestionSingle questionSingle : questionSingles) {
                String question = questionSingle.getQuestion();
                if (questionSingle.getSubject().equals(subjects.get(i))) {
                    if (allRounds.get(i).isEmpty() && question.length() <= 12 && question.length() > 0) {
                        allRounds.get(i).add(question);
                    }
                    if (allRounds.get(i).size() == 1 && question.length() <= 14 && question.length() > 0 && !question.equals(allRounds.get(i).get(0))) {
                        allRounds.get(i).add(question);
                    }
                    if (allRounds.get(i).size() == 2 && question.length() <= 14 && question.length() > 0
                            && !question.equals(allRounds.get(i).get(0))
                            && !question.equals(allRounds.get(i).get(1))
                    ) {
                        allRounds.get(i).add(question);
                    }
                    if (allRounds.get(i).size() == 3 && question.length() <= 12 && question.length() > 0
                            && !question.equals(allRounds.get(i).get(1))
                            && !question.equals(allRounds.get(i).get(2))
                    ) {
                        allRounds.get(i).add(question);
                    }
                }
            }
            count++;
            if (count == 500){
                run();
            }
        }

        complete =false;
        i =1;
        while(allRounds.get(i).size()<2) {
            questionSingles.shuffle();
            for (QuestionSingle questionSingle : questionSingles) {
                String question = questionSingle.getQuestion();
                if (questionSingle.getSubject().equals(subjects.get(i))) {
                    if (allRounds.get(i).isEmpty() && question.length() <= 12 && question.length() > 0) {
                        allRounds.get(i).add(question);
                    }
                    if (allRounds.get(i).size() == 1 && question.length() <= 14 && question.length() > 0 && !question.equals(allRounds.get(i).get(0))) {
                        allRounds.get(i).add(question);
                    }
                    if (allRounds.get(i).size() == 2 && question.length() <= 14 && question.length() > 0
                            && !question.equals(allRounds.get(i).get(0))
                            && !question.equals(allRounds.get(i).get(1))
                    ) {
                        allRounds.get(i).add(question);
                        complete = true;
                    }
                }
            }
            count++;
            if (count == 500){
                run();
            }
        }
        complete =false;
        i =2;
        while (allRounds.get(i).size()<1) {
            questionSingles.shuffle();
            for (QuestionSingle questionSingle : questionSingles) {
                String question = questionSingle.getQuestion();
                if (questionSingle.getSubject().equals(subjects.get(i))) {
                    if (allRounds.get(i).isEmpty() && question.length() <= 12 && question.length() > 0) {
                        allRounds.get(i).add(question);
                    }
                    if (allRounds.get(i).size() == 1 && question.length() <= 14 && question.length() > 0 && !question.equals(allRounds.get(i).get(0))) {
                        allRounds.get(i).add(question);
                        complete = true;
                    }
                }
            }
            count++;
            if (count == 500){
                run();
            }
        }



        questionsReady.setRoundOne(allRounds.get(0));
        questionsReady.setRoundTwo(allRounds.get(1));
        questionsReady.setRoundThree(allRounds.get(2));

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

}
