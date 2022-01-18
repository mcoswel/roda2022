package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.actors.SmallButton;
import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.games.ClientNew;
import com.somboi.rodaimpian.gdxnew.onlineclasses.RoomSession;

import java.util.ArrayList;

public class OnlineScreen extends BaseScreenNew {
    private final Table onlineMenu = new Table();
    ClientNew clientNew;

    // private OnlineGame onlineGame;
    public OnlineScreen(RodaImpianNew rodaImpianNew) {
        super(rodaImpianNew);
        Gdx.input.setInputProcessor(stage);
        actorFactory.createGameBgBlur(rodaImpianNew.isGoldTheme());
        createMenu();
        //onlineGame = new OnlineGame(stage,rodaImpianNew);
        clientNew = new ClientNew(rodaImpianNew, stage, skin);
    }

    @Override
    public void update(float delta) {
        clientNew.update(delta);
    }

    private void createMenu() {
        SmallButton chat = new SmallButton(StringRes.CHAT, skin);
        SmallButton createRoom = new SmallButton(StringRes.CREATEROOM, skin);
        createRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (clientNew.isConnected()) {
                    RoomSession roomSession = new RoomSession();
                    roomSession.setPlayerList(new ArrayList<>());
                    roomSession.getPlayerList().add(rodaImpianNew.getPlayer());
                    roomSession.setQuestionNews(new ArrayList<>());
                    roomSession.setRoomUri(rodaImpianNew.getPlayer().getPicUri());
                    roomSession.setRoomNo(rodaImpianNew.getPlayer().getConnectionID());
                    for (QuestionNew questionNew : rodaImpianNew.getPreparedQuestions()) {
                        roomSession.getQuestionNews().add(questionNew);
                    }
                    rodaImpianNew.setRoomSessionID(roomSession.getRoomNo());
                    clientNew.sendObjects(roomSession);
                }
            }
        });
        onlineMenu.defaults().pad(15f);
        onlineMenu.add(chat);
        onlineMenu.add(createRoom);
        onlineMenu.pack();
        onlineMenu.setPosition(450f - onlineMenu.getWidth() / 2f, 80f);
        stage.addActor(onlineMenu);
    }
}
