package com.somboi.rodaimpian.gdxnew.entitiesnew;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdxnew.actors.PlayerGuis;
import com.somboi.rodaimpian.gdxnew.actors.PlayerMenu;
import com.somboi.rodaimpian.gdxnew.actors.TileBase;
import com.somboi.rodaimpian.gdxnew.games.BaseGame;
import com.somboi.rodaimpian.gdxnew.games.CpuMovement;

public class AiMoves {
    private final BaseGame baseGame;
    private int completion;
    private PlayerMenu playerMenu;
    private Array<TileBase> tileBases;
    private PlayerGuis playerGuis;
    private PlayerNew playerNew;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    private final Array<Character> groupOne = new Array<>(GameConfig.GROUP_CONS_ONE);
    private final Array<Character> groupTwo = new Array<>(GameConfig.GROUP_CONS_ONE);
    private final Array<Character> groupThree = new Array<>(GameConfig.GROUP_CONS_ONE);

    public AiMoves(BaseGame baseGame) {
        this.baseGame = baseGame;
    }

    public void execute(Array<TileBase> tileBases, PlayerMenu playerMenu, PlayerNew playerNew, PlayerGuis playerGuis) {
        final Array<CpuMovement> availableMoves = new Array<>();
        this.playerMenu = playerMenu;
        this.tileBases = tileBases;
        this.playerGuis = playerGuis;
        this.playerNew = playerNew;
        completion = tilesRevealed();


        if (playerNew.getScore() >= 250 && playerMenu.vocalAvailable()) {
            availableMoves.add(CpuMovement.CHOOSEVOCAL);
        }

        if (playerMenu.consonantAvailable()) {
            availableMoves.add(CpuMovement.SPIN);
        }

        float percentageComplete = (float)completion / (float)tileBases.size;

        logger.debug("completion " + completion+" tile size "+tileBases.size+" percentage " + percentageComplete);
        if (percentageComplete > 0.75f || !playerMenu.consonantAvailable()) {
            availableMoves.add(CpuMovement.COMPLETE);
        }

        availableMoves.shuffle();
        CpuMovement executeMove = availableMoves.first();

        float greedyRandom = MathUtils.random(0, 1);

        if (greedyRandom == 1) {
            logger.debug("greedy");
            if (availableMoves.contains(CpuMovement.SPIN, false) && questionHaveConsonants()) {
                chooseSpin();
                return;
            }
        }

        if (executeMove.equals(CpuMovement.SPIN)) {
            chooseSpin();
        } else if (executeMove.equals(CpuMovement.CHOOSEVOCAL)) {
            chooseVocals();
        } else {
            chooseComplete();
        }


    }

    private void chooseSpin() {
        playerGuis.chat(StringRes.CPUPUTAR.random());
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                baseGame.spinWheel(true, false);
            }
        }, 1.7f);
    }

    private void wrongComplete() {
        playerGuis.chat(StringRes.ANSWERIS + tileBases.get(0).getLetter() + tileBases.get(1).getLetter() +
                tileBases.get(2).getLetter() +
                " ...err... ");
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                baseGame.changeTurn();
            }
        }, 2f);
    }

    private void truComplete() {
        playerGuis.chat(StringRes.ANSWERIS + baseGame.getAnswerString());
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                baseGame.finishGame();
            }
        }, 2f);
    }

    private void chooseComplete() {
        playerGuis.chat(StringRes.CPUCOMPLETE);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (randomize() < completion) {
                    truComplete();
                } else {
                    wrongComplete();
                }
            }
        }, 1.5f);
    }

    private void chooseVocals() {
        playerNew.setScore(playerNew.getScore() - 250);
        int random = randomize();
        String c1 = chooseCorrectVocals();
        String c2 = chooseRandomVocals();
        String c3 = String.valueOf(playerMenu.getVocalLetter().charAt(0));
        if (random < completion && questionHaveVocals() && c1!=null) {
            answerVocals(c1);
            return;
        } else if (c2!=null){
            answerVocals(c2);
            return;
        }else{
            answerVocals(c3);
            return;
        }
    }

    private String chooseCorrectVocals() {
        Array<String> vocals = new Array<>();
        String vocalsLeft = playerMenu.getVocalLetter().toString().toLowerCase();
        for (TileBase t : tileBases) {
            String c = t.getLetter().toLowerCase();

            if (vocalsLeft.contains(c)) {
                vocals.add(t.getLetter());
            }
        }
        if (vocals.isEmpty()){
            return null;
        }
        vocals.shuffle();
        return vocals.first();
    }

    private String chooseRandomVocals() {
        logger.debug("choose random vocals");

        Array<Character> vocals = new Array<>(GameConfig.VOCALS);
        vocals.shuffle();
        for (Character c : vocals) {
            if (playerMenu.getVocalLetter().toString().contains(String.valueOf(c))) {
                return String.valueOf(c);
            }
        }
        return String.valueOf(playerMenu.getVocalLetter().charAt(0));
    }

    public void chooseConsonants() {
        int random = randomize();
        logger.debug("random no " + random);
        String c1 = chooseCorrectConsonants();
        String c2 = chooseRandomConsonants();
        String c3 = String.valueOf(playerMenu.getConsonantLetter().charAt(0));
        if (random < completion && questionHaveConsonants() && c1!=null) {
            answerConsonants(c1);
            return;
        } else if (c2!=null){
            answerConsonants(c2);
            return;
        }else{
            answerConsonants(c3);
            return;
        }
    }

    private int randomize() {
        return MathUtils.random(0, tileBases.size);
    }

    private String chooseCorrectConsonants() {
        Array<String> correctCons = new Array<>();
        String consonantLeft = playerMenu.getConsonantLetter().toString().toLowerCase();
        for (TileBase t : tileBases) {
            String c = t.getLetter().toLowerCase();
            if (consonantLeft.contains(c)) {
                correctCons.add(t.getLetter());
            }
        }
        if (correctCons.isEmpty()){
            return null;
        }
        correctCons.shuffle();
        return correctCons.first();
    }

    private String chooseRandomConsonants() {
        logger.debug("random consonant");
        groupOne.shuffle();
        groupTwo.shuffle();
        groupThree.shuffle();
        for (Character c : groupOne) {
            String c1 = String.valueOf(c);
            if (playerMenu.getConsonantLetter().toString().contains(c1)) {
                return c1;
            }
        }

        for (Character c : groupTwo) {
            String c1 = String.valueOf(c);
            if (playerMenu.getConsonantLetter().toString().contains(c1)) {
                return c1;
            }
        }
        for (Character c : groupThree) {
            String c1 = String.valueOf(c);
            if (playerMenu.getConsonantLetter().toString().contains(c1)) {
                return c1;
            }
        }
        return String.valueOf(playerMenu.getConsonantLetter().charAt(0));
    }

    private void answerConsonants(String answer) {
        logger.debug("answer consonants: "+answer);
        playerGuis.chat(StringRes.CPUCHOOSECONSONANTS.random() + answer);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                baseGame.checkAnswer(answer);
            }
        }, 1.5f);
    }

    private void answerVocals(String answer) {
        logger.debug("answer vocal: "+answer);
        playerGuis.chat(StringRes.CPUBUYVOCALS.random() + answer);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                baseGame.checkAnswer(answer);
            }
        }, 1.5f);
    }

    private boolean questionHaveVocals() {
        String vocals = "aeiou";
        for (TileBase t : tileBases) {
            if (!t.isRevealed()) {
                String c = t.getLetter().toLowerCase();
                if (vocals.contains(c)) {
                    return true;
                }
            }
        }

        return false;
    }


    private boolean questionHaveConsonants() {
        String consonants = "bcdfghjklmnpqrstvwxyz";
        for (TileBase t : tileBases) {
            if (!t.isRevealed()) {
                String c = t.getLetter().toLowerCase();
                if (consonants.contains(c)) {
                    return true;
                }
            }
        }

        return false;
    }

    private int tilesRevealed() {
        int left = 0;
        for (TileBase t : tileBases) {
            if (t.isRevealed()) {
                left++;
            }
        }
        return left;
    }
}
