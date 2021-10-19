package com.somboi.rodaimpian.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.ErrorDialog;
import com.somboi.rodaimpian.gdx.actor.LargeButton;
import com.somboi.rodaimpian.gdx.actor.SessionListTable;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.base.BaseScreen;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.modes.OnlinePlay;
import com.somboi.rodaimpian.gdx.online.Network;
import com.somboi.rodaimpian.gdx.online.PlayerState;
import com.somboi.rodaimpian.gdx.online.RegisterPlayer;
import com.somboi.rodaimpian.gdx.online.RodaClient;
import com.somboi.rodaimpian.gdx.online.SessionList;
import com.somboi.rodaimpian.gdx.online.SessionRoom;

import java.io.IOException;
import java.util.ArrayList;

public class RoomScreen extends BaseScreen {
    private RodaClient rodaClient;
    private Group roomGroup = new Group();
    private boolean isPlaying;
    private final LargeButton chatBtn;
    private final LargeButton createRoombtn;

    public RoomScreen(RodaImpian rodaImpian) {
        super(rodaImpian);
        rodaImpian.setRoomScreen(this);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        rodaImpian.setGameModes(GameModes.ONLINE);
        OnlinePlay onlinePlay = new OnlinePlay(rodaImpian, stage);
        rodaClient = new RodaClient(rodaImpian, onlinePlay, gameSound, this);
        onlinePlay.setRodaClient(rodaClient);
        rodaClient.start();
        Table button = new Table();
        chatBtn = new LargeButton(StringRes.CHAT, skin);
        chatBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.startOnline();
            }
        });

        createRoombtn = new LargeButton(StringRes.CREATEROOM, skin);
        createRoombtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (rodaClient.isOnline()) {
                    rodaClient.sendTCP(PlayerState.EXITROOM);
                    SessionRoom sessionRoom = new SessionRoom();
                    sessionRoom.setRoomID(player.id);
                    sessionRoom.setPlayerList(new ArrayList<>());
                    sessionRoom.setRoomName(StringRes.ROOM + player.name);
                    sessionRoom.setQuestionsReady(rodaImpian.getQuestionsReady());
                    // sessionRoom.getPlayerList().add(player);
                    rodaClient.sendTCP(sessionRoom);
                    RegisterPlayer registerPlayer = new RegisterPlayer();
                    registerPlayer.player = rodaImpian.getPlayer();
                    registerPlayer.roomID = sessionRoom.getRoomID();
                    rodaClient.sendTCP(registerPlayer);
                    player.guiIndex = 0;
                }
            }
        });
        button.add(chatBtn);
        button.add(createRoombtn);

        button.pack();
        button.center().bottom();
        stage.addActor(button);
        roomGroup.setSize(900f, 1400f);
        roomGroup.setPosition(0, 50f);
        stage.addActor(roomGroup);
        connectServer();
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    public void updateSessionList(SessionList sessionList) {
        //logger.debug("update ");
        if (!isPlaying) {
            roomGroup.clear();
            Table content = new Table();
            content.defaults().padTop(10f);
            for (SessionRoom sessionRoom : sessionList.sessionRoomList) {
                // logger.debug("session list player size "+sessionRoom.getPlayerList().size());
                for (Player player : sessionRoom.getPlayerList()) {
                    if (player.id.equals(this.player.id)) {
                        rodaImpian.setSessionRoom(sessionRoom);
                    } else {
                        rodaImpian.setSessionRoom(null);
                    }
                }
                SessionListTable sessionListTable = new SessionListTable(sessionRoom, skin, textureAtlas.findRegion("default_avatar"), rodaImpian, rodaClient);
                Table sessionTable = new Table();
                sessionTable.add(sessionListTable);
                content.add(sessionTable).row();
            }

            ScrollPane scrollPane = new ScrollPane(content, skin);
            Table table = new Table();
            table.setSize(850f, 1000f);
            table.add(scrollPane);
            table.setPosition(0, 100f);
            table.setFillParent(true);
            roomGroup.addActor(table);
        }
    }

    public void startPlaying() {
        isPlaying = true;
        roomGroup.remove();
        bgImg.setDrawable(new SpriteDrawable(new Sprite(assetManager.get(AssetDesc.BG))));
        chatBtn.remove();
        createRoombtn.remove();
    }

    public void kickPlayer(SessionRoom sessionRoom, Player player) {
        sessionRoom.getPlayerList().remove(player);
        rodaClient.sendTCP(sessionRoom);
    }

    private void connectServer() {
        new Thread("Connect") {
            @Override
            public void run() {
                try {
                    rodaClient.connect(5001, "10.1.19.2", Network.port);
                } catch (IOException e) {
                    e.printStackTrace();
                    ErrorDialog errorDialog = new ErrorDialog(StringRes.FAILSERVER, skin);
                    errorDialog.show(stage);
                }
            }
        }.start();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            rodaImpian.gotoMenu();
            rodaClient.stop();
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
