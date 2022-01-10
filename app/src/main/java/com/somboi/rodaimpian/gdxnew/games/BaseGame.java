package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.GameSound;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdxnew.actors.ChatBubble;
import com.somboi.rodaimpian.gdxnew.actors.Confetti;
import com.somboi.rodaimpian.gdxnew.actors.CorrectLabel;
import com.somboi.rodaimpian.gdxnew.actors.CpuFactory;
import com.somboi.rodaimpian.gdxnew.actors.Fireworks;
import com.somboi.rodaimpian.gdxnew.actors.GiftBonuses;
import com.somboi.rodaimpian.gdxnew.actors.HourGlass;
import com.somboi.rodaimpian.gdxnew.actors.PlayerGuis;
import com.somboi.rodaimpian.gdxnew.actors.PlayerMenu;
import com.somboi.rodaimpian.gdxnew.actors.ProfilePic;
import com.somboi.rodaimpian.gdxnew.actors.TileBase;
import com.somboi.rodaimpian.gdxnew.actors.VannaHost;
import com.somboi.rodaimpian.gdxnew.actors.WheelTurns;
import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.AiMoves;
import com.somboi.rodaimpian.gdxnew.entitiesnew.GiftsNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.interfaces.KeyListen;
import com.somboi.rodaimpian.gdxnew.screens.SpinScreen;

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
    protected final PlayerMenu playerMenu;
    protected PlayerGuis currentGui;
    protected AiMoves aiMoves;
    protected CorrectLabel correctLabel;
    protected final HourGlass hourGlass;
    protected String answerString;
    protected final GiftBonuses giftBonuses;
    protected final Array<Integer> giftsIndexes = new Array<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23});
    protected final GiftsNew giftsNew = new GiftsNew();
    protected final Array<TileBase> incompleteTiles = new Array<>();
    protected KeyListen keyListen;
    protected String answerHolder;

    public BaseGame(Stage stage, RodaImpianNew rodaImpianNew) {
        this.stage = stage;
        this.rodaImpianNew = rodaImpianNew;
        this.atlas = rodaImpianNew.getAssetManager().get(AssetDesc.ATLAS);
        this.skin = rodaImpianNew.getAssetManager().get(AssetDesc.NEWSKIN);
        this.cpuFactory = new CpuFactory(atlas);
        this.vannaHost = new VannaHost(rodaImpianNew.getAssetManager().get(AssetDesc.ATLAS));
        this.gameSound = new GameSound(rodaImpianNew.getAssetManager());
        this.subjectLabel = new Label("", skin);
        this.playerMenu = new PlayerMenu(stage, this, skin);
        this.hourGlass = new HourGlass(rodaImpianNew.getAssetManager().get(AssetDesc.HOURGLASS));
        this.giftBonuses = new GiftBonuses(atlas, rodaImpianNew.getAssetManager().get(AssetDesc.SPARKLE));
        this.giftsIndexes.shuffle();
        stage.addActor(subjectLabel);
        stage.addActor(tilesGroup);
        stage.addActor(incompleteGroup);
        stage.addActor(vannaHost);
        setUpNewRound();
    }

    public void setUpNewRound() {
        if (!tileBases.isEmpty()) {
            for (TileBase t : tileBases) {
                t.remove();
            }
        }
        tileBases.clear();
        currentQuestion = rodaImpianNew.getPreparedQuestions().get(gameRound);
        setTile();
    }


    public void setTile() {
        float initialY;
        float initialX;
        if (currentQuestion.getTotalline() <= 2) {
            initialY = 1401f;
            initialX = 37f;
        } else {
            initialX = 96f;
            initialY = 1481f;
        }
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
        setSubject();
    }

    public void startRound() {

        vannaHost.relax();
        showQuestions();
        startTurn();
    }

    public void startTurn() {
        vannaHost.increaseCount();
        hourGlass.remove();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
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
                    cpuMove();
                } else {
                    showPlayerMenu();
                }
            }
        }, 2f);

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

    private void setSubject() {
        subjectLabel.setText(currentQuestion.getSubject());
        subjectLabel.pack();
        subjectLabel.setPosition(450f - subjectLabel.getWidth() / 2f, 888f);
    }

    public void showPlayerMenu() {
        playerMenu.show();
    }

    public void spinWheel(boolean cpuMoves) {
        rodaImpianNew.setScreen(new SpinScreen(rodaImpianNew, cpuMoves));
    }

    public void checkAnswer(String c) {
        boolean correct = false;
        int multiplier = 0;

        for (TileBase tileBase : tileBases) {
            if (tileBase.getLetter().contains(c)) {
                tileBase.reveal();
                multiplier++;
                correct = true;
            }
        }

        if (correct) {
            vannaHost.correct();
            gameSound.playCorrect();
            if (rodaImpianNew.getWheelParams().getScores() > 0) {
                correctLabel = new CorrectLabel("$" + rodaImpianNew.getWheelParams().getScores() + " x " + multiplier, skin);
                if (rodaImpianNew.getWheelParams().getScoreStrings().equals(StringRes.FREETURN)) {
                    stage.addActor(currentGui.getFreeTurn());
                    currentGui.setFree(true);
                }
                if (giftBonuses.isPrepareGift()) {
                    giftBonuses.winGifts(giftsNew.getGiftIndex(), currentGui.getPosition(), stage);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {

                            giftBonuses.setPrepareGift(false);
                        }
                    }, 2f);

                }
                stage.addActor(correctLabel);
            }


            boolean complete = true;
            for (TileBase t : tileBases) {
                if (!t.isRevealed()) {
                    complete = false;
                }
            }

            if (complete) {
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

        } else {
            vannaHost.wrong();
            if (giftBonuses.isPrepareGift()) {
                giftBonuses.setPrepareGift(false);
            }
            gameSound.playWrong();
            if (currentGui.isFree()) {
                currentGui.setFree(false);

                startTurn();
            } else {
                changeTurn();
            }

        }
        playerMenu.removeLetter(c.charAt(0));
        logger.debug("correct score " + (multiplier * rodaImpianNew.getWheelParams().getScores()));
        activePlayer.setScore(activePlayer.getScore() + (multiplier * rodaImpianNew.getWheelParams().getScores()));
        rodaImpianNew.getWheelParams().setScores(0);


    }


    public void showConsonants() {
        vannaHost.waitingAnswer();
        if (rodaImpianNew.getWheelParams().getScores() > 0) {

            if (rodaImpianNew.getWheelParams().getScoreStrings().equals(StringRes.GIFT)) {
                giftBonuses.prepareGift(stage);
                giftsIndexes.shuffle();
                giftsNew.setGiftIndex(giftsIndexes.get(0));
                giftsIndexes.removeIndex(0);
                rodaImpianNew.getWheelParams().setScores(
                        giftBonuses.getGiftValue(giftsNew.getGiftIndex())
                );

                ///sendObject giftIndexNew
            }

            if (activePlayer.isAi()) {
                aiMoves.chooseConsonants();
            } else {
                playerMenu.createConsonantsTable();
            }
        } else {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    changeTurn();
                }
            }, 1f);
        }
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
            logger.debug("Incomplete size " + incompleteTiles.size);
            for (TileBase t : incompleteTiles) {
                incompleteGroup.addActor(t);
                t.setLetter("*");
            }
            Gdx.input.setOnscreenKeyboardVisible(true);
            playerMenu.showCompleteMenu();
            keyListen = new KeyListen(incompleteTiles);
            stage.addListener(keyListen);
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

        currentGui.getProfilePic().addAction(Actions.moveTo(350f, 1200f, 1.5f));

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                currentGui.getProfilePic().addAction(Actions.moveTo(currentGui.getPosition().x, currentGui.getPosition().y, 1f));
                gameRound++;
                setUpNewRound();
                startRound();
            }
        }, 4f);

    }

    public PlayerNew getActivePlayer() {
        return activePlayer;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void update(float delta) {
        for (PlayerGuis playerGui : playerGuis) {
            playerGui.update(delta);
        }
    }

}
