package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.actor.StatusLabel;
import com.somboi.rodaimpian.gdx.assets.StringRes;

import java.io.IOException;

public class ClientNew extends Client {
    private final RodaImpianNew rodaImpianNew;
    private boolean connected;
    private final Label statusLabel;
    public ClientNew(RodaImpianNew rodaImpianNew, Stage stage, Skin skin) {
        super(1000000, 1000000);
        this.rodaImpianNew = rodaImpianNew;
        start();
        NetWork.register(this);
        addListener(new RodaListener());
        connectServer();
        statusLabel = new StatusLabel("", skin);
        SequenceAction sequenceAction = new SequenceAction(Actions.fadeOut(1f), Actions.fadeIn(1f));
        statusLabel.addAction(Actions.forever(sequenceAction));
        stage.addActor(statusLabel);
        updateStatus(StringRes.CONNECTING);
    }

    private void updateStatus(String text){
        statusLabel.setText(text);
        statusLabel.pack();
        statusLabel.setPosition(450f - statusLabel.getWidth(), 888f);
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

    private class RodaListener extends Listener{
        @Override
        public void connected(Connection connection) {
            connected = true;
            sendTCP(rodaImpianNew.getPlayer());
        }

        @Override
        public void disconnected(Connection connection) {
            super.disconnected(connection);
        }

        @Override
        public void received(Connection connection, Object o) {
            super.received(connection, o);
        }

        @Override
        public void idle(Connection connection) {
            super.idle(connection);
        }
    }
}
