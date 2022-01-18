package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.StringRes;

import java.io.IOException;

public class ClientNew extends Client {
    private final RodaImpianNew rodaImpianNew;
    public ClientNew(RodaImpianNew rodaImpianNew) {
        super(1000000, 1000000);
        this.rodaImpianNew = rodaImpianNew;
        start();
        NetWork.register(this);
        addListener(new RodaListener());
        connectServer();
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
