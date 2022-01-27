package com.somboi.rodaimpian.gdxnew.entitiesnew;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.assets.GameConfig;
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


        if (playerNew.getScore() >= 250 && questionHaveVocals() && playerMenu.vocalAvailable()) {
            availableMoves.add(CpuMovement.CHOOSEVOCAL);
        }

        if (playerMenu.consonantAvailable()) {
            availableMoves.add(CpuMovement.SPIN);
        }

        float percentageComplete = (float) completion / (float) tileBases.size;

        if (percentageComplete > 0.8f || !playerMenu.consonantAvailable()) {
            availableMoves.add(CpuMovement.COMPLETE);
        }

        availableMoves.shuffle();
        CpuMovement executeMove = availableMoves.first();

        if (!questionHaveConsonants() && playerNew.getScore() < 250) {
            executeMove = CpuMovement.COMPLETE;
        }

        if (questionHaveConsonants() && !playerMenu.vocalAvailable()){
            executeMove = CpuMovement.SPIN;
        }

        if (executeMove.equals(CpuMovement.SPIN)) {
            chooseSpin();
            return;
        } else if (executeMove.equals(CpuMovement.CHOOSEVOCAL)) {
            chooseVocals();
            return;
        } else {
            chooseComplete();
            return;
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
        int r = randomize();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (r < completion) {
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
        Character c1 = chooseCorrectVocals();
        Character c2 = chooseRandomVocals();
        Character chosen = playerMenu.getVocalLetter().get(0);
        if (random < completion && questionHaveVocals() && c1 != null) {
            chosen = c1;
        } else if (c2 != null) {
            chosen = c2;
        }
        answer(chosen,false);
    }

    private Character chooseCorrectVocals() {
        Array<Character> vocals = new Array<>();
        for (TileBase t : tileBases) {
            for (Character character : playerMenu.getVocalLetter()) {
                if (String.valueOf(character).equals(t.getLetter())) {
                    vocals.add(character);
                }
            }
        }
        if (vocals.isEmpty()) {
            return null;
        }
        vocals.shuffle();
        return vocals.first();
    }

    private Character chooseRandomVocals() {
        Array<Character> vocals = new Array<>(playerMenu.getVocalLetter());
        vocals.shuffle();
        return vocals.get(0);
    }

    public void chooseConsonants() {
        int random = randomize();
        Character c1 = chooseCorrectConsonants();
        Character c2 = chooseRandomConsonants();
        Character chosen = playerMenu.getConsonantLetter().get(0);
        if (random < completion && questionHaveConsonants() && c1 != null) {
            chosen = c1;
        } else if (c2 != null) {
            chosen = c2;
        }
        final Character chosenAnswer = chosen;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                answer(chosenAnswer, true);
            }
        }, 1.5f);

    }

    private int randomize() {
        return MathUtils.random(0, tileBases.size);
    }

    private Character chooseCorrectConsonants() {
        Array<Character> correctCons = new Array<>();
        for (TileBase t : tileBases) {

            for (Character character : playerMenu.getConsonantLetter()) {
                if (String.valueOf(character).equals(t.getLetter())) {
                    correctCons.add(character);
                }
            }
        }
        if (correctCons.isEmpty()) {
            return null;
        }
        correctCons.shuffle();
        return correctCons.first();
    }

    private Character chooseRandomConsonants() {
        groupOne.shuffle();
        groupTwo.shuffle();
        groupThree.shuffle();
        for (Character c : groupOne) {
            for (Character character : playerMenu.getConsonantLetter()) {
                if (character == c) {
                    return character;
                }
            }
        }

        for (Character c : groupTwo) {
            for (Character character : playerMenu.getConsonantLetter()) {
                if (character == c) {
                    return character;
                }
            }
        }
        for (Character c : groupThree) {
            for (Character character : playerMenu.getConsonantLetter()) {
                if (character == c) {
                    return character;
                }
            }
        }
        return playerMenu.getConsonantLetter().get(0);
    }

    private void answer(Character answer, boolean consonant){
        if (consonant){
            playerGuis.chat(StringRes.CPUCHOOSECONSONANTS.random() + answer);
        }else{
            playerGuis.chat(StringRes.CPUBUYVOCALS.random() + answer);
        }
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
