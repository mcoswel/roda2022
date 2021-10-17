package com.somboi.rodaimpian.gdx.online;

import com.badlogic.gdx.utils.Logger;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.assets.GameSound;
import com.somboi.rodaimpian.gdx.entities.WheelParam;
import com.somboi.rodaimpian.gdx.modes.OnlinePlay;

public class RodaClient extends Client {
    private final RodaImpian rodaImpian;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    private MatchRoom matchRoom;
    private boolean isHost;
    private PlayerState playerState;

    public RodaClient(RodaImpian rodaImpian, GameSound gameSound, OnlinePlay onlinePlay) {

        this.rodaImpian = rodaImpian;
        Network.register(this);
        this.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {

                RegisterPlayer registerPlayer = new RegisterPlayer();
                playerState = PlayerState.CONNECTED;
                registerPlayer.player = rodaImpian.getPlayer();
                registerPlayer.roomID = rodaImpian.getRooms().getId();
                sendTCP(registerPlayer);

                if (rodaImpian.getPlayer().id.equals(rodaImpian.getRooms().getHostPlayer().id)) {
                    MatchRoom matchRoom = new MatchRoom();
                    matchRoom.questionsReady = rodaImpian.getQuestionsReady();
                    matchRoom.wheelParam = new WheelParam();
                    sendTCP(matchRoom);
                    isHost = true;
                }

            }

            @Override
            public void disconnected(Connection connection) {
                logger.debug("disconnected ");

            }

            @Override
            public void received(Connection connection, Object o) {
                if (o instanceof WheelParam) {
                    onlinePlay.setWheelParam((WheelParam) o);
                }

                if (o instanceof MatchRoom) {
                    //  logger.debug("receive matchroom");
                    MatchRoom matchRoom0 = (MatchRoom) o;
                    matchRoom = matchRoom0;

                    if (playerState.equals(PlayerState.CONNECTED)) {
                        if (!isHost) {
                            rodaImpian.setQuestionsReady(matchRoom.questionsReady);
                        }
                        onlinePlay.setPlayers();
                    }

                }
                if (o instanceof GameState) {
                    GameState gameState = (GameState) o;
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
                    if (gameState.equals(GameState.WHEELIMPULSE)) {
                        if (!playerState.equals(PlayerState.WHEELIMPULSE)) {
                            rodaImpian.getWheelScreen().applyImpulse(onlinePlay.getWheelParam().wheelImpulse);
                            setPlayerState(PlayerState.WHEELIMPULSE);
                        }
                    }
                    if (gameState.equals(GameState.WHEELSTOP)) {
                        if (!rodaImpian.getPlayer().turn) {
                            rodaImpian.getWheelScreen().getWheelBody().setTransform(
                                    rodaImpian.getWheelScreen().getWheelBody().getPosition(),
                                    onlinePlay.getWheelParam().wheelangle
                            );
                        } else {
                            rodaImpian.getWheelScreen().checkContact();
                        }
                    }

                    if (gameState.equals(GameState.SHOWRESULT)) {
                        if (!playerState.equals(PlayerState.SHOWRESULT)) {
                            if (rodaImpian.getPlayer().turn) {
                                rodaImpian.getWheelScreen().showResult();
                            }
                            setPlayerState(PlayerState.SHOWRESULT);
                        }
                    }

                    if (gameState.equals(GameState.GOTOMATCH)){
                        if (!playerState.equals(PlayerState.GOTOMATCH)){
                            rodaImpian.gotoMatch();
                        }
                    }

                    if (gameState.equals(GameState.CHANGETURN)){
                        if (!playerState.equals(PlayerState.CHANGETURN)){
                            onlinePlay.changeTurnOffline();
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

    public MatchRoom getMatchRoom() {
        return matchRoom;
    }

    public boolean isHost() {
        return isHost;
    }
}
