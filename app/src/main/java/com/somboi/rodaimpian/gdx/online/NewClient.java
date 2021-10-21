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
import com.somboi.rodaimpian.gdx.actor.StatusLabel;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.base.BaseScreen;
import com.somboi.rodaimpian.gdx.entities.BgImg;
import com.somboi.rodaimpian.gdx.entities.WheelParam;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.online.actor.RoomMenu;
import com.somboi.rodaimpian.gdx.online.actor.SessionTable;
import com.somboi.rodaimpian.gdx.online.newentities.CreateSessions;
import com.somboi.rodaimpian.gdx.online.newentities.RegisterPlayer;
import com.somboi.rodaimpian.gdx.online.newentities.RemoveSession;
import com.somboi.rodaimpian.gdx.online.newentities.RodaSession;
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

    public NewClient(RodaImpian rodaImpian) {
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
            }

            @Override
            public void received(Connection connection, Object o) {
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
                    onlineScreen.updateOwnSession();
                }
            }

            @Override
            public void disconnected(Connection connection) {
                logger.debug("Disconnected");
                isConnected = false;
                connectionStatus.setText(StringRes.FAILSERVER);
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
        rodaImpian.setScreen(onlineScreen);

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
        rodaSession.wheelParam = new WheelParam();
        rodaSession.tilesOnlineList = new ArrayList<>();
        rodaSession.playerList.add(rodaImpian.getPlayer());
        sendObject(rodaSession);


    }


    private class OnlineScreen extends BaseScreen {

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
        }

        @Override
        public void show() {
            stage.addActor(roomGroup);
            Gdx.input.setInputProcessor(stage);
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
            if (NewClient.this.isConnected) {
                roomGroup.addActor(new RoomMenu(NewClient.this, skin));
                roomGroup.addActor(sessionListTable);
                connectionStatus.remove();
            } else {
                stage.addActor(connectionStatus);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
                client.stop();
                rodaImpian.gotoMenu();
            }
        }
    }

}

