package com.somboi.rodaimpian.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.ErrorDialog;
import com.somboi.rodaimpian.gdx.actor.StatusLabel;
import com.somboi.rodaimpian.gdx.actor.YesNoDialog;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.base.BaseScreen;
import com.somboi.rodaimpian.gdx.entities.BgImg;
import com.somboi.rodaimpian.gdx.entities.WheelParam;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.online.OnlinePlay;
import com.somboi.rodaimpian.gdx.online.RodaClient;
import com.somboi.rodaimpian.gdx.online.actor.RoomMenu;
import com.somboi.rodaimpian.gdx.online.actor.SessionTable;
import com.somboi.rodaimpian.gdx.online.ChatOnlineOld;
import com.somboi.rodaimpian.gdx.online.entities.EnvelopeIndex;
import com.somboi.rodaimpian.gdx.online.newentities.CreateSessions;

public class OnlineMatchScreen extends BaseScreen implements OnlineInterface {

    private Group roomGroup = new Group();
    private OnlinePlay onlinePlay;
    private final Array<CreateSessions> sessionList = new Array<>();
    private final Table sessionListTable = new Table();
    private RodaClient newClient;
    private StatusLabel connectionStatus;
    private RoomMenu roomMenu;

    public OnlineMatchScreen(RodaImpian rodaImpian) {
        super(rodaImpian);
        newClient = new RodaClient(rodaImpian, this);
        newClient.startClient();
        rodaImpian.setOnlineScreen(this);
        rodaImpian.setGameModes(GameModes.ONLINE);
        sessionListTable.setSize(900f, 1500f);
        sessionListTable.setPosition(0, 100f);
        connectionStatus = new StatusLabel(StringRes.CONNECTING, skin);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        roomMenu = new RoomMenu(newClient, rodaImpian, skin);
        stage.addActor(roomGroup);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        roomGroup.addActor(new RoomMenu(newClient, rodaImpian, skin));
        roomGroup.addActor(sessionListTable);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (onlinePlay != null) {
            onlinePlay.update(delta);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            YesNoDialog promptExit = new YesNoDialog(StringRes.EXIT + "?", skin) {
                @Override
                protected void result(Object object) {
                    if (object.equals(true)) {
                        newClient.stop();
                        rodaImpian.gotoMenu();
                    }
                }
            };
            promptExit.show(stage);
        }

    }

    @Override
    public void checkDisconnected(String id) {
        onlinePlay.checkDisconnected(id);
    }

    @Override
    public void setWheelParam(WheelParam wheelParam) {
        onlinePlay.setWheelParam(wheelParam);
    }

    @Override
    public void checkChar(Character c) {
        onlinePlay.checkChar(c);
    }

    @Override
    public void setActivePlayer(int index) {
        onlinePlay.setActivePlayer(index);
    }

    @Override
    public void setWheelParamResult(WheelParam wheelParam) {
        onlinePlay.setWheelParamResults(wheelParam);
    }

    @Override
    public void setGiftOnline(int giftIndex) {
        onlinePlay.setGiftOnline(giftIndex);
    }

    @Override
    public void hostLost() {
        ErrorDialog errorDialog = new ErrorDialog(StringRes.HOSTLOST, skin) {
            @Override
            protected void result(Object object) {
                newClient.disconnect();
                rodaImpian.gotoMenu();
            }
        };
        errorDialog.show(stage);

    }

    @Override
    public void startPlays() {
        onlinePlay.startPlays();
    }

    @Override
    public void showMenu() {
        onlinePlay.showMenu();
    }

    @Override
    public void showVocals() {
        onlinePlay.showVocals();
    }

    @Override
    public void roomFull() {
        ErrorDialog errorDialog = new ErrorDialog(StringRes.ROOMFULL, skin);
        errorDialog.show(stage);

    }

    @Override
    public void kickedOut() {
        ErrorDialog errorDialog = new ErrorDialog(StringRes.YOUBEENKICK, skin) {
            @Override
            protected void result(Object object) {
                rodaImpian.setScreen(new OnlineMatchScreen(rodaImpian));
            }
        };
        errorDialog.show(stage);
    }

    @Override
    public void revealAll() {
        onlinePlay.reveaAll();
    }

    @Override
    public void newRound() {
        onlinePlay.newRound();
    }

    @Override
    public void updateSession(CreateSessions createSessions) {
        Array<String> sessionIds = new Array<>();
        for (CreateSessions c : sessionList) {
            sessionIds.add(c.sessionID);
        }
        if (!sessionIds.contains(createSessions.sessionID, false)) {
            sessionList.add(createSessions);
        }
        updateList();

    }

    private void updateList() {
        sessionListTable.clear();
        Table content = new Table();
        for (CreateSessions c : sessionList) {
            SessionTable sessionTable = new SessionTable(c, newClient, skin,
                    textureAtlas.findRegion("default_avatar"), rodaImpian.getPlayer());
            content.add(sessionTable).height(250f).width(800f).row();
        }
        ScrollPane scrollPane = new ScrollPane(content, skin);
        sessionListTable.add(scrollPane);
    }

    @Override
    public void removeSession(String sessionID) {
        CreateSessions createSessions = getByID(sessionID);
        if (createSessions != null) {
            sessionList.removeValue(createSessions, false);
        }
        updateList();
    }

    private CreateSessions getByID(String id) {
        for (CreateSessions createSessions : sessionList) {
            if (createSessions.sessionID.equals(id)) {
                return createSessions;
            }
        }
        return null;
    }

    @Override
    public void updateOwnSession() {
        stage.clear();
        stage.addActor(new BgImg(assetManager.get(AssetDesc.BG)));
        onlinePlay = new OnlinePlay(rodaImpian, newClient, stage, newClient.getRodaSession());
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                rodaImpian.setWheelScreen(new WheelScreen(rodaImpian, onlinePlay));
            }
        });
    }

    @Override
    public void queueChat(ChatOnlineOld chatOnlineOld) {
        onlinePlay.queueChatOnline(chatOnlineOld);
    }

    @Override
    public void setBonusOnline(int bonusIndex) {
        onlinePlay.setBonusOnline(bonusIndex);
    }

    @Override
    public void bonusRound() {
        onlinePlay.bonusRound();
    }

    @Override
    public void openEnvelopes(EnvelopeIndex envelopeIndex) {
        onlinePlay.openEnvelopes(envelopeIndex);
    }

    @Override
    public void checkBonusString(String holder) {
        onlinePlay.checkBonusString(holder);
    }

    @Override
    public StatusLabel getStatus() {
        return connectionStatus;
    }
}
