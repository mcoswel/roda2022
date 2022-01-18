package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.actors.StatusLabel;
import com.somboi.rodaimpian.gdxnew.onlineclasses.RoomSession;
import com.somboi.rodaimpian.gdxnew.screens.OnlineScreen;

import java.io.IOException;

public class ClientNew extends Client {
    private final RodaImpianNew rodaImpianNew;
    private boolean connected;
    private final StatusLabel statusLabel;
    private OnlineGame onlineGame;
    private final Stage stage;

    public ClientNew(RodaImpianNew rodaImpianNew, Stage stage, Skin skin) {
        super(1000000, 1000000);
        this.rodaImpianNew = rodaImpianNew;
        this.stage = stage;
        start();
        NetWork.register(this);
        addListener(new RodaListener());
        connectServer();
        statusLabel = new StatusLabel(skin);
        onlineGame = new OnlineGame(stage, rodaImpianNew);
        statusLabel.updateText(StringRes.CONNECTING);
        stage.addActor(statusLabel);
    }

    private void connectServer(){
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    connect(6001, StringRes.RODARODA, 6001);
                    setTimeout(20000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendObjects(Object o){
        sendTCP(o);
    }

    public void update(float delta){
        onlineGame.update(delta);
    }

    private class RodaListener extends Listener{
        @Override
        public void connected(Connection connection) {
            connected = true;
            rodaImpianNew.getPlayer().setConnectionID(connection.getID());
            sendTCP(rodaImpianNew.getPlayer());
            statusLabel.clearActions();
            statusLabel.updateText(StringRes.CONNECTED);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                  statusLabel.remove();
                }
            },2f);
        }

        @Override
        public void disconnected(Connection connection) {
            rodaImpianNew.getPlayer().setDisconnect(true);
        }

        @Override
        public void received(Connection connection, Object o) {
           if (o instanceof RoomSession){
               RoomSession roomSession = (RoomSession) o;
               if (roomSession.getRoomNo() == rodaImpianNew.getRoomSessionID()){
                   stage.clear();
                   onlineGame.createGameBg();
                   onlineGame.createGameTables();
               }
           }
        }

        @Override
        public void idle(Connection connection) {
            super.idle(connection);
        }
    }
}
