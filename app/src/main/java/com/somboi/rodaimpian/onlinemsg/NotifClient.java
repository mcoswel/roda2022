package com.somboi.rodaimpian.onlinemsg;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.onlineclasses.PushNotif;

import java.io.IOException;

public class NotifClient extends Client {

    public NotifClient(PushNotif pushNotif) {
        start();
        getKryo().register(PushNotif.class);

        new Thread(){
            @Override
            public void run() {
                try {
                    connect(5000, StringRes.RODARODA,6005);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        addListener(new Listener(){
            @Override
            public void connected(Connection connection) {
                sendTCP(pushNotif);
                stop();
            }
        });

    }
}
