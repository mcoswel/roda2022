package com.somboi.rodaimpian.gdx.online;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.ErrorDialog;
import com.somboi.rodaimpian.gdx.actor.StatusLabel;
import com.somboi.rodaimpian.gdx.actor.YesNoDialog;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.base.BaseScreen;
import com.somboi.rodaimpian.gdx.entities.BgImg;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.WheelParam;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.online.actor.RoomMenu;
import com.somboi.rodaimpian.gdx.online.actor.SessionTable;
import com.somboi.rodaimpian.gdx.online.entities.BonusHolder;
import com.somboi.rodaimpian.gdx.online.entities.BonusIndex;
import com.somboi.rodaimpian.gdx.online.entities.ChatOnline;
import com.somboi.rodaimpian.gdx.online.entities.CheckAnswer;
import com.somboi.rodaimpian.gdx.online.entities.DisconnectPlayer;
import com.somboi.rodaimpian.gdx.online.entities.EnvelopeIndex;
import com.somboi.rodaimpian.gdx.online.entities.GameState;
import com.somboi.rodaimpian.gdx.online.entities.PlayerState;
import com.somboi.rodaimpian.gdx.online.newentities.ClearSession;
import com.somboi.rodaimpian.gdx.online.newentities.CreateSessions;
import com.somboi.rodaimpian.gdx.online.newentities.FinishSpin;
import com.somboi.rodaimpian.gdx.online.newentities.RegisterPlayer;
import com.somboi.rodaimpian.gdx.online.newentities.RemoveSession;
import com.somboi.rodaimpian.gdx.online.newentities.RodaSession;
import com.somboi.rodaimpian.gdx.online.newentities.SetActivePlayer;
import com.somboi.rodaimpian.gdx.screen.WheelScreen;

import java.io.IOException;
import java.util.ArrayList;

public class NewClient {
    private final RodaImpian rodaImpian;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    private boolean isConnected;
    private Client client;
    OnlineScreen onlineScreen;
    private StatusLabel connectionStatus;
    private RodaSession rodaSession;
    private WheelParam wheelParam;

    public NewClient(RodaImpian rodaImpian) {
        rodaImpian.setGameModes(GameModes.ONLINE);
        this.rodaImpian = rodaImpian;
        onlineScreen = new OnlineScreen(rodaImpian);
        client = new Client(1000000, 1000000);
        client.start();
        NewNetwork.register(client);
        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                isConnected = true;
                rodaImpian.getPlayer().conID = connection.getID();
                ClearSession clearSession = new ClearSession();
                clearSession.playerID = rodaImpian.getPlayer().id;
                sendObject(clearSession);
            }

            @Override
            public void received(Connection connection, Object o) {
                if (o instanceof DisconnectPlayer) {
                    Player player = ((DisconnectPlayer) o).player;
                    onlineScreen.newOnline.checkDisconnected(player.id);
                }
                if (o instanceof WheelParam) {
                    wheelParam = (WheelParam) o;
                    onlineScreen.newOnline.setWheelParam(wheelParam);
                }
                if (o instanceof CheckAnswer) {

                    CheckAnswer checkAnswer = (CheckAnswer) o;
                    logger.debug("receive chk answer " + checkAnswer.character);

                    onlineScreen.newOnline.checkChar(checkAnswer.character);
                }

                if (o instanceof SetActivePlayer) {
                    SetActivePlayer setActivePlayer = (SetActivePlayer) o;
                    onlineScreen.newOnline.setActivePlayer(setActivePlayer.index);
                    rodaImpian.gotoOnlineScreen();
                    sendObject(PlayerState.SHOWMENU);
                }
                if (o instanceof FinishSpin) {
                    FinishSpin finishSpin = (FinishSpin) o;
                    //onlineScreen.newOnline.setWheelParam(finishSpin.wheelParam);
                    rodaImpian.getWheelScreen().setWheelParamResults(finishSpin.wheelParam);
                    onlineScreen.newOnline.setWheelParamResults(finishSpin.wheelParam);
                    logger.debug("Gift index online " + finishSpin.wheelParam.giftIndex);
                    if (finishSpin.wheelParam.results.equals(StringRes.GIFT)) {
                        onlineScreen.newOnline.setGiftOnline(finishSpin.wheelParam.giftIndex);
                    }
                    sendObject(PlayerState.SHOWRESULT);
                }
                if (o instanceof PlayerState) {
                    sendObject(o);
                    logger.debug("receive playerstate " + ((PlayerState) o));
                }

                if (o instanceof GameState) {
                    GameState gameState = (GameState) o;
                    if (gameState.equals(GameState.HOSTLOST)) {
                        onlineScreen.hostLost();
                    }
                    if (gameState.equals(GameState.START)) {
                        logger.debug("Start play");
                        onlineScreen.newOnline.startPlays();
                    }
                    if (gameState.equals(GameState.BANKRUPT)) {
                        rodaImpian.gotoOnlineScreen();
                        sendObject(PlayerState.CHANGETURN);
                    }

                    if (gameState.equals(GameState.SHOWMENU)) {
                        onlineScreen.newOnline.showMenu();
                    }
                    if (gameState.equals(GameState.SPIN)) {
                        rodaImpian.spinWheel();
                    }

                    if (gameState.equals(GameState.SHOWVOCAL)) {
                        onlineScreen.newOnline.showVocals();
                    }
                    if (gameState.equals(GameState.ROOMFULL)) {
                        onlineScreen.roomFull();
                    }
                    if (gameState.equals(GameState.KICKOUT)) {
                        onlineScreen.kickedOut();
                    }
                    if (gameState.equals(GameState.SHOWRESULT)) {
                        rodaImpian.getWheelScreen().showResult();
                    }
                    if (gameState.equals(GameState.GOTOMATCH)) {
                        rodaImpian.gotoOnlineScreen();
                    }
                    if (gameState.equals(GameState.REVEALALL)) {
                        rodaImpian.gotoOnlineScreen();
                        onlineScreen.newOnline.reveaAll();
                    }
                    if (gameState.equals(GameState.NEWROUND)) {
                        logger.debug("Increase game ");
                        onlineScreen.newOnline.newRound();
                    }
                }
                if (o instanceof CreateSessions) {
                    CreateSessions createSessions = (CreateSessions) o;
                    if (createSessions.sessionID != null) {
                        onlineScreen.updateSessionList(createSessions);
                    }
                }
                if (o instanceof RemoveSession) {
                    RemoveSession removeSession = (RemoveSession) o;
                    if (removeSession.sessionid != null) {
                        onlineScreen.removeSession(removeSession.sessionid);
                    }
                }

                if (o instanceof RodaSession) {
                    rodaSession = (RodaSession) o;
                    rodaImpian.setQuestionsReady(rodaSession.questionsReady);
                    onlineScreen.updateOwnSession();
                }
                if (o instanceof ChatOnline) {
                    ChatOnline chatOnline = (ChatOnline) o;
                    onlineScreen.newOnline.queueChatOnline(chatOnline);
                    logger.debug("receive chat online");
                }

                if (o instanceof BonusIndex) {
                    BonusIndex bonusIndex = (BonusIndex) o;
                    onlineScreen.newOnline.setBonusOnline(bonusIndex.index);
                    rodaImpian.gotoOnlineScreen();
                    onlineScreen.newOnline.bonusRound();
                }
                if (o instanceof EnvelopeIndex) {
                    EnvelopeIndex envelopeIndex = (EnvelopeIndex) o;
                    onlineScreen.newOnline.openEnvelopes(envelopeIndex);
                }
                if (o instanceof BonusHolder) {
                    BonusHolder bonusHolder = (BonusHolder) o;
                    onlineScreen.newOnline.checkBonusString(bonusHolder.holder);
                }
            }

            @Override
            public void disconnected(Connection connection) {
                logger.debug("Disconnected");
                isConnected = false;
                rodaImpian.gotoOnlineScreen();
                // connectionStatus.setText(StringRes.FAILSERVER);
            }
        });

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    client.connect(5001, "10.1.19.2", Network.port);

                    client.setTimeout(10000);
                } catch (IOException e) {
                    e.printStackTrace();
                    if (connectionStatus != null) {
                        connectionStatus.setText(StringRes.FAILSERVER);
                    }
                }
            }
        });
        rodaImpian.setOnlineScreen(onlineScreen);
        rodaImpian.gotoOnlineScreen();

    }

    public void tryConnect() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    client.connect(5001, "192.168.0.132", Network.port);

                    client.setTimeout(10000);
                } catch (IOException e) {
                    e.printStackTrace();
                    if (connectionStatus != null) {
                        connectionStatus.setText(StringRes.FAILSERVER);
                    }
                }
            }
        });
    }


    public void sendObject(Object o) {
        client.sendTCP(o);
    }

    public void createRoom() {
        RegisterPlayer registerPlayer = new RegisterPlayer();
        registerPlayer.player = rodaImpian.getPlayer();
        registerPlayer.roomID = rodaImpian.getPlayer().id;
        sendObject(registerPlayer);

        CreateSessions createSessions = new CreateSessions();
        createSessions.name = StringRes.ROOM + rodaImpian.getPlayer().name;
        createSessions.picUri = rodaImpian.getPlayer().picUri;
        createSessions.sessionID = rodaImpian.getPlayer().id;
        sendObject(createSessions);

        RodaSession rodaSession = new RodaSession();
        rodaSession.id = rodaImpian.getPlayer().id;
        rodaSession.playerList = new ArrayList<>();
        rodaSession.questionsReady = rodaImpian.getQuestionsReady();
        rodaSession.tilesOnlineList = new ArrayList<>();
        rodaSession.playerList.add(rodaImpian.getPlayer());
        sendObject(rodaSession);


    }

    public void disconnect() {
        client.stop();
    }


    public class OnlineScreen extends BaseScreen {

        private Group roomGroup = new Group();
        private NewOnline newOnline;
        private final Array<CreateSessions> sessionList = new Array<>();
        private final Table sessionListTable = new Table();

        public OnlineScreen(RodaImpian rodaImpian) {
            super(rodaImpian);
            rodaImpian.setGameModes(GameModes.ONLINE);
            sessionListTable.setSize(900f, 1500f);
            sessionListTable.setPosition(0, 100f);
            connectionStatus = new StatusLabel(StringRes.CONNECTING, skin);
            Gdx.input.setCatchKey(Input.Keys.BACK, true);
            stage.addActor(roomGroup);
        }

        @Override
        public void show() {
            Gdx.input.setInputProcessor(stage);
            roomGroup.addActor(new RoomMenu(NewClient.this, rodaImpian, skin));
            roomGroup.addActor(sessionListTable);
        }


        public void updateOwnSession() {
            stage.clear();
            stage.addActor(new BgImg(assetManager.get(AssetDesc.BG)));
            newOnline = new NewOnline(rodaImpian, NewClient.this, stage, rodaSession);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    rodaImpian.setWheelScreen(new WheelScreen(rodaImpian, newOnline));
                }
            });

        }

        public void updateSessionList(CreateSessions createSessions) {
            Array<String> sessionIds = new Array<>();
            for (CreateSessions c : sessionList) {
                sessionIds.add(c.sessionID);
            }
            if (!sessionIds.contains(createSessions.sessionID, false)) {
                sessionList.add(createSessions);
            }

            updateList();
        }

        public void updateList() {
            sessionListTable.clear();
            Table content = new Table();
            for (CreateSessions c : sessionList) {
                SessionTable sessionTable = new SessionTable(c, NewClient.this, skin,
                        textureAtlas.findRegion("default_avatar"), rodaImpian.getPlayer());
                content.add(sessionTable).height(250f).width(800f).row();
            }
            ScrollPane scrollPane = new ScrollPane(content, skin);
            sessionListTable.add(scrollPane);
        }

        public void removeSession(String id) {
            CreateSessions createSessions = getByID(id);
            if (createSessions != null) {
                sessionList.removeValue(createSessions, false);
            }
            updateList();
        }

        public CreateSessions getByID(String id) {
            for (CreateSessions createSessions : sessionList) {
                if (createSessions.sessionID.equals(id)) {
                    return createSessions;
                }
            }
            return null;
        }


        @Override
        public void update(float delta) {
            if (newOnline != null) {
                newOnline.update(delta);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
                YesNoDialog promptExit = new YesNoDialog(StringRes.EXIT + "?", skin) {
                    @Override
                    protected void result(Object object) {
                        if (object.equals(true)) {
                            client.stop();
                            rodaImpian.gotoMenu();
                        }
                    }
                };
                promptExit.show(stage);


            }
        }

        public void hostLost() {
            ErrorDialog errorDialog = new ErrorDialog(StringRes.HOSTLOST, skin) {
                @Override
                protected void result(Object object) {
                    NewClient.this.disconnect();
                    rodaImpian.gotoMenu();
                }
            };
            errorDialog.show(stage);

        }

        public void roomFull() {
            ErrorDialog errorDialog = new ErrorDialog(StringRes.ROOMFULL, skin);
            errorDialog.show(stage);
        }

        public void kickedOut() {
            ErrorDialog errorDialog = new ErrorDialog(StringRes.YOUBEENKICK, skin) {
                @Override
                protected void result(Object object) {
                    NewClient newClient = new NewClient(rodaImpian);
                }
            };
            errorDialog.show(stage);
        }
    }

    public boolean isConnected() {
        return isConnected;
    }
}

