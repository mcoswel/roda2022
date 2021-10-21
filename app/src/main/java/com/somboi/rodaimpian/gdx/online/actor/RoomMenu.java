package com.somboi.rodaimpian.gdx.online.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.LargeButton;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.online.NewClient;

public class RoomMenu extends Table {
    private final LargeButton chat;
    private final LargeButton createRoom;

    public RoomMenu(NewClient newClient, RodaImpian rodaImpian, Skin skin) {
        chat = new LargeButton(StringRes.CHAT, skin);
        chat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.startOnlineChat();
            }
        });
        createRoom = new LargeButton(StringRes.CREATEROOM, skin);
        createRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                newClient.createRoom();
            }
        });
        this.add(chat);
        this.add(createRoom);
        this.pack();
        this.setPosition(450f-this.getWidth()/2f, 20f);

    }
}
