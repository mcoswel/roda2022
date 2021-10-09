package com.somboi.gdx.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.actor.CorrectScore;
import com.somboi.gdx.actor.Fireworks;
import com.somboi.gdx.actor.SubjectLabel;
import com.somboi.gdx.assets.AssetDesc;
import com.somboi.gdx.assets.GameSound;
import com.somboi.gdx.assets.StringRes;
import com.somboi.gdx.base.ModeBase;
import com.somboi.gdx.config.GameConfig;

import java.util.ArrayList;
import java.util.List;

public class MatchRound {
    private final List<Tiles> questionTiles = new ArrayList<>();
    private final RodaImpian rodaImpian;
    private final TextureAtlas textureAtlas;
    private final Group tilesGroup;
    private final Skin skin;
    private final int gameRound;
    private final GameSound gameSound;
    private String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
    private String vocals = "AEIOU";
    private final ModeBase modeBase;
    private final CorrectScore correctScore;

    public MatchRound(RodaImpian rodaImpian, GameSound gameSound, TextureAtlas textureAtlas, Group tilesGroup, Skin skin, int gameRound, ModeBase modeBase) {
        this.rodaImpian = rodaImpian;
        this.textureAtlas = textureAtlas;
        this.tilesGroup = tilesGroup;
        this.skin = skin;
        this.gameRound = gameRound;
        this.gameSound = gameSound;
        this.modeBase = modeBase;
        this.correctScore = new CorrectScore("SCORES", skin);
    }

    public void setQuestion() {
        questionTiles.clear();
        List<String> question = rodaImpian.getQuestionsReady().getRoundOne();
        String subjects = rodaImpian.getQuestionsReady().getSubjectRoundOne();
        if (gameRound == 1) {
            question = rodaImpian.getQuestionsReady().getRoundTwo();
            subjects = rodaImpian.getQuestionsReady().getSubjectRoundTwo();
        } else if (gameRound == 2) {
            question = rodaImpian.getQuestionsReady().getRoundThree();
            subjects = rodaImpian.getQuestionsReady().getSubjectRoundThree();
        }
        for (int i = 0; i < question.size(); i++) {
            for (int j = 0; j < question.get(i).length(); j++) {
                Tiles tiles = new Tiles(textureAtlas);
                tiles.setLetter(question.get(i).charAt(j));
                if ((i == 1 || i == 2) && question.get(i).length() == 13) {
                    tiles.setPosition(31f + (j * tiles.getWidth()), 1465f - (i * tiles.getHeight()));
                } else {
                    tiles.setPosition(91f + (j * tiles.getWidth()), 1465f - (i * tiles.getHeight()));
                }
                tiles.introAnimation();
                questionTiles.add(tiles);
            }
        }

        for (Tiles t : questionTiles) {
            tilesGroup.addActor(t);
        }
        tilesGroup.addActor(new SubjectLabel(subjects, skin));
    }

    public void checkAnswer(char c) {
        Player activePlayer = modeBase.getActivePlayer();
        WheelParam wheelParam = modeBase.getWheelParam();
        int multiplier = 0;
        boolean correct = false;
        StringBuilder consBuilder = new StringBuilder(consonants);
        for (int i = 0; i < consBuilder.length(); i++) {
            if (c == consBuilder.charAt(i)) {
                consBuilder.deleteCharAt(i);
            }
        }
        this.consonants = consBuilder.toString();

        StringBuilder vokalBuilder = new StringBuilder(vocals);
        for (int i = 0; i < vokalBuilder.length(); i++) {
            if (c == vokalBuilder.charAt(i)) {
                vokalBuilder.deleteCharAt(i);
            }
        }
        this.vocals = vokalBuilder.toString();

        for (Tiles t : questionTiles) {
            if (t.getLetter() == c && !t.isRevealed()) {
                correct = true;
                t.reveal(c);
                multiplier++;
            }
        }
        if (correct) {
            if (wheelParam.results.equals(StringRes.GIFT)) {
                modeBase.showGifts();
                gameSound.playCheer();
            }
            if (wheelParam.results.equals(StringRes.FREETURN) && !activePlayer.freeTurn && wheelParam.resultValue > 0) {
                activePlayer.freeTurn = true;
                correctScore.setText("$" + wheelParam.resultValue + " x " + multiplier + " + " + StringRes.FREETURN);
                modeBase.showFreeTurn();
            } else {
                if (wheelParam.resultValue > 0) {
                    correctScore.setText("$" + wheelParam.resultValue + " x " + multiplier);
                }
            }
            if (wheelParam.resultValue > 0) {
                activePlayer.currentScore += (wheelParam.resultValue * multiplier);
                correctScore.show(tilesGroup);
                if (wheelParam.resultValue * multiplier > 2500) {
                    gameSound.playCheer();
                }
            }
            gameSound.playCorrect();
            if (!checkRevealAll()) {
                modeBase.startPlays();
            } else {
                revealAll();
            }
            correctScore.pack();
            correctScore.setPosition(450f - correctScore.getWidth() / 2f, 850f);
        } else {
            if (wheelParam.results.equals(StringRes.GIFT)) {
                modeBase.cancelGifts();
            }
            gameSound.playWrong();
            modeBase.changeTurn();
        }
        wheelParam.results = "";
        wheelParam.resultValue = 0;
    }

    private boolean checkRevealAll() {
        for (Tiles t : questionTiles) {
            if (!t.isRevealed()) {
                return false;
            }
        }
        return true;
    }

    public String getConsonants() {
        return consonants;
    }

    public void setConsonants(String consonants) {
        this.consonants = consonants;
    }

    public String getVocals() {
        return vocals;
    }

    public void setVocals(String vocals) {
        this.vocals = vocals;
    }


    public Character findCorrectConsonants() {
        Array<Character> consonantsG = GameConfig.CONSONANTS;
        consonantsG.shuffle();
        for (Character c : consonantsG) {
            for (Tiles t : questionTiles) {
                if (c == t.getLetter() && !t.isRevealed()) {
                    return c;
                }
            }
        }
        return null;
    }

    public Character findCorrectVocals() {
        Array<Character> vocalsG = GameConfig.VOCALS;
        vocalsG.shuffle();
        for (Character c : vocalsG) {
            for (Tiles t : questionTiles) {
                if (c == t.getLetter() && !t.isRevealed()) {
                    return c;
                }
            }
        }
        return null;
    }

    public boolean questionStillHaveConsonants() {
        for (Tiles t : questionTiles) {
            for (Character c : GameConfig.CONSONANTS) {
                if (!t.isRevealed() && t.getLetter() == c) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean questionStillHaveVocals() {
        for (Tiles t : questionTiles) {
            for (Character c : GameConfig.VOCALS) {
                if (!t.isRevealed() && t.getLetter() == c) {
                    return true;
                }
            }
        }
        return false;
    }

    public float completePercentage() {
        float revealed = 0;
        for (Tiles t : questionTiles) {
            if (t.isRevealed()) {
                revealed++;
            }
        }
        return (revealed / questionTiles.size());
    }

    public String correctAnswers() {
        String correct = " ";
        if (gameRound == 0) {
            for (String s : rodaImpian.getQuestionsReady().getRoundOne()) {
                correct += s + " ";
            }
        } else if (gameRound == 1) {
            for (String s : rodaImpian.getQuestionsReady().getRoundTwo()) {
                correct += s + " ";
            }
        } else {
            for (String s : rodaImpian.getQuestionsReady().getRoundThree()) {
                correct += s + " ";
            }
        }
        return correct;
    }

    public void revealAll() {
        gameSound.playWinSound();
        if (gameRound == 2) {
            checkWinner();
        }
        for (Tiles t : questionTiles) {
            if (!t.isRevealed()) {
                t.reveal(t.getLetter());
            }
        }

        final Fireworks fireworks = new Fireworks(rodaImpian.getAssetManager().get(AssetDesc.WINANIMATION));
        tilesGroup.addActor(fireworks);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                fireworks.remove();
            }
        }, 3f);
        modeBase.getPlayerGuis().get(modeBase.getActivePlayer().guiIndex).getImage().addAction(Actions.moveTo(325f, 1124, 2f));
        modeBase.getActivePlayer().fullScore = modeBase.getActivePlayer().fullScore + modeBase.getActivePlayer().currentScore+500;
        modeBase.getPlayerGuis().get(modeBase.getActivePlayer().guiIndex).updateFullScore();
        modeBase.newRound();
        modeBase.removeHourGlass();
    }

    private void checkWinner() {

    }

    public boolean stillHaveConsonants() {
        return consonants.length() != 0;
    }

    public boolean stillHaveVocals() {
        return vocals.length() != 0;
    }

    public Character randomVocals() {
        Array<Character> characters = new Array<>();
        for (Character c : vocals.toCharArray()) {
            characters.add(c);
        }

        characters.shuffle();
        return characters.get(0);
    }

    public List<Tiles> completePuzzle() {
        List<Tiles> completPuzz = new ArrayList<>();
        for (Tiles t : questionTiles) {
            if (!t.isRevealed()) {
                try {
                    completPuzz.add((Tiles) t.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>(completPuzz);
    }

}
