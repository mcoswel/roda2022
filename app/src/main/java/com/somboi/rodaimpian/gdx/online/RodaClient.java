package com.somboi.rodaimpian.gdx.online;

import static com.somboi.rodaimpian.gdx.online.GameState.BUYVOCAL;
import static com.somboi.rodaimpian.gdx.online.GameState.CHANGETURN;
import static com.somboi.rodaimpian.gdx.online.GameState.CHECKCONTACT;
import static com.somboi.rodaimpian.gdx.online.GameState.INIT;
import static com.somboi.rodaimpian.gdx.online.GameState.RESULTSHOWN;
import static com.somboi.rodaimpian.gdx.online.GameState.SHOWCONS;
import static com.somboi.rodaimpian.gdx.online.GameState.SPIN;
import static com.somboi.rodaimpian.gdx.online.GameState.SPINNING;
import static com.somboi.rodaimpian.gdx.online.GameState.STARTPLAY;

import com.badlogic.gdx.math.MathUtils;
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
    private WheelParam wheelParam;

    public RodaClient(RodaImpian rodaImpian, GameSound gameSound, OnlinePlay onlinePlay) {

        this.rodaImpian = rodaImpian;
        Network.register(this);
        this.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {


                RegisterPlayer registerPlayer = new RegisterPlayer();
                registerPlayer.player = rodaImpian.getPlayer();
                registerPlayer.roomID = rodaImpian.getRooms().getId();
                sendTCP(registerPlayer);

                if (rodaImpian.getPlayer().id.equals(rodaImpian.getRooms().getHostPlayer().id)) {
                    MatchRoom matchRoom = new MatchRoom();
                    matchRoom.firstTurnRotation = MathUtils.random(5f, 9f);
                    matchRoom.questionsReady = rodaImpian.getQuestionsReady();
                    matchRoom.wheelParam = new WheelParam();
                    sendTCP(matchRoom);
                    isHost = true;
                }
                logger.debug("connected ");

            }

            @Override
            public void disconnected(Connection connection) {
                logger.debug("disconnected ");

            }

            @Override
            public void received(Connection connection, Object o) {
                if (o instanceof WheelParam) {
                    wheelParam = (WheelParam) o;
                  //  onlinePlay.setWheelParam(wheelParam);
                    if (onlinePlay.getGameState().equals(SPIN)) {
                        rodaImpian.getWheelScreen().applyImpulse(wheelParam.wheelImpulse);
                        onlinePlay.setGameState(SPINNING);
                    }

                    if (onlinePlay.getGameState().equals(CHECKCONTACT)) {
                        rodaImpian.getWheelScreen().showResult();
                        onlinePlay.setGameState(RESULTSHOWN);
                    }
                }

                if (o instanceof BeginSpin){
                    BeginSpin beginSpin = (BeginSpin) o;

                    if (wheelParam!=null){
                        rodaImpian.getWheelScreen().getWheelBody().setTransform(
                                rodaImpian.getWheelScreen().getWheelBody().getPosition(),
                               beginSpin.startingAngle
                        );
                    }
                    onlinePlay.spinWheel();
                }

                if (o instanceof GameState) {
                    GameState gameState = (GameState) o;
                    if (!gameState.equals(onlinePlay.getGameState())) {
                        if (gameState.equals(BUYVOCAL)) {
                            onlinePlay.getActivePlayer().currentScore -= 250;
                        }
                        if (gameState.equals(STARTPLAY)) {
                            onlinePlay.startPlays();
                        }

                        if (gameState.equals(CHECKCONTACT)) {
                            rodaImpian.getWheelScreen().checkContact();
                            onlinePlay.setGameState(CHECKCONTACT);
                        }
                        if (gameState.equals(SHOWCONS)) {
                            onlinePlay.showConsonants();
                            onlinePlay.setGameState(SHOWCONS);
                        }
                        if (gameState.equals(CHANGETURN)){
                            onlinePlay.changeTurnOffline();
                            onlinePlay.setGameState(CHANGETURN);
                        }
                    }
                }
                if (o instanceof MatchRoom) {
                    matchRoom = (MatchRoom) o;

                    if (onlinePlay.getGameState().equals(INIT)) {
                        if (!isHost) {
                            rodaImpian.setQuestionsReady(matchRoom.questionsReady);
                        }
                        onlinePlay.setPlayers();
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


    public MatchRoom getMatchRoom() {
        return matchRoom;
    }

    public boolean isHost() {
        return isHost;
    }
}
