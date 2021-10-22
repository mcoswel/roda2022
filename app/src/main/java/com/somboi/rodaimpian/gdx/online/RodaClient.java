package com.somboi.rodaimpian.gdx.online;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Logger;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.assets.GameSound;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.PlayerGui;
import com.somboi.rodaimpian.gdx.entities.WheelParam;
import com.somboi.rodaimpian.gdx.modes.OnlinePlay;
import com.somboi.rodaimpian.gdx.online.entities.BonusHolder;
import com.somboi.rodaimpian.gdx.online.entities.BonusIndex;
import com.somboi.rodaimpian.gdx.online.entities.ChatOnline;
import com.somboi.rodaimpian.gdx.online.entities.CheckAnswer;
import com.somboi.rodaimpian.gdx.online.entities.DisconnectPlayer;
import com.somboi.rodaimpian.gdx.online.entities.EnvelopeIndex;
import com.somboi.rodaimpian.gdx.online.entities.GameState;
import com.somboi.rodaimpian.gdx.online.entities.PlayerState;
import com.somboi.rodaimpian.gdx.online.entities.SessionList;
import com.somboi.rodaimpian.gdx.online.newentities.SessionRoom;
import com.somboi.rodaimpian.gdx.online.entities.StatusText;
import com.somboi.rodaimpian.gdx.screen.RoomScreen;

public class RodaClient extends Client {
    private final RodaImpian rodaImpian;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    private boolean isHost;
    private PlayerState playerState;
    private OnlinePlay onlinePlay;
    private boolean online;
    private boolean offline = true;
    private SessionRoom sessionRoom;
    private String sessionID;

    public RodaClient(RodaImpian rodaImpian, OnlinePlay onlinePlay, GameSound gameSound, RoomScreen roomScreen) {
        super(1000000, 1000000);
        this.rodaImpian = rodaImpian;
        this.onlinePlay = onlinePlay;
        Network.register(this);

        this.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                online = true;
                offline = false;
                Player player = rodaImpian.getPlayer();
                player.conID = connection.getID();
                playerState = PlayerState.CONNECTED;
                rodaImpian.setPlayer(player);
                onlinePlay.getDebugStatus().setText(StringRes.ONLINE);
                updateReturnTripTime();
            }

            @Override
            public void disconnected(Connection connection) {
                logger.debug("disconnected ");
            }

            @Override
            public void received(Connection connection, Object o) {


                if (o instanceof SessionList) {
                    if (!rodaImpian.getRoomScreen().isPlaying()) {
                    SessionList sessionList = (SessionList) o;
                    onlinePlay.getDebugStatus().setText(StringRes.ONLINE + " (Ping: "+ connection.getReturnTripTime()+"ms)");
                        rodaImpian.updateSessionList(sessionList);
                        for (SessionRoom sessionRoom0 : sessionList.sessionRoomList) {
                            if (sessionRoom != null) {
                                if (sessionRoom.getRoomID().equals(sessionRoom0.getRoomID())) {
                                    sessionRoom = sessionRoom0;
                                    rodaImpian.setSessionRoom(sessionRoom);
                                }
                            }
                        }
                    }
                }

                if (o instanceof EnvelopeIndex){
                    EnvelopeIndex envelopeIndex = (EnvelopeIndex)o;
                    onlinePlay.openEnvelopeByIndex(envelopeIndex.index);
                }
                if (o instanceof BonusHolder){
                    BonusHolder bonusHolder = (BonusHolder) o;
                    onlinePlay.checkBonusStringOnline(bonusHolder.holder);
                }
                if (o instanceof BonusIndex){
                    BonusIndex bonusIndex = (BonusIndex) o;
                    onlinePlay.setOnlineBonus(bonusIndex.index);
                }

                if (o instanceof WheelParam) {
                    onlinePlay.setWheelParam((WheelParam) o);
                   /* if (!rodaImpian.getPlayer().turn) {
                        rodaImpian.getWheelScreen().getWheelBody().setTransform(
                                rodaImpian.getWheelScreen().getWheelBody().getPosition(),
                                onlinePlay.getWheelParam().wheelangle
                        );
                    }*/
                }

                if (o instanceof SessionRoom) {
                    sessionRoom = (SessionRoom) o;
                    if (playerState.equals(PlayerState.CONNECTED)) {
                        rodaImpian.setQuestionsReady(sessionRoom.getQuestionsReady());
                        //onlinePlay.setPlayers();
                    }

                }

                if (o instanceof DisconnectPlayer) {
                    Player player = ((DisconnectPlayer) o).player;
                    for (PlayerGui p : onlinePlay.getPlayerGuis()) {
                        if (p.getPlayer().id.equals(player.id)) {
                            p.getImage().setColor(Color.BLUE);
                            p.getPlayer().disconnect = true;
                            if (p.getPlayer().turn) {
                                if (isHost) {
                                    sendTCP(GameState.CHANGETURN);
                                }
                            }
                        }
                    }
                }
                if (o instanceof GameState) {
                    GameState gameState = (GameState) o;
                    if (gameState.equals(GameState.START)) {
                        logger.debug("START ");
                        if (!playerState.equals(PlayerState.START)) {
                            if (sessionRoom.getRoomID().equals(rodaImpian.getPlayer().id)) {
                                isHost = true;
                            }
                            rodaImpian.setQuestionsReady(sessionRoom.getQuestionsReady());
                            roomScreen.startPlaying();
                            onlinePlay.setPlayers();
                        }
                    }
                    if (gameState.equals(GameState.REVEALALL)){
                        onlinePlay.reveaAll();
                    }
                    if (gameState.equals(GameState.SHOWMENU)) {
                        if (!playerState.equals(PlayerState.SHOWMENU)) {
                            onlinePlay.startPlays();
                        }
                    }
                    if (gameState.equals(GameState.SPIN)) {
                        if (!playerState.equals(PlayerState.SPIN)) {
                            onlinePlay.spinWheel();
                            setPlayerState(PlayerState.SPIN);
                        }
                    }
                    if (gameState.equals(GameState.WHEELALMOSTSTOP)) {
                        if (!rodaImpian.getPlayer().turn) {
                            rodaImpian.gotoRoomScreen();
                        }
                    }
                    if (gameState.equals(GameState.WHEELIMPULSE)) {
                        if (!playerState.equals(PlayerState.WHEELIMPULSE)) {
                            rodaImpian.getWheelScreen().applyImpulse(onlinePlay.getWheelParam().wheelImpulse);
                            setPlayerState(PlayerState.WHEELIMPULSE);
                        }
                    }
                    if (gameState.equals(GameState.WHEELSTOP)) {
                        if (rodaImpian.getPlayer().turn) {
                            rodaImpian.getWheelScreen().checkContact();
                        } else {
                            rodaImpian.gotoRoomScreen();
                        }
                    }

                    if (gameState.equals(GameState.SHOWRESULT)) {

                        if (!playerState.equals(PlayerState.SHOWRESULT)) {
                            if (rodaImpian.getPlayer().turn) {
                                rodaImpian.getWheelScreen().showResult();
                            } else {
                                onlinePlay.showInfo(StringRes.WHEELRESULT + onlinePlay.getWheelParam().results);
                                if (onlinePlay.getWheelParam().results.equals(StringRes.GIFT)) {
                                    onlinePlay.setGiftOnline(onlinePlay.getWheelParam().giftIndex);
                                }
                            }
                            setPlayerState(PlayerState.SHOWRESULT);
                        }
                    }

                    if (gameState.equals(GameState.GOTOMATCH)) {
                        if (!playerState.equals(PlayerState.GOTOMATCH)) {
                            rodaImpian.gotoRoomScreen();
                        }
                    }


                    if (gameState.equals(GameState.CHANGETURN)) {
                        if (!playerState.equals(PlayerState.CHANGETURN)) {
                           // onlinePlay.changeTurnOffline();
                            setPlayerState(PlayerState.CHANGETURN);
                        }
                    }

                }

                if (o instanceof StatusText) {
                    StatusText statusText = (StatusText) o;
                    onlinePlay.getDebugStatus().setText(statusText.status);
                }

                if (o instanceof ChatOnline) {
                    ChatOnline chatOnline = (ChatOnline) o;
                    onlinePlay.queueChatOnline(chatOnline);
                }

                if (o instanceof CheckAnswer) {
                    CheckAnswer checkAnswer = (CheckAnswer) o;
                    onlinePlay.checkAnswer(checkAnswer.character);
                }


            }
        });


    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
        sendTCP(playerState);
    }

    public boolean isOnline() {
        return online;
    }


    public boolean isHost() {
        return isHost;
    }

    public SessionRoom getSessionRoom() {
        return sessionRoom;
    }

    public void setOnlinePlay(OnlinePlay onlinePlay) {
        this.onlinePlay = onlinePlay;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
