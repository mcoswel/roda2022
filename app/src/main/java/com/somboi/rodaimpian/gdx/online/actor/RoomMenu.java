package com.somboi.rodaimpian.gdx.online.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.ErrorDialog;
import com.somboi.rodaimpian.gdx.actor.LargeButton;
import com.somboi.rodaimpian.gdx.actor.PromptAds;
import com.somboi.rodaimpian.gdx.actor.YesNoDialog;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.online.RodaClient;

public class RoomMenu extends Table {
    private final LargeButton chat;
    private final LargeButton createRoom;

    public RoomMenu(RodaClient newClient, RodaImpian rodaImpian, Skin skin) {
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
                if (newClient.isConnected()) {
                    if (!rodaImpian.isRewarded()){
                        PromptAds promptAds = new PromptAds(skin){
                            @Override
                            protected void result(Object object) {
                                if (object.equals(true)){
                                    newClient.createRoom();
                                    rodaImpian.showRewardedAds();
                                  //  newClient.tryConnect();
                                }
                            }
                        };
                        promptAds.show(getStage());
                    }else {
                        newClient.createRoom();
                    }
                }else{
                    ErrorDialog errorDialog = new ErrorDialog(StringRes.FAILSERVER,skin){
                        @Override
                        protected void result(Object object) {
                            newClient.tryConnect();
                        }
                    };
                    errorDialog.show(getStage());
                }
            }
        });
        this.add(chat);
        this.add(createRoom);
        this.pack();
        this.setPosition(450f-this.getWidth()/2f, 20f);

    }
}
