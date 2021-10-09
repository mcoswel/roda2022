package com.somboi.rodaimpian;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class RodaClient {
    private Client client;
    private final RodaOnlineInterface rodaOnlineInterface;

    public RodaClient(RodaOnlineInterface rodaOnlineInterface) {
        this.rodaOnlineInterface = rodaOnlineInterface;
        client = new Client();
        Network.register(client);
        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                Network.RoomID roomID = new Network.RoomID();
                roomID.roomID = rodaOnlineInterface.getRoomID();
                Log.d("RoomID","Room ID "+roomID.roomID);
                client.sendTCP(roomID);
            }

            @Override
            public void disconnected(Connection connection) {

            }

            @Override
            public void received(Connection connection, Object o) {
                super.received(connection, o);
            }
        });
        client.start();
        connects();

    }

    private void connects() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.connect(5000, "192.168.8.110", Network.port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }
}
