package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.actors.ErrDiag;
import com.somboi.rodaimpian.gdxnew.actors.RoomTable;
import com.somboi.rodaimpian.gdxnew.actors.SmallButton;
import com.somboi.rodaimpian.gdxnew.actors.StatusLabel;
import com.somboi.rodaimpian.gdxnew.actors.YesNoDiag;
import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.games.OnlineGame;
import com.somboi.rodaimpian.gdxnew.interfaces.OnInterface;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ApplyForce;
import com.somboi.rodaimpian.gdxnew.onlineclasses.BonusStringHolder;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ChangeTurn;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ChatOnline;
import com.somboi.rodaimpian.gdxnew.onlineclasses.CheckAnswer;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ChooseSpin;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ChooseVocal;
import com.somboi.rodaimpian.gdxnew.onlineclasses.CompleteAnswer;
import com.somboi.rodaimpian.gdxnew.onlineclasses.Disconnect;
import com.somboi.rodaimpian.gdxnew.onlineclasses.FinishGame;
import com.somboi.rodaimpian.gdxnew.onlineclasses.GameState;
import com.somboi.rodaimpian.gdxnew.onlineclasses.GiftsNew;
import com.somboi.rodaimpian.gdxnew.onlineclasses.KickPlayer;
import com.somboi.rodaimpian.gdxnew.onlineclasses.NetWork;
import com.somboi.rodaimpian.gdxnew.onlineclasses.PlayerStates;
import com.somboi.rodaimpian.gdxnew.onlineclasses.RoomLists;
import com.somboi.rodaimpian.gdxnew.onlineclasses.RoomSession;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ShowMenu;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ShowWinner;
import com.somboi.rodaimpian.gdxnew.onlineclasses.StartTurn;
import com.somboi.rodaimpian.gdxnew.onlineclasses.WheelBodyAngle;
import com.somboi.rodaimpian.gdxnew.wheels.WheelParams;

import java.io.IOException;
import java.util.ArrayList;

public class OnlineScreen extends BaseScreenNew implements OnInterface {
    private final Table onlineMenu = new Table();
    private final Client client = new Client(1000000, 1000000);
    private final OnlineGame onlineGame;
    private final StatusLabel statusLabel;
    private final Table roomTableScroll = new Table();
    private boolean host;
    private final Group sessionGroup = new Group();

    public OnlineScreen(RodaImpianNew rodaImpianNew) {
        super(rodaImpianNew);
        statusLabel = new StatusLabel(skin);
        statusLabel.updateText(StringRes.CONNECTING);
        actorFactory.createGameBgBlur(rodaImpianNew.isGoldTheme());
        onlineGame = new OnlineGame(stage, rodaImpianNew, this);
        stage.addActor(sessionGroup);
        stage.addActor(statusLabel);
        createMenu();
        setUpClient();
        //Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    private void setUpClient() {
        client.start();
        NetWork.register(client);
        client.addListener(new Clistener());
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    client.connect(6001, StringRes.RODARODA, 6001);
                    client.setTimeout(20000);
                } catch (IOException e) {
                    statusLabel.updateText(StringRes.FAILSERVER);
                }
            }
        });
    }

    @Override
    public void update(float delta) {
        onlineGame.update(delta);
    }

    private void createMenu() {
        SmallButton chat = new SmallButton(StringRes.CHAT, skin);
        SmallButton createRoom = new SmallButton(StringRes.CREATEROOM, skin);
        createRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (client.isConnected()) {
                    createRoomSession();
                }
            }
        });
        onlineMenu.defaults().pad(15f);
        onlineMenu.add(chat);
        onlineMenu.add(createRoom);
        onlineMenu.pack();
        onlineMenu.setPosition(450f - onlineMenu.getWidth() / 2f, 80f);
        stage.addActor(onlineMenu);
    }

    private void createRoomSession() {
        //  logger.debug("create room");
        host = true;
        RoomSession roomSession = new RoomSession();
        roomSession.setPlayerList(new ArrayList<>());
        roomSession.setQuestionNews(new ArrayList<>());
        roomSession.setRoomUri(rodaImpianNew.getPlayer().getPicUri());
        roomSession.setRoomID(rodaImpianNew.getPlayer().getUid());
        for (QuestionNew questionNew : rodaImpianNew.getPreparedQuestions()) {
            roomSession.getQuestionNews().add(questionNew);
        }
        client.sendTCP(roomSession);
    }


    private class Clistener extends Listener {
        @Override
        public void connected(Connection connection) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    statusConnected(connection);
                }
            });

        }

        @Override
        public void disconnected(Connection connection) {
            Disconnect disconnect = new Disconnect();
            disconnect.setPlayerId(rodaImpianNew.getPlayer().getUid());
            client.sendTCP(disconnect);
            sendObjects(PlayerStates.DISCONNECT);
            ErrDiag errDiag = new ErrDiag(StringRes.DISCONNECTED, skin) {
                @Override
                public void func() {
                    rodaImpianNew.mainMenu();

                }
            };
            errDiag.show(stage);
        }

        @Override
        public void received(Connection connection, Object o) {

            if (o instanceof ChooseSpin) {
                client.sendTCP(PlayerStates.CHOOSESPIN);
            }
            if (o instanceof FinishGame) {
                client.sendTCP(PlayerStates.FINISHROUND);
            }
            if (o instanceof CompleteAnswer) {
                client.sendTCP(PlayerStates.CHOOSECOMPLETE);
            }
            if (o instanceof ShowWinner) {
                client.sendTCP(PlayerStates.SHOWWINNER);
            }
            if (o instanceof ChangeTurn) {
                client.sendTCP(PlayerStates.CHANGETURN);
            }
            if (o instanceof StartTurn) {
                client.sendTCP(PlayerStates.STARTTURN);
            }
            if (o instanceof ShowMenu) {
                client.sendTCP(PlayerStates.SHOWMENU);
            }
            if (o instanceof RoomLists) {
                RoomLists roomLists = (RoomLists) o;
                if (roomLists != null && !host) {
                    updateRoomList(roomLists);
                }
            }
            if (o instanceof RoomSession) {
                RoomSession roomSession = (RoomSession) o;
                createPlayStage(roomSession);
            }
            if (o instanceof Disconnect) {
                Disconnect disconnect = (Disconnect) o;
                //   logger.debug("Player disconnect ");
                onlineGame.playerDisconnected(disconnect.getPlayerId());
            }
            if (o instanceof GiftsNew) {
                GiftsNew giftsNew = (GiftsNew) o;
                onlineGame.setGiftOnline(giftsNew);
            }


                if (o instanceof BonusStringHolder) {
                BonusStringHolder bonusStringHolder = (BonusStringHolder) o;
                onlineGame.checkBonusString(bonusStringHolder.getBonusStringHolder());
            }

            if (o instanceof GameState) {
                GameState gameState = (GameState) o;

                if (gameState.equals(GameState.HOSTDISCONNECT)) {
                    ErrDiag errDiag = new ErrDiag(StringRes.HOSTLOST, skin) {
                        @Override
                        public void func() {
                            rodaImpianNew.setScreen(new OnlineScreen(rodaImpianNew));
                        }
                    };
                    errDiag.show(stage);
                }

                if (gameState.equals(GameState.SPINSCREEN)) {
                    spinOnline(false);
                }

                if (gameState.equals(GameState.FINISHSPIN)) {
                    rodaImpianNew.backOnlineScreen();
                    sendObjects(PlayerStates.SHOWCONSONANT);
                    rodaImpianNew.getPlayer().setPlayerStates(PlayerStates.SHOWCONSONANT);
                }

                if (gameState.equals(GameState.START)) {
                    statusLabel.remove();
                    onlineGame.startRound();
                }
                if (gameState.equals(GameState.SHOWQUESTIONS)) {
                    onlineGame.showQuestions();
                }
                if (gameState.equals(GameState.STARTURN)) {
                    onlineGame.executeStartTurn();
                }
                if (gameState.equals(GameState.SHOWMENU)) {
                    onlineGame.executeShowPlayerMenu();
                }
                if (gameState.equals(GameState.CHANGETURN)) {
                    onlineGame.executeChangeTurn();
                }
                if (gameState.equals(GameState.PREPAREENVELOPE)) {
                    onlineGame.prepareEnvelope();
                }
                if (gameState.equals(GameState.SHOWCONSONANT)) {
                    onlineGame.showConsonants();
                }
                if (gameState.equals(GameState.SHOWVOCAL)) {
                    onlineGame.vocalKeyboard();
                }
                if (gameState.equals(GameState.FINISHROUND)) {
                    onlineGame.executeFinishGame();
                }
                if (gameState.equals(GameState.SHOWWINNER)) {
                    onlineGame.executeShowWinner();
                }
                if (gameState.equals(GameState.COMPLETE)) {
                    if (isTurn()) {
                        try {
                            if (onlineGame.completePuzzle()) {
                                onlineGame.clearPlayerMenu();
                            }
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            if (o instanceof ChooseVocal) {
                ChooseVocal chooseVocal = (ChooseVocal) o;
                onlineGame.chooseVocal(chooseVocal);
            }
            if (o instanceof CheckAnswer) {
                CheckAnswer checkAnswer = (CheckAnswer) o;
                onlineGame.checkAnswer(checkAnswer.getCharacter());
            }

            if (o instanceof KickPlayer) {
                KickPlayer kickPlayer = (KickPlayer) o;
                rodaImpianNew.getBannedRoom().add(kickPlayer.getRoomId());
                ErrDiag errDiag = new ErrDiag(StringRes.YOUBEENKICK, skin) {
                    @Override
                    public void func() {
                        rodaImpianNew.setScreen(new OnlineScreen(rodaImpianNew));
                    }
                };
                errDiag.show(stage);
            }
            if (o instanceof ChatOnline) {
                ChatOnline chatOnline = (ChatOnline) o;
                onlineGame.showChatOnline(chatOnline);
            }

            if (o instanceof WheelBodyAngle) {
                if (!isTurn()) {
                    WheelBodyAngle wheelBodyAngle = (WheelBodyAngle) o;
                    rodaImpianNew.getSpinOnline().setWheelBodyAngle(wheelBodyAngle);
                }
            }

            if (o instanceof WheelParams) {
                WheelParams wheelParams = (WheelParams) o;
                rodaImpianNew.getSpinOnline().showResults(wheelParams);
            }
            if (o instanceof ApplyForce) {
                ApplyForce applyForce = (ApplyForce) o;
                rodaImpianNew.forceWheel(applyForce);
            }

        }

        @Override
        public void idle(Connection connection) {
            super.idle(connection);
        }
    }


    private void statusConnected(Connection connection) {
        statusLabel.updateText(StringRes.CONNECTED);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                statusLabel.remove();
            }
        }, 1.5f);
        rodaImpianNew.getPlayer().setConnectionID(connection.getID());
        client.sendTCP(rodaImpianNew.getPlayer());
    }

    @Override
    public void sendObjects(Object o) {
        client.sendTCP(o);
    }

    @Override
    public boolean isHost() {
        return host;
    }

    @Override
    public boolean isBanned(String roomID) {
        return rodaImpianNew.getBannedRoom().contains(roomID, false);
    }

    @Override
    public void updateStatusLabel(String text) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                statusLabel.updateText(text);
                stage.addActor(statusLabel);
            }
        });

    }

    @Override
    public boolean isTurn() {
        return onlineGame.isTurn();
    }

    public void spinOnline(boolean bonus) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                rodaImpianNew.spinOnline(bonus, OnlineScreen.this);
            }
        });

    }

    private void createPlayStage(RoomSession roomSession) {
        onlineGame.setQuestionNewList(roomSession.getQuestionNews());
        sessionGroup.clear();
        sessionGroup.remove();
        onlineGame.setRoomSession(roomSession);
        actorFactory.createGameBg(rodaImpianNew.isGoldTheme());
        actorFactory.createGameTables();
        onlineGame.addPlayers();
    }

    private void updateRoomList(RoomLists roomLists) {
        sessionGroup.clear();
        roomTableScroll.clear();
        Table content = new Table();
        if (!roomLists.getList().isEmpty()) {

            for (RoomSession r : roomLists.getList()) {
                content.add(new RoomTable(skin, atlas.findRegion("defaultavatar"), r,
                        this)).row();
            }
            ScrollPane scrollPane = new ScrollPane(content, skin);
            scrollPane.setScrollingDisabled(true, false);
            roomTableScroll.add(scrollPane);
            roomTableScroll.setSize(890f, 1500);
            roomTableScroll.setPosition(0, 90f);
            sessionGroup.addActor(roomTableScroll);
        }
    }


    @Override
    public void backKey() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            YesNoDiag yesNoDiag = new YesNoDiag(StringRes.EXIT2 + "?", skin) {
                @Override
                public void yesFunc() {
                    Disconnect disconnect = new Disconnect();
                    disconnect.setPlayerId(rodaImpianNew.getPlayer().getUid());
                    sendObjects(disconnect);
                    sendObjects(PlayerStates.DISCONNECT);
                    client.stop();
                    rodaImpianNew.mainMenu();
                }
            };
            yesNoDiag.show(stage);

        }
    }

    @Override
    public void dispose() {
        super.dispose();
        Disconnect disconnect = new Disconnect();
        disconnect.setPlayerId(rodaImpianNew.getPlayer().getUid());
        client.sendTCP(disconnect);
        sendObjects(PlayerStates.DISCONNECT);

    }
}
