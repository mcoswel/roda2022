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
        logger.debug("completeion percent " + completion);
        this.playerMenu = playerMenu;
        this.tileBases = tileBases;
        this.playerGuis = playerGuis;
        this.playerNew = playerNew;
        completion = tilesLeft();

        if (playerNew.getScore() >= 250 && playerMenu.vocalAvailable()) {
            availableMoves.add(CpuMovement.CHOOSEVOCAL);
        }

        if (playerMenu.consonantAvailable()) {
            availableMoves.add(CpuMovement.SPIN);
        }

        if (completion <= 2) {
            availableMoves.add(CpuMovement.COMPLETE);
        }

        availableMoves.shuffle();
        CpuMovement executeMove = availableMoves.first();
        if (executeMove.equals(CpuMovement.SPIN)) {
            playerGuis.chat(StringRes.CPUPUTAR.random());
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    baseGame.spinWheel(true);
                }
            }, 1.7f);
        } else if (executeMove.equals(CpuMovement.CHOOSEVOCAL)) {
            chooseVocals();
        } else {
            chooseComplete();
        }


    }

    private void wrongComplete() {

    }

    private void truComplete() {

    }

    private void chooseComplete() {

    }

    private void chooseVocals() {
        logger.debug("choose vocals");
        playerNew.setScore(playerNew.getScore()-250);
        if (randomize() < completion && questionHaveVocals()) {
           answerVocals(chooseCorrectVocals());
        } else {
            answerVocals(chooseRandomVocals());
        }
    }

    private String chooseCorrectVocals() {

        Array<String> vocals = new Array<>();
        for (TileBase t : tileBases) {
            vocals.add(t.getLetter());
        }
        vocals.shuffle();
        return vocals.first();
    }

    private String chooseRandomVocals() {
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
        if (randomize() < completion && questionHaveConsonants()) {
            answerConsonants(chooseCorrectConsonants());
        } else {
            answerConsonants(chooseRandomConsonants());
        }
    }

    private int randomize() {
        return MathUtils.random(0, tileBases.size - 1);
    }

    private String chooseCorrectConsonants() {
        Array<String> correctCons = new Array<>();
        logger.debug("correct consonant");
        for (TileBase t : tileBases) {
            correctCons.add(t.getLetter());
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
                answerConsonants(c1);
                return c1;
            }
        }
        for (Character c : groupThree) {
            String c1 = String.valueOf(c);
            if (playerMenu.getConsonantLetter().toString().contains(c1)) {
                answerConsonants(c1);
                return c1;
            }
        }
        return String.valueOf(playerMenu.getConsonantLetter().charAt(0));
    }

    private void answerConsonants(String answer) {
        playerGuis.chat(StringRes.CPUCHOOSECONSONANTS.random() + answer);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                baseGame.checkAnswer(answer);
            }
        }, 1f);
    }

    private void answerVocals(String answer) {
        playerGuis.chat(StringRes.CPUBUYVOCALS.random() + answer);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                baseGame.checkAnswer(answer);
            }
        }, 1f);
    }

    private boolean questionHaveVocals() {
        String questions = "";
        for (TileBase t : tileBases) {
            questions += t.getLetter();
        }
        String vocals = "AEIOU";
        if (questions.contains(vocals)) {
            return true;
        }
        return false;
    }


    private boolean questionHaveConsonants() {
        String questions = "";
        for (TileBase t : tileBases) {
            questions += t.getLetter();
        }
        String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
        if (questions.contains(consonants)) {
            return true;
        }
        return false;
    }

    private int tilesLeft() {
        int left = 0;
        for (TileBase t : tileBases) {
            if (!t.isRevealed()) {
                left++;
            }
        }
        return left;
    }
}
