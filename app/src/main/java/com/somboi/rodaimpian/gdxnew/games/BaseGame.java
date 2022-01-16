package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.GameSound;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdxnew.FlyingMoney;
import com.somboi.rodaimpian.gdxnew.actors.Bonuses;
import com.somboi.rodaimpian.gdxnew.actors.ChatBubble;
import com.somboi.rodaimpian.gdxnew.actors.Confetti;
import com.somboi.rodaimpian.gdxnew.actors.CorrectLabel;
import com.somboi.rodaimpian.gdxnew.actors.CpuFactory;
import com.somboi.rodaimpian.gdxnew.actors.EnvelopeNew;
import com.somboi.rodaimpian.gdxnew.actors.ErrDiag;
import com.somboi.rodaimpian.gdxnew.actors.Fireworks;
import com.somboi.rodaimpian.gdxnew.actors.Gifts;
import com.somboi.rodaimpian.gdxnew.actors.HourGlass;
import com.somboi.rodaimpian.gdxnew.actors.PlayerGuis;
import com.somboi.rodaimpian.gdxnew.actors.PlayerMenu;
import com.somboi.rodaimpian.gdxnew.actors.ProfilePic;
import com.somboi.rodaimpian.gdxnew.actors.Sparkling;
import com.somboi.rodaimpian.gdxnew.actors.TileBase;
import com.somboi.rodaimpian.gdxnew.actors.TrophyNew;
import com.somboi.rodaimpian.gdxnew.actors.VannaHost;
import com.somboi.rodaimpian.gdxnew.actors.WheelTurns;
import com.somboi.rodaimpian.gdxnew.actors.WinnerDialog;
import com.somboi.rodaimpian.gdxnew.actors.YesNoDiag;
import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.AiMoves;
import com.somboi.rodaimpian.gdxnew.entitiesnew.GiftsNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.interfaces.KeyListen;
import com.somboi.rodaimpian.gdxnew.screens.BombeiroScreen;
import com.somboi.rodaimpian.gdxnew.screens.SpinScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseGame {
    protected final Stage stage;
    protected final RodaImpianNew rodaImpianNew;
    protected final Array<PlayerGuis> playerGuis = new Array<>();
    protected final VannaHost vannaHost;
    protected final TextureAtlas atlas;
    protected final Skin skin;
    protected final CpuFactory cpuFactory;
    protected final Logger logger = new Logger(this.getClass().getName(), 3);
    protected int gameRound;
    protected QuestionNew currentQuestion;
    protected final Array<TileBase> tileBases = new Array<>();
    protected final GameSound gameSound;
    protected PlayerNew activePlayer;
    protected int currentIndex;
    protected final Label subjectLabel;
    protected Group tilesGroup = new Group();
    protected Group incompleteGroup = new Group();
    protected  PlayerMenu playerMenu;
    protected PlayerGuis currentGui;
    protected AiMoves aiMoves;
    protected CorrectLabel correctLabel;
    protected final HourGlass hourGlass;
    protected String answerString;
    protected final Gifts gifts;
    protected final Array<Integer> giftsIndexes = new Array<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23});
    protected final GiftsNew giftsNew = new GiftsNew();
    protected final Array<TileBase> incompleteTiles = new Array<>();
    protected KeyListen keyListen;
    protected String answerHolder;
    protected PlayerGuis testGui;
    protected boolean clickEnvelope;
    protected StringBuilder bonusStringHolder;
    protected Bonuses bonusGiftImg;

    public BaseGame(Stage stage, RodaImpianNew rodaImpianNew) {
        this.stage = stage;
        this.rodaImpianNew = rodaImpianNew;
        this.atlas = rodaImpianNew.getAssetManager().get(AssetDesc.ATLAS);
        this.skin = rodaImpianNew.getAssetManager().get(AssetDesc.NEWSKIN);
        this.cpuFactory = new CpuFactory(atlas);
        this.vannaHost = new VannaHost(rodaImpianNew.getAssetManager().get(AssetDesc.ATLAS));
        this.gameSound = new GameSound(rodaImpianNew.getAssetManager());
        this.subjectLabel = new Label("", skin);
        this.hourGlass = new HourGlass(rodaImpianNew.getAssetManager().get(AssetDesc.HOURGLASS));
        this.gifts = new Gifts(atlas, rodaImpianNew.getAssetManager().get(AssetDesc.SPARKLE));
        this.giftsIndexes.shuffle();
        stage.addActor(subjectLabel);
        stage.addActor(tilesGroup);
        stage.addActor(incompleteGroup);
        stage.addActor(vannaHost);
        setUpNewRound();
    }

    private void prepareEnvelope() {
        setUpNewRound();
        bonusGiftImg = new Bonuses(atlas, rodaImpianNew.getWheelParams().getBonusIndex());
        bonusGiftImg.setPosition(350f, 1110f);
        stage.addActor(new Sparkling(rodaImpianNew.getAssetManager().get(AssetDesc.SPARKLE)));
        stage.addActor(bonusGiftImg);
        correctLabel = new CorrectLabel(StringRes.CHOOSEENVELOPE, skin);
        stage.addActor(correctLabel);
        Array<Integer> randEnvelope = new Array<>(new Integer[]{0, 1});
        Array<Vector2> poseS = new Array<>(new Vector2[]{new Vector2(488f, 505f), new Vector2(75f, 505f)});
        final Array<EnvelopeNew> envelopeNews = new Array<>();
        randEnvelope.shuffle();
        poseS.shuffle();
        int index = 0;
        for (Integer integer : randEnvelope) {
            EnvelopeNew envelopeNew = new EnvelopeNew(atlas, integer, poseS.get(index));
            envelopeNews.add(envelopeNew);
            DragListener dragListener = new DragListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (!clickEnvelope) {
                        envelopeNew.opened(stage);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                removeEnvelopes(envelopeNews, integer);
                            }
                        }, 3f);
                        clickEnvelope = true;
                    }
                    return super.touchDown(event, x, y, pointer, button);
                }
            };
            envelopeNew.prepareEnvelope(stage, dragListener);
            index++;
        }
    }

    private void removeEnvelopes(Array<EnvelopeNew> envelopeNews, int integer) {
        for (EnvelopeNew envelopeNew : envelopeNews) {
            envelopeNew.clearAll();
        }
        if (integer == 0) {
            rodaImpianNew.setScreen(new BombeiroScreen(rodaImpianNew, currentGui));
        } else {

            showQuestions();
            correctLabel = new CorrectLabel(StringRes.CHOOSE5CONS, skin);
            stage.addActor(correctLabel);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    playerMenu.setBonusMode(true);
                    playerMenu.createConsonantsTable();
                }
            }, 4f);
        }
    }

    public void setUpNewRound() {
        if (!tileBases.isEmpty()) {
            for (TileBase t : tileBases) {
                t.remove();
            }
        }
        tileBases.clear();
        currentQuestion = rodaImpianNew.getPreparedQuestions().get(gameRound);
        if (gameRound <= 2) {
            setSubject(StringRes.ROUND + (gameRound + 1));
        } else {
            setSubject(StringRes.BONUSROUND);
        }
        setTile();
    }


    public void setTile() {
        float initialY;
        float initialX;

        if (currentQuestion.getTotalline() <= 2) {
            initialY = 1401f;
            initialX = 37f;
            if (currentQuestion.getLine1() != null) {
                answerString = currentQuestion.getLine1();
                addTileBase(currentQuestion.getLine1(), 14, initialY, initialX);
            }
            if (currentQuestion.getLine2() != null) {
                answerString += (" " + currentQuestion.getLine2());
                addTileBase(currentQuestion.getLine2(), 14, initialY - 78f - 2, 37f);
            }
        } else {
            initialX = 96f;
            initialY = 1481f;
            if (currentQuestion.getLine1() != null) {
                answerString = currentQuestion.getLine1();
                addTileBase(currentQuestion.getLine1(), 12, initialY, initialX);
            }
            if (currentQuestion.getLine2() != null) {
                answerString += (" " + currentQuestion.getLine2());
                addTileBase(currentQuestion.getLine2(), 14, initialY - 78f - 2, 37f);
            }
            if (currentQuestion.getLine3() != null) {
                answerString += (" " + currentQuestion.getLine3());
                addTileBase(currentQuestion.getLine3(), 14, initialY - 78f * 2 - 4, 37f);
            }
            if (currentQuestion.getLine4() != null) {
                answerString += (" " + currentQuestion.getLine4());
                addTileBase(currentQuestion.getLine4(), 12, initialY - 78f * 3 - 6, 96f);
            }
        }


    }

    private void addTileBase(String questionLine, int lineMaxLength, float positionY, float positionX) {
        if (lineMaxLength > 12) {
            if ((lineMaxLength - questionLine.length()) >= 1) {
                positionX += 59f;
            }
        }
        if (lineMaxLength == 12 && currentQuestion.getTotalline() <= 2) {
            if ((lineMaxLength - questionLine.length()) >= 1) {
                positionX += 59f;
            }
        }
        for (int i = 0; i < questionLine.length(); i++) {
            if (questionLine.charAt(i) != ' ') {
                TileBase tileBase = new TileBase(atlas, String.valueOf(questionLine.charAt(i)));
                tileBase.setPosition(positionX + 57 * i + 2 * i, positionY);
                tileBases.add(tileBase);
            }

        }
    }

    public void addPlayers() {
        PlayerGuis playerOneGuis = setHumanGui(rodaImpianNew.getPlayer());
        testGui = playerOneGuis;
        PlayerGuis playerTwoGuis;
        PlayerGuis playerThreeGuis;
        if (rodaImpianNew.getPlayerTwo() != null) {
            playerTwoGuis = setHumanGui(rodaImpianNew.getPlayerTwo());
        } else {
            playerTwoGuis = cpuFactory.createCpu(skin);
        }

        if (rodaImpianNew.getPlayerThree() != null) {
            playerThreeGuis = setHumanGui(rodaImpianNew.getPlayerThree());
        } else {
            playerThreeGuis = cpuFactory.createCpu(skin);
        }

        playerGuis.add(playerOneGuis);
        playerGuis.add(playerTwoGuis);
        playerGuis.add(playerThreeGuis);
        playerGuis.shuffle();

        for (PlayerGuis p : playerGuis) {
            p.getPlayerNew().setScore(0);
            p.getScoreLabel().setText("$" + p.getPlayerNew().getScore());
            p.getPlayerNew().setFullScore(0);
            p.updateFullScore(p.getPlayerNew().getFullScore());
            p.getPlayerNew().setAnimateScore(0);
        }

        wheelTurn();
    }

    public void wheelTurn() {
        final WheelTurns wheelTurns = new WheelTurns(atlas.findRegion("wheelturn"),
                atlas.findRegion("pointer"), playerGuis, this);
        stage.addActor(wheelTurns);
        gameSound.playCheer();

        for (int i = 0; i < playerGuis.size; i++) {
            playerGuis.get(i).setPlayerIndex(i);
            playerGuis.get(i).setChatBubble(new ChatBubble(i, stage, skin));
            stage.addActor(playerGuis.get(i).getProfilePic());
        }
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                wheelTurns.spin();
            }
        }, 2f);
    }

    public void animatePicture() {
        for (int i = 0; i < playerGuis.size; i++) {
            playerGuis.get(i).animateShowBoard();
            tilesGroup.addActor(playerGuis.get(i).getScoreLabel());
            tilesGroup.addActor(playerGuis.get(i).getFulLScoreLabel());
            //tilesGroup.addActor(playerGuis.get(i).getFreeTurn());
            tilesGroup.addActor(playerGuis.get(i).getNameLabel());
        }
    }

    public PlayerGuis setHumanGui(PlayerNew playerNew) {
        PlayerGuis playerGuis = new PlayerGuis();
        playerGuis.setPlayerNew(playerNew);
        playerGuis.setNameLabel(new Label(playerNew.getName().toUpperCase(), skin, "name"));
        playerGuis.setProfilePic(new ProfilePic(atlas.findRegion("defaultavatar"), playerNew.getPicUri()));
        playerGuis.setScoreLabel(new Label("$" + playerNew.getScore(), skin, "score"));
        playerGuis.setFulLScoreLabel(new Label("$" + playerNew.getFullScore(), skin, "arial36"));
        playerGuis.setFreeTurn(new Label(StringRes.FREETURN, skin, "free"));
        return playerGuis;
    }

    public void showQuestions() {
        gameSound.playCorrect();
        for (TileBase tileBase : tileBases) {
            tileBase.addAction(
                    new ParallelAction(
                            new SequenceAction(Actions.fadeOut(0),
                                    Actions.fadeIn(1f)),
                            new SequenceAction(Actions.color(Color.RED, 1f), Actions.color(new Color(1f, 1f, 1f, 1f), 1f)))
            );
            tilesGroup.addActor(tileBase);
        }
        setSubject(currentQuestion.getSubject());
    }

    public void startRound() {
        vannaHost.relax();
        showQuestions();
        playerMenu = new PlayerMenu(stage, this,skin);
        startTurn();
    }

    public void startTurn() {
        vannaHost.increaseCount();
        for (PlayerGuis playerGui : playerGuis) {
            if (playerGui.getPlayerNew().isTurn()) {
                currentIndex = playerGui.getPlayerIndex();
            }
        }
        currentGui = playerGuis.get(currentIndex);
        activePlayer = currentGui.getPlayerNew();
        hourGlass.changePos(currentGui);
        stage.addActor(hourGlass);
        if (activePlayer.isAi()) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    cpuMove();
                }
            }, 2f);
        } else {
            showPlayerMenu();
        }
    }

    private void cpuMove() {
        aiMoves = new AiMoves(this);
        aiMoves.execute(tileBases, playerMenu, activePlayer, currentGui);
    }


    public void changeTurn() {
        currentIndex = (currentIndex + 1) % playerGuis.size;
        for (PlayerGuis playerGui : playerGuis) {
            playerGui.getPlayerNew().setTurn(false);
        }
        playerGuis.get(currentIndex).getPlayerNew().setTurn(true);
        startTurn();
    }

    private void setSubject(String text) {
        subjectLabel.setText(text);
        subjectLabel.pack();
        subjectLabel.setPosition(450f - subjectLabel.getWidth() / 2f, 888f);
    }

    public void showPlayerMenu() {
        playerMenu.show();
    }

    public void spinWheel(boolean cpuMoves, boolean bonus) {
        rodaImpianNew.setScreen(new SpinScreen(rodaImpianNew, cpuMoves, bonus));
    }


    public void checkBonusChar(String c) {
        boolean correct = false;
        for (TileBase tileBase : tileBases) {
            if (tileBase.getLetter().contains(c)) {
                tileBase.reveal();
                correct = true;
            }
        }
        if (correct) {
            vannaHost.correct();
            gameSound.playCorrect();
        } else {
            vannaHost.wrong();
            gameSound.playWrong();
        }
    }

    public void checkAnswer(Character character) {
        playerMenu.removeLetter(character);
        boolean correct = false;
        int multiplier = 0;
        String c = String.valueOf(character);

        for (TileBase tileBase : tileBases) {
            if (tileBase.getLetter().equals(c)) {
                tileBase.reveal();
                multiplier++;
                correct = true;
            }
        }

        if (correct) {
            vannaHost.correct();
            gameSound.playCorrect();
            if (rodaImpianNew.getWheelParams().getScores() > 0) {
                int correctScores = (multiplier * rodaImpianNew.getWheelParams().getScores());
                int scores = activePlayer.getScore() + correctScores;
                activePlayer.setScore(scores);
                if (correctScores >= 2500) {
                    gameSound.playCheer();
                    vannaHost.dance();
                }
                correctLabel = new CorrectLabel("$" + rodaImpianNew.getWheelParams().getScores() + " x " + multiplier, skin);
                if (rodaImpianNew.getWheelParams().getScoreStrings().equals(StringRes.FREETURN)) {
                    stage.addActor(currentGui.createFreeTurn(skin));
                    currentGui.setFree(true);
                }
                if (gifts.isPrepareGift()) {
                    gameSound.playCheer();
                    gifts.winGifts(giftsNew.getGiftIndex(), currentGui, stage);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            gifts.setPrepareGift(false);
                        }
                    }, 2f);

                }
                stage.addActor(correctLabel);
            }
            checkIfComplete();
        } else {
            vannaHost.wrong();
            if (gifts.isPrepareGift()) {
                gifts.setPrepareGift(false);
            }
            gameSound.playWrong();
            if (currentGui.isFree()) {
                currentGui.setFree(false);
                startTurn();
            } else {
                changeTurn();
            }

        }

        rodaImpianNew.getWheelParams().setScores(0);
    }

    private boolean completenessCheck() {
        boolean complete = true;
        for (TileBase t : tileBases) {
            if (!t.isRevealed()) {
                complete = false;
            }
        }
        return complete;
    }

    private void checkIfComplete() {

        if (completenessCheck()) {
            finishGame();
        } else {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (!activePlayer.isAi()) {
                        playerMenu.show();
                    } else {
                        cpuMove();
                    }
                }
            }, 2f);
        }
    }

    public void checkBonusString(String bonusHolder) {
        final Label label = new Label(bonusHolder, skin, "spin");
        label.pack();
        label.setPosition(450f - label.getWidth() / 2f, 800f);
        bonusStringHolder = new StringBuilder(bonusHolder);
        stage.addActor(label);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (bonusStringHolder.length() > 0) {
                    checkBonusChar(String.valueOf(bonusStringHolder.charAt(0)));
                    bonusStringHolder.deleteCharAt(0);
                    label.setText(bonusStringHolder.toString());
                    label.pack();
                    label.setPosition(450f - label.getWidth() / 2f, 800f);
                } else {
                    label.remove();
                    if (completenessCheck()) {
                        showWinner();
                    } else {
                        try {
                            completePuzzle();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, 2f, 2f, bonusStringHolder.length());
    }


    public boolean completePuzzle() throws CloneNotSupportedException {
        answerHolder = "";
        incompleteTiles.clear();
        for (TileBase t : tileBases) {
            if (!t.isRevealed()) {
                answerHolder += t.getLetter();
                incompleteTiles.add((TileBase) t.clone());
            }
        }
        if (!incompleteTiles.isEmpty() && answerHolder != null) {
            for (TileBase t : incompleteTiles) {
                incompleteGroup.addActor(t);
                t.setLetter("*");
            }
            Gdx.input.setOnscreenKeyboardVisible(true);
            playerMenu.showCompleteMenu();
            keyListen = new KeyListen(incompleteTiles);
            stage.addListener(keyListen);
//            logger.debug("Complete " + answerHolder);
            return true;
        }
        return false;
    }


    public void checkCompleteAnswer() throws CloneNotSupportedException {
        boolean correct = false;
        String compareAnswer = "";
        Gdx.input.setOnscreenKeyboardVisible(false);
        if (keyListen != null) {
            stage.removeListener(keyListen);
        }

        if (!incompleteTiles.isEmpty() && answerHolder != null) {
            playerMenu.hideComplete();
            for (TileBase t : incompleteTiles) {
                t.setColor(GameConfig.NORMAL_COLOR);
                compareAnswer += t.getLetter();
            }
            answerHolder = answerHolder.toLowerCase();
            compareAnswer = compareAnswer.toLowerCase();

            if (answerHolder.equals(compareAnswer)) {
                correct = true;
            }
            if (playerMenu.isBonusMode()) {
                String dialogString = StringRes.LOSEBONUS;
                if (correct) {
                    stage.addActor(new Fireworks(rodaImpianNew.getAssetManager().get(AssetDesc.WINANIMATION)));
                    dialogString = StringRes.WINBONUS + rodaImpianNew.getWheelParams().getScoreStrings();
                    if (bonusGiftImg != null) {
                        currentGui.getBonusWon().add(bonusGiftImg.getBonusIndex());
                    }
                    gameSound.playWinSound();
                    gameSound.playCheer();
                    vannaHost.dance();
                    currentGui.addBonus(bonusGiftImg.getBonusIndex());
                } else {
                    gameSound.playAww();
                    vannaHost.wrong();
                }

                ErrDiag errDiag = new ErrDiag(dialogString, skin) {
                    @Override
                    protected void result(Object object) {
                        showWinner();
                        hide();
                        super.result(object);
                    }
                };
                errDiag.show(stage);
                return;
            }
            if (correct) {
                finishGame();
            } else {
                answerHolder = "";
                incompleteGroup.clear();
                incompleteTiles.clear();
                gameSound.playWrong();
                changeTurn();
            }
        } else {
            completePuzzle();
        }

    }

    public void finishGame() {
        gameSound.playCheer();
        vannaHost.dance();
        if (!incompleteTiles.isEmpty()) {
            incompleteGroup.clear();
            incompleteTiles.clear();
        }
        for (TileBase t : tileBases) {
            if (!t.isRevealed()) {
                t.reveal();
            }
        }
        stage.addActor(new Fireworks(rodaImpianNew.getAssetManager().get(AssetDesc.WINANIMATION)));
        stage.addActor(new Confetti(rodaImpianNew.getAssetManager().get(AssetDesc.CONFETTI), currentIndex));
        gameSound.playWinSound();

        currentGui.getProfilePic().addAction(Actions.moveTo(350f, 1150f, 1.5f));
        activePlayer.setFullScore(activePlayer.getFullScore() + activePlayer.getScore() + 200 * (gameRound + 1));
        currentGui.updateFullScore(activePlayer.getFullScore());
        increaseGameRound();

    }

    private void increaseGameRound() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                currentGui.getProfilePic().addAction(Actions.moveTo(currentGui.getPosition().x, currentGui.getPosition().y, 1f));
                gameRound++;
                if (gameRound != 3) {
                    continuePlay();
                } else {
                    List<Integer> allScore = new ArrayList<>();
                    for (PlayerGuis p : playerGuis) {
                        allScore.add(p.getPlayerNew().getFullScore());
                    }
                    int highest = Collections.max(allScore);

                    for (PlayerGuis p : playerGuis) {
                        if (p.getPlayerNew().getFullScore() == highest) {
                            activePlayer = p.getPlayerNew();
                            currentGui = p;
                        }
                    }

                    showTrophy();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if (activePlayer.isAi()) {
                                showWinner();
                            } else {
                                spinBonusRound();
                            }
                        }
                    }, 2f);

                }


            }

            private void continuePlay() {
                setUpNewRound();
                for (PlayerGuis p : playerGuis) {
                    p.getPlayerNew().setScore(0);
                    p.setFree(false);
                }
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {

                        startRound();
                    }
                }, 2f);

            }
        }, 4f);
    }

    private void spinBonusRound() {
        rodaImpianNew.setBonusMode(true);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (PlayerGuis p : playerGuis) {
                    if (!p.equals(currentGui)) {
                        p.getProfilePic().remove();
                    }
                }
                spinWheel(false, true);
            }
        }, 2f);


    }


    public void showConsonants() {

        if (rodaImpianNew.isBonusMode()) {
            vannaHost.relax();
            prepareEnvelope();
            return;
        }
        if (rodaImpianNew.getWheelParams().getScores() > 0) {
            vannaHost.waitingAnswer();
            if (rodaImpianNew.getWheelParams().getScoreStrings().equals(StringRes.GIFT)) {
                gifts.prepareGift(stage);
                giftsIndexes.shuffle();
                giftsNew.setGiftIndex(giftsIndexes.get(0));
                giftsIndexes.removeIndex(0);
                rodaImpianNew.getWheelParams().setScores(
                        gifts.getGiftValue(giftsNew.getGiftIndex())
                );
                ///sendObject giftIndexNew
            }

            if (activePlayer.isAi()) {
                aiMoves.chooseConsonants();
            } else {
                playerMenu.createConsonantsTable();
            }

        } else {
            vannaHost.wrong();
            if (rodaImpianNew.getWheelParams().getScoreStrings().equals(StringRes.BANKRUPT)) {
                stage.addActor(new FlyingMoney(atlas.findRegion("3_badgebankrupt"), currentGui.getPosition()));
                if (activePlayer.equals(rodaImpianNew.getPlayer())) {
                    rodaImpianNew.getPlayer().setBankrupt(rodaImpianNew.getPlayer().getBankrupt() + 1);
                }
                activePlayer.setScore(0);
                if (currentGui.isFree()) {
                    currentGui.setFree(false);
                }
            }
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    changeTurn();
                }
            }, 1f);
        }
    }


    private void showWinner() {
        WinnerDialog winnerDialog = new WinnerDialog(skin, currentGui, atlas, rodaImpianNew);
        winnerDialog.show(stage);
    }

    private void showTrophy() {
        hourGlass.remove();
        stage.addActor(new TrophyNew(currentGui.getPosition(), atlas.findRegion("trophy")));
    }


    public PlayerNew getActivePlayer() {
        return activePlayer;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void mainMenu(){
        rodaImpianNew.restart();
    }

    public void update(float delta) {
        for (PlayerGuis playerGui : playerGuis) {
            playerGui.update(delta);
        }
    }

}
