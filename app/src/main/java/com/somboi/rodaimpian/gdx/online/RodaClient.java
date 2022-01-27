package com.somboi.rodaimpian.gdx.online;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.somboi.rodaimpian.activities.RodaImpian;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.WheelParam;
import com.somboi.rodaimpian.gdxnew.games.GameModes;
import com.somboi.rodaimpian.gdx.online.entities.BonusHolder;
import com.somboi.rodaimpian.gdx.online.entities.BonusIndex;
import com.somboi.rodaimpian.gdx.online.entities.CheckAnswerOld;
import com.somboi.rodaimpian.gdx.online.entities.DisconnectPlayer;
import com.somboi.rodaimpian.gdx.online.entities.EnvelopeIndex;
import com.somboi.rodaimpian.gdx.online.entities.GameStateOld;
import com.somboi.rodaimpian.gdx.online.entities.PlaeyrStateOld;
import com.somboi.rodaimpian.gdx.online.newentities.ClearSession;
import com.somboi.rodaimpian.gdx.online.newentities.CreateSessions;
import com.somboi.rodaimpian.gdx.online.newentities.FinishSpin;
import com.somboi.rodaimpian.gdx.online.newentities.RegisterPlayer;
import com.somboi.rodaimpian.gdx.online.newentities.RemoveSession;
import com.somboi.rodaimpian.gdx.online.newentities.RodaSession;
import com.somboi.rodaimpian.gdx.online.newentities.SetActivePlayer;
import com.somboi.rodaimpian.gdx.screen.OnlineInterface;

import java.io.IOException;
import java.util.ArrayList;

public class RodaClient {
    private final RodaImpian rodaImpian;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    private boolean isConnected;
    private Client client;

    private RodaSession rodaSession;
    private WheelParam wheelParam;
    private OnlineInterface onlineScreen;
    private String sessionID;
    private PlaeyrStateOld plaeyrStateOld;
    public RodaClient(RodaImpian rodaImpian, OnlineInterface onlineScreen) {
        rodaImpian.setGameModes(GameModes.ONLINE);
        this.rodaImpian = rodaImpian;
        this.onlineScreen = onlineScreen;
        rodaImpian.loadRewardedAds();
    }
    public void startClient(){
        client = new Client(1000000, 1000000);
        client.start();
        NetWorkOld.register(client);
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
                    onlineScreen.checkDisconnected(player.id);
                }
                if (o instanceof WheelParam) {
                    wheelParam = (WheelParam) o;
                    onlineScreen.setWheelParam(wheelParam);
                }
                if (o instanceof CheckAnswerOld) {

                    CheckAnswerOld checkAnswerOld = (CheckAnswerOld) o;
                    logger.debug("receive chk answer " + checkAnswerOld.character);

                    onlineScreen.checkChar(checkAnswerOld.character);
                }

                if (o instanceof SetActivePlayer) {
                    SetActivePlayer setActivePlayer = (SetActivePlayer) o;
                    onlineScreen.setActivePlayer(setActivePlayer.index);
                    rodaImpian.gotoOnlineScreen();
                    sendObject(PlaeyrStateOld.SHOWMENU);
                }

                if (o instanceof FinishSpin) {
                    FinishSpin finishSpin = (FinishSpin) o;
                    //onlineScreen.newOnline.setWheelParam(finishSpin.wheelParam);
                    rodaImpian.getWheelScreen().setWheelParamResults(finishSpin.wheelParam);
                    onlineScreen.setWheelParamResult(finishSpin.wheelParam);
                    if (finishSpin.wheelParam.giftIndex > 0) {
                        onlineScreen.setGiftOnline(finishSpin.wheelParam.giftIndex);
                        finishSpin.wheelParam.giftIndex=0;
                        if (rodaImpian.getPlayer().turn){
                            sendObject(finishSpin.wheelParam);
                        }
                    }
                    sendObject(PlaeyrStateOld.SHOWRESULT);
                }
                if (o instanceof PlaeyrStateOld) {
                    sendObject(o);
                    logger.debug("receive playerstate " + ((PlaeyrStateOld) o));
                }

                if (o instanceof GameStateOld) {
                    GameStateOld gameStateOld = (GameStateOld) o;
                    if (gameStateOld.equals(GameStateOld.HOSTLOST)) {
                        onlineScreen.hostLost();
                    }
                    if (gameStateOld.equals(GameStateOld.START)) {
                        logger.debug("Start play");
                        onlineScreen.startPlays();
                    }
                    if (gameStateOld.equals(GameStateOld.BANKRUPT)) {
                        rodaImpian.gotoOnlineScreen();
                        sendObject(PlaeyrStateOld.CHANGETURN);
                    }

                    if (gameStateOld.equals(GameStateOld.SHOWMENU)) {
                        onlineScreen.showMenu();
                    }
                    if (gameStateOld.equals(GameStateOld.SPIN)) {
                        rodaImpian.spinWheel();
                    }

                    if (gameStateOld.equals(GameStateOld.SHOWVOCAL)) {
                        onlineScreen.showVocals();
                    }
                    if (gameStateOld.equals(GameStateOld.ROOMFULL)) {
                        onlineScreen.roomFull();
                        RegisterPlayer registerPlayer = new RegisterPlayer();
                        registerPlayer.player = rodaImpian.getPlayer();
                        registerPlayer.roomID = "empty";
                        sendObject(registerPlayer);
                    }
                    if (gameStateOld.equals(GameStateOld.KICKOUT)) {
                        onlineScreen.kickedOut();
                    }
                    if (gameStateOld.equals(GameStateOld.SHOWRESULT)) {
                        rodaImpian.getWheelScreen().showResult();
                    }
                    if (gameStateOld.equals(GameStateOld.GOTOMATCH)) {
                        rodaImpian.gotoOnlineScreen();
                    }
                    if (gameStateOld.equals(GameStateOld.REVEALALL)) {
                        rodaImpian.gotoOnlineScreen();
                        onlineScreen.revealAll();
                    }
                    if (gameStateOld.equals(GameStateOld.NEWROUND)) {
                        logger.debug("Increase game ");
                        onlineScreen.newRound();
                    }
                }
                if (o instanceof CreateSessions) {
                    CreateSessions createSessions = (CreateSessions) o;
                    if (createSessions.sessionID != null) {
                        onlineScreen.updateSession(createSessions);
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
                if (o instanceof ChatOnlineOld) {
                    ChatOnlineOld chatOnlineOld = (ChatOnlineOld) o;
                    onlineScreen.queueChat(chatOnlineOld);
                    logger.debug("receive chat online");
                }

                if (o instanceof BonusIndex) {
                    BonusIndex bonusIndex = (BonusIndex) o;
                    onlineScreen.setBonusOnline(bonusIndex.index);
                    rodaImpian.gotoOnlineScreen();
                    onlineScreen.bonusRound();
                }
                if (o instanceof EnvelopeIndex) {
                    EnvelopeIndex envelopeIndex = (EnvelopeIndex) o;
                    onlineScreen.openEnvelopes(envelopeIndex);
                }
                if (o instanceof BonusHolder) {
                    BonusHolder bonusHolder = (BonusHolder) o;
                    onlineScreen.checkBonusString(bonusHolder.holder);
                }
            }

            @Override
            public void disconnected(Connection connection) {
                logger.debug("Disconnected");
                isConnected = false;
                rodaImpian.gotoOnlineScreen();
                DisconnectPlayer disconnectPlayer = new DisconnectPlayer();
                disconnectPlayer.player = rodaImpian.getPlayer();
                sendObject(disconnectPlayer);
                // connectionStatus.setText(StringRes.FAILSERVER);
            }
        });


        //rodaImpian.setRewarded(true);

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    client.connect(5001, StringRes.RODARODA, 5001);
//192.168.42.213


                    client.setTimeout(20000);
                } catch (IOException e) {
                    e.printStackTrace();
                    if (onlineScreen.getStatus() != null) {
                        onlineScreen.getStatus().setText(StringRes.FAILSERVER);
                    }
                }
            }
        });
    }


    public void tryConnect() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    client.connect(5001, "159.223.73.205", 5001);

                    client.setTimeout(20000);
                } catch (IOException e) {
                    e.printStackTrace();
                    if (onlineScreen.getStatus() != null) {
                        onlineScreen.getStatus().setText(StringRes.FAILSERVER);
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

        rodaImpian.setRewarded(true);

    }

    public RodaSession getRodaSession() {
        return rodaSession;
    }

    public void disconnect() {
        client.stop();
    }


    public boolean isConnected() {
        return isConnected;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public PlaeyrStateOld getPlayerState() {
        return plaeyrStateOld;
    }

    public void setPlayerState(PlaeyrStateOld plaeyrStateOld) {
        this.plaeyrStateOld = plaeyrStateOld;
    }

    public void stop() {
        client.stop();
    }

}

