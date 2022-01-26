package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdxnew.FlyingMoney;
import com.somboi.rodaimpian.gdxnew.actors.Bonuses;
import com.somboi.rodaimpian.gdxnew.actors.ChatBubble;
import com.somboi.rodaimpian.gdxnew.actors.Confetti;
import com.somboi.rodaimpian.gdxnew.actors.CorrectLabel;
import com.somboi.rodaimpian.gdxnew.actors.ErrDiag;
import com.somboi.rodaimpian.gdxnew.actors.Fireworks;
import com.somboi.rodaimpian.gdxnew.actors.PlayerGuis;
import com.somboi.rodaimpian.gdxnew.actors.PlayerMenu;
import com.somboi.rodaimpian.gdxnew.actors.SmallButton;
import com.somboi.rodaimpian.gdxnew.actors.Sparkling;
import com.somboi.rodaimpian.gdxnew.actors.TileBase;
import com.somboi.rodaimpian.gdxnew.actors.UltraSmallBtn;
import com.somboi.rodaimpian.gdxnew.actors.WinnerDialog;
import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.interfaces.OnInterface;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ChangeTurn;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ChatOnline;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ChooseVocal;
import com.somboi.rodaimpian.gdxnew.onlineclasses.CompleteAnswer;
import com.somboi.rodaimpian.gdxnew.onlineclasses.FinishGame;
import com.somboi.rodaimpian.gdxnew.onlineclasses.GameState;
import com.somboi.rodaimpian.gdxnew.onlineclasses.GiftsNew;
import com.somboi.rodaimpian.gdxnew.onlineclasses.IncreaseRound;
import com.somboi.rodaimpian.gdxnew.onlineclasses.KickPlayer;
import com.somboi.rodaimpian.gdxnew.onlineclasses.LoseBonus;
import com.somboi.rodaimpian.gdxnew.onlineclasses.PlayerStates;
import com.somboi.rodaimpian.gdxnew.onlineclasses.PrepareEnvelope;
import com.somboi.rodaimpian.gdxnew.onlineclasses.RemoveSession;
import com.somboi.rodaimpian.gdxnew.onlineclasses.RoomSession;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ShowMenu;
import com.somboi.rodaimpian.gdxnew.onlineclasses.SpinBonusWheel;
import com.somboi.rodaimpian.gdxnew.onlineclasses.StartTurn;
import com.somboi.rodaimpian.gdxnew.onlineclasses.WinBonus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OnlineGame extends BaseGame {
    private RoomSession roomSession;
    private final OnInterface onInterface;
    private final Group onlineMenuGroup = new Group();
    private List<QuestionNew> questionNewList = new ArrayList<>();

    public OnlineGame(Stage stage, RodaImpianNew rodaImpianNew, OnInterface onInterface) {
        super(stage, rodaImpianNew);
        this.onInterface = onInterface;
        vannaHost.remove();
        incompleteGroup.remove();
    }

    @Override
    public void addPlayers() {
        gameRound = 0;
        setUpNewRound();
        playerGuis.clear();
        tilesGroup.clear();
        tilesGroup.remove();
        onlineMenuGroup.clear();
        onlineMenuGroup.remove();
        stage.addActor(tilesGroup);
        stage.addActor(onlineMenuGroup);
        for (PlayerNew playerNew : roomSession.getPlayerList()) {
            playerGuis.add(setHumanGui(playerNew, 1));
        }

        for (PlayerGuis p : playerGuis) {
            if (p.getPlayerNew().getUid().equals(rodaImpianNew.getPlayer().getUid())) {
                selfGui = p;
            }
            p.getPlayerNew().setScore(0);
            p.getPlayerNew().setTurn(false);
            p.getScoreLabel().setText("$" + p.getPlayerNew().getScore());
            p.getPlayerNew().setFullScore(0);
            p.updateFullScore(p.getPlayerNew().getFullScore());
            p.getPlayerNew().setAnimateScore(0);
        }

        playerGuis.get(0).getPlayerNew().setTurn(true);
        for (int i = 0; i < playerGuis.size; i++) {
            PlayerGuis pgui = playerGuis.get(i);
            pgui.setPlayerIndex(i);
            pgui.setChatBubble(new ChatBubble(i, stage, skin));
            pgui.setPicFixPos();
            pgui.setChatBubble(new ChatBubble(i, stage, skin));
            stage.addActor(pgui.getProfilePic());
            if (i > 0 && onInterface.isHost()) {
                UltraSmallBtn kickBtn = new UltraSmallBtn(StringRes.KICK, skin);
                kickBtn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        KickPlayer kickPlayer = new KickPlayer();
                        kickPlayer.setRoomId(rodaImpianNew.getPlayer().getUid());
                        kickPlayer.setKickedPlayerID(pgui.getPlayerNew().getUid());
                        onInterface.sendObjects(kickPlayer);
                    }
                });
                kickBtn.setPosition(pgui.getPosition().x, pgui.getPosition().y + 255f);
                onlineMenuGroup.addActor(kickBtn);
            }
            tilesGroup.addActor(pgui.getScoreLabel());
            tilesGroup.addActor(pgui.getFulLScoreLabel());
            //tilesGroup.addActor(playerGuis.get(i).getFreeTurn());
            tilesGroup.addActor(pgui.getNameLabel());
        }

        if (playerGuis.size < 2) {
            onInterface.updateStatusLabel(StringRes.WAITINGENTRY);
        } else {
            onInterface.updateStatusLabel(StringRes.WAITINGHOST);
            if (onInterface.isHost()) {
                createOnlineMenu();
            }
        }
        UltraSmallBtn chat = new UltraSmallBtn(StringRes.CHAT, skin);
        chat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpianNew.chatOnline(onInterface, selfGui.getPlayerIndex());
            }
        });
        chat.setPosition(selfGui.getPosition().x, 310f);
        stage.addActor(chat);
        rodaImpianNew.getWheelParams().setAngle(0);
    }

    //startQuestion
    @Override
    public void startRound() {
        subjectLabel.remove();
        stage.addActor(incompleteGroup);
        stage.addActor(vannaHost);
        stage.addActor(subjectLabel);
        vannaHost.dance();
        playerMenu = new PlayerMenu(stage, this, skin);
        onInterface.sendObjects(PlayerStates.SHOWQUESTIONS);
        //    startTurn();
    }

    @Override
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
                if (correct) {
                    sendObject(new WinBonus());
                } else {
                    sendObject(new LoseBonus());
                }
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
            if (isTurn()) {
                sendObject(new CompleteAnswer());
            }
        }
    }


    public void executeBonusWin() {
        stage.addActor(new Fireworks(rodaImpianNew.getAssetManager().get(AssetDesc.WINANIMATION)));
        String dialogString = activePlayer.getName() + ", " + StringRes.WINBONUS + rodaImpianNew.getWheelParams().getScoreStrings();
        if (bonusGiftImg != null) {
            currentGui.getBonusWon().add(bonusGiftImg.getBonusIndex());
        }
        gameSound.playWinSound();
        gameSound.playCheer();
        vannaHost.dance();
        currentGui.addBonus(bonusGiftImg.getBonusIndex());
        showFinisDialog(dialogString);
    }

    public void executeLoseBonus() {
        String dialogString = activePlayer.getName() + ", " + StringRes.LOSEBONUS;
        gameSound.playAww();
        vannaHost.wrong();
        showFinisDialog(dialogString);
    }

    private void showFinisDialog(String dialogString) {
        ErrDiag errDiag = new ErrDiag(dialogString, skin) {
            @Override
            protected void result(Object object) {
                showWinner();
                hide();
                super.result(object);
            }
        };
        errDiag.show(stage);
    }

    @Override
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
                            if (isTurn()) {
                                completePuzzle();
                            }
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, 2f, 2f, bonusStringHolder.length());
    }

    @Override
    public void finishGame() {
        if (isTurn()) {
            sendObject(new FinishGame());
        }
    }

    @Override
    public void showWinner() {
        WinnerDialog winnerDialog = new WinnerDialog(skin, currentGui, atlas, rodaImpianNew);
        winnerDialog.show(stage);
        RemoveSession removeSession = new RemoveSession();
        removeSession.setUid(rodaImpianNew.getPlayer().getUid());
        sendObject(removeSession);
    }

    @Override
    public void update(float delta) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (PlayerGuis playerGui : playerGuis) {
                    playerGui.update(delta);
                }
            }
        });

    }


    public void executeFinishGame() {
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
        if (isTurn()) {
            sendObject(new IncreaseRound());
        }
    }


    @Override
    public void showQuestions() {
        super.showQuestions();
        if (isTurn()) {
            onInterface.sendObjects(new StartTurn());
        }
    }

    @Override
    public void showPlayerMenu() {
        if (isTurn()) {
            onInterface.sendObjects(new ShowMenu());
        }
    }

    public void executeShowPlayerMenu() {
        if (isTurn()) {
            playerMenu.show();
        }
    }

    @Override
    public QuestionNew getCurrentQuestion() {
        return questionNewList.get(gameRound);
    }

    public void createOnlineMenu() {
        final Table onlineMenuTable = new Table();
        SmallButton start = new SmallButton(StringRes.START, skin);
        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onlineMenuGroup.remove();
                onInterface.sendObjects(GameState.OCCUPIED);
                //onInterface.sendObjects(GameState.START);
            }
        });
        onlineMenuTable.add(start);
        onlineMenuTable.pack();
        onlineMenuTable.setPosition(450f - onlineMenuTable.getWidth() / 2f, 750f);
        onlineMenuGroup.addActor(onlineMenuTable);
    }

    public void showChatOnline(ChatOnline chatOnline) {
        playerGuis.get(chatOnline.getGuiIndex()).chat(chatOnline.getText());
    }

    public void playerDisconnected(String uid) {
        for (PlayerGuis p : playerGuis) {
            if (p.getPlayerNew().getUid().equals(uid)) {
                p.getProfilePic().setColor(Color.BLUE);
                p.getPlayerNew().setDisconnect(true);
            }
        }

    }

    public void setQuestionNewList(List<QuestionNew> questionNewList) {
        this.questionNewList = questionNewList;
    }

    public void setRoomSession(RoomSession roomSession) {
        this.roomSession = roomSession;
    }


    @Override
    public void showConsonants() {
        if (rodaImpianNew.isBonusMode()) {
            vannaHost.relax();
            if (onInterface.isTurn()) {
                onInterface.sendObjects(new PrepareEnvelope());
            }
            return;
        }

        if (rodaImpianNew.getWheelParams().getScores() > 0) {
            vannaHost.waitingAnswer();
            if (rodaImpianNew.getWheelParams().getScoreStrings().equals(StringRes.GIFT)) {
                gifts.prepareGift(stage);
                giftsIndexes.shuffle();
                if (isTurn()) {
                    GiftsNew giftsNew = new GiftsNew();
                    giftsNew.setGiftIndex(giftsIndexes.get(0));
                    onInterface.sendObjects(giftsNew);
                }
                //setGiftOnline(giftsNew);
                return;
            }
            showKeyboardConsonant();
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
            changeTurn();
        }
    }

    @Override
    public void increaseGameRound() {
        gameRound++;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                currentGui.getProfilePic().addAction(Actions.moveTo(currentGui.getPosition().x, currentGui.getPosition().y, 1f));
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
                            spinBonusRound();

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
                startRound();
            }
        }, 2f);
    }

    @Override
    public void spinBonusRound() {
        rodaImpianNew.setBonusMode(true);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (PlayerGuis p : playerGuis) {
                    if (!p.equals(currentGui)) {
                        p.getProfilePic().remove();
                    }
                }
                if (isTurn()) {
                    sendObject(new SpinBonusWheel());
                }
            }
        }, 2f);
    }

    private void showKeyboardConsonant() {
        if (isTurn()) {
            playerMenu.createConsonantsTable();
        }
    }

    @Override
    public void startTurn() {
        if (isTurn()) {
            onInterface.sendObjects(new StartTurn());
        }
    }

    @Override
    public void changeTurn() {
        if (isTurn()) {
            onInterface.sendObjects(new ChangeTurn());
        }
    }

    public void executeStartTurn() {
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
        showPlayerMenu();

    }

    @Override
    public void checkIfComplete() {
        if (completenessCheck()) {
            finishGame();
        } else {
            showPlayerMenu();
        }
    }

    public void executeChangeTurn() {
        // logger.debug("current Index before" + currentIndex);

        playerMenu.clear();
        currentIndex = (currentIndex + 1) % playerGuis.size;
        if (playerGuis.get(currentIndex).getPlayerNew().isDisconnect()){
            executeChangeTurn();
            return;
        }
        for (PlayerGuis playerGui : playerGuis) {
            playerGui.getPlayerNew().setTurn(false);
        }
        playerGuis.get(currentIndex).getPlayerNew().setTurn(true);
        startTurn();
        // logger.debug("current Index after" + currentIndex);
    }

    public void setGiftOnline(GiftsNew gg) {
        this.giftsNew.setGiftIndex(gg.getGiftIndex());
        rodaImpianNew.getWheelParams().setScores(
                gifts.getGiftValue(giftsNew.getGiftIndex())
        );
        giftsIndexes.removeValue(gg.getGiftIndex(), false);
        if (giftsIndexes.isEmpty()) {
            giftsIndexes = new Array<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23});
            giftsIndexes.shuffle();
        }
        showKeyboardConsonant();
    }


    @Override
    public void prepareEnvelope() {
        logger.debug("prepareEnvelope");
        playerMenu = new PlayerMenu(stage, this, skin);
        gameRound = 3;
        setUpNewRound();
        bonusGiftImg = new Bonuses(atlas, rodaImpianNew.getWheelParams().getBonusIndex());
        bonusGiftImg.setPosition(350f, 1110f);
        stage.addActor(new Sparkling(rodaImpianNew.getAssetManager().get(AssetDesc.SPARKLE)));
        stage.addActor(bonusGiftImg);
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
        correctLabel = new CorrectLabel(StringRes.CHOOSE5CONS, skin);
        stage.addActor(correctLabel);
        playerMenu.setBonusMode(true);
        if (onInterface.isTurn()) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    playerMenu.createConsonantsTable();
                }
            }, 2f);
        }

    }


    @Override
    public void sendObject(Object o) {
        onInterface.sendObjects(o);
    }

    @Override
    public boolean isTurn() {
        for (PlayerGuis p : playerGuis) {
            if (p.getPlayerNew().isTurn()) {
                return p.getPlayerNew().getUid().equals(rodaImpianNew.getPlayer().getUid());
            }
        }
        return false;
    }

    public void chooseVocal(ChooseVocal chooseVocal) {
        playerGuis.get(chooseVocal.getGuiIndex()).getPlayerNew().setScore(
                playerGuis.get(chooseVocal.getGuiIndex()).getPlayerNew().getScore() - 250
        );
        onInterface.sendObjects(PlayerStates.CHOOSEVOCAL);
    }

    public void vocalKeyboard() {
        if (isTurn()) {
            playerMenu.createVocalTable();
        }
    }
}
