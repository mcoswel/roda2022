package com.somboi.rodaimpian.gdxnew.games;

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
import com.somboi.rodaimpian.gdxnew.actors.ChatBubble;
import com.somboi.rodaimpian.gdxnew.actors.CpuFactory;
import com.somboi.rodaimpian.gdxnew.actors.PlayerGuis;
import com.somboi.rodaimpian.gdxnew.actors.PlayerMenu;
import com.somboi.rodaimpian.gdxnew.actors.ProfilePic;
import com.somboi.rodaimpian.gdxnew.actors.TileBase;
import com.somboi.rodaimpian.gdxnew.actors.VannaHost;
import com.somboi.rodaimpian.gdxnew.actors.WheelTurns;
import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.AiMoves;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
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
    protected final PlayerMenu playerMenu;
    protected PlayerGuis currentGui;
    protected AiMoves aiMoves;

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
        currentQuestion = rodaImpianNew.getPreparedQuestions().get(gameRound);
        setTile();
        stage.addActor(vannaHost);
        stage.addActor(subjectLabel);
        stage.addActor(tilesGroup);
    }

    public void setTile() {
        float initialY = 0;
        float initialX = 0;
        if (currentQuestion.getTotalline() <= 2) {
            initialY = 1401f;
            initialX = 37f;
        } else {
            initialX = 96f;
            initialY = 1481f;
        }
        if (currentQuestion.getLine1() != null) {
            addTileBase(currentQuestion.getLine1(), 12, initialY, initialX);
        }
        if (currentQuestion.getLine2() != null) {
            addTileBase(currentQuestion.getLine2(), 14, initialY - 78f - 2, 37f);
        }
        if (currentQuestion.getLine3() != null) {
            addTileBase(currentQuestion.getLine3(), 14, initialY - 78f * 2 - 4, 37f);
        }
        if (currentQuestion.getLine4() != null) {
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
            tilesGroup.addActor(playerGuis.get(i).getFreeTurn());
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
        showQuestions();
        startTurn();
    }

    public void startTurn() {

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
        currentIndex = (currentIndex + 1) % (playerGuis.size - 1);
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
        for (TileBase tileBase : tileBases) {
            if (tileBase.getLetter().contains(c)) {
                tileBase.reveal();
                correct = true;
            }
        }

        if (correct) {
            gameSound.playCorrect();
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

        } else {
            gameSound.playWrong();
        }
    }


    public void showConsonants() {

        if (rodaImpianNew.getWheelParams().getScores() > 0) {
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

    public PlayerNew getActivePlayer() {
        return activePlayer;
    }

    public void update(float delta) {
        for (PlayerGuis playerGui : playerGuis) {
            playerGui.update(delta);
        }
    }

}
