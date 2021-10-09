package com.somboi.gdx.modes;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.assets.StringRes;
import com.somboi.gdx.base.ModeBase;
import com.somboi.gdx.config.GameConfig;
import com.somboi.gdx.entities.MatchRound;
import com.somboi.gdx.entities.Moves;
import com.somboi.gdx.entities.PlayerGui;
import com.somboi.gdx.entities.RandomCpu;

public class SinglePlayer extends ModeBase {
    private final Logger logger = new Logger(this.getClass().getName(), 3);

    public SinglePlayer(RodaImpian rodaImpian, Stage stage) {
        super(rodaImpian, stage);

    }

    @Override
    public void setPlayers() {
        RandomCpu randomCpu = new RandomCpu(textureAtlas);
        PlayerGui playerGuiCpu1 = new PlayerGui(randomCpu.getPlayer(0), randomCpu.getImage(0));
        PlayerGui playerGuiCpu2 = new PlayerGui(randomCpu.getPlayer(1), randomCpu.getImage(1));
        PlayerGui playerGuiHuman = new PlayerGui(thisPlayer, rodaImpian.getPlayerImage());
        playerGuis.add(playerGuiCpu1);
        playerGuis.add(playerGuiCpu2);
        playerGuis.add(playerGuiHuman);
    }


    @Override
    public void startPlays() {
        super.startPlays();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (activePlayer.isAi) {
                    aiRun();
                } else {
                    if (activePlayer.turn) {
                        showMenu();
                    }
                }
            }
        }, 2f);

    }

    @Override
    public void aiRun() {
        final Moves moves = decideAiMoves();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                cpuRun(moves);
            }
        }, 2f);
    }

    private Moves decideAiMoves() {
        Array<Moves> availableMoves = availableMoves();
        availableMoves.shuffle();
        Moves moves = availableMoves.random();
        float decide = randomize();
        if (decide < matchRound.completePercentage()) {
            if (matchRound.questionStillHaveConsonants()) {
                return Moves.SPINWHEEL;
            }
            if (availableMoves.contains(Moves.COMPLETE, false)) {
                return Moves.COMPLETE;
            }
            if (matchRound.questionStillHaveVocals()) {
                return Moves.BUYVOCAL;
            }
        }
        return moves;
    }

    private Array<Moves> availableMoves() {
        Array<Moves> available = new Array<>();
        if (matchRound.stillHaveConsonants()) {
            available.add(Moves.SPINWHEEL);
        }
        if (matchRound.stillHaveVocals() && activePlayer.currentScore >= 250) {
            available.add(Moves.BUYVOCAL);
        }
        if (matchRound.completePercentage() > 0.75f) {
            available.add(Moves.COMPLETE);
        }
        return available;
    }

    private void cpuRun(final Moves moves) {
        if (moves.equals(Moves.SPINWHEEL)) {
            cpuSpinWheel();
        } else if (moves.equals(Moves.BUYVOCAL)) {
            cpuBuyVocals();
        } else if (moves.equals(Moves.COMPLETE)) {
            cpuTryComplete();
        }
    }


    private void cpuBuyVocals() {
        queueChat(StringRes.CPUBUYVOCALS.random());
        activePlayer.currentScore -= 250;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                char c = matchRound.randomVocals();
                float decide = randomize();
                if (decide < matchRound.completePercentage() && matchRound.questionStillHaveVocals()) {
                    c = matchRound.findCorrectVocals();
                }
                final char cFinal = c;
                queueChat(StringRes.CPUCHOOSEVOCALS.random() + c);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        matchRound.checkAnswer(cFinal);
                    }
                }, 2f);

            }
        }, 2f);
    }

    private void cpuComplete() {
        queueChat(StringRes.ANSWERIS + matchRound.correctAnswers());
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                matchRound.revealAll();
            }
        }, 2f);
    }

    private void cpuTryComplete() {
        queueChat(StringRes.CPUCOMPLETE);
        float decide = randomize();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (decide < matchRound.completePercentage()) {
                    cpuComplete();
                } else {
                    queueChat(StringRes.ANSWERIS + " ... ... ");
                    gameSound.playWrong();
                    changeTurn();
                }
            }
        }, 2f);

    }

    private void cpuSpinWheel() {
        queueChat(StringRes.CPUPUTAR.random());
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                rodaImpian.spinWheel();
            }
        }, 2f);
    }

    @Override
    public void cpuChooseConsonants() {
        char c = findRandom();
        if (matchRound.completePercentage() > 0.4) {
            float decide = randomize();
            if (decide < matchRound.completePercentage() && matchRound.stillHaveConsonants()) {
                c = matchRound.findCorrectConsonants();
            }
        }

        final Character cFinal = c;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                queueChat(StringRes.CPUCHOOSECONSONANTS.random() + "\'" + cFinal + "\'");
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        matchRound.checkAnswer(cFinal);
                    }
                }, 2f);
            }
        }, 2f);
    }

    public float randomize() {
        return new Float(MathUtils.random(0f, 1f));
    }

    private Character findRandom() {
        GameConfig.GROUP_CONS_ONE.shuffle();
        GameConfig.GROUP_CONS_TWO.shuffle();
        GameConfig.GROUP_CONS_THREE.shuffle();
        Character c;
        if ((c = checkGroupOne()) != null) {
            return c;
        }
        if ((c = checkGroupTwo()) != null) {
            return c;
        }
        return (checkGroupThree());
    }

    private Character checkGroupOne() {
        for (Character c : GameConfig.GROUP_CONS_ONE) {
            if (matchRound.getConsonants().contains(String.valueOf(c))) {
                return c;
            }
        }
        return null;
    }

    private Character checkGroupTwo() {
        for (Character c : GameConfig.GROUP_CONS_TWO) {
            if (matchRound.getConsonants().contains(String.valueOf(c))) {
                return c;
            }
        }
        return null;
    }

    private Character checkGroupThree() {
        for (Character c : GameConfig.GROUP_CONS_THREE) {
            if (matchRound.getConsonants().contains(String.valueOf(c))) {
                return c;
            }
        }
        return null;
    }


}