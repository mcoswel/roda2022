package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.actor.StatusLabel;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.actors.SmallButton;
import com.somboi.rodaimpian.gdxnew.games.ClientNew;

public class OnlineScreen extends BaseScreenNew{
    private final Table onlineMenu = new Table();
    public OnlineScreen(RodaImpianNew rodaImpianNew) {
        super(rodaImpianNew);
        Gdx.input.setInputProcessor(stage);
        actorFactory.createGameBgBlur(rodaImpianNew.isGoldTheme());
        createMenu();
        ClientNew clientNew = new ClientNew(rodaImpianNew, stage, skin);

    }



    private void createMenu() {
        SmallButton chat = new SmallButton(StringRes.CHAT, skin);
        SmallButton createRoom = new SmallButton(StringRes.CREATEROOM, skin);
        onlineMenu.defaults().pad(15f);
        onlineMenu.add(chat);
        onlineMenu.add(createRoom);
        onlineMenu.pack();
        onlineMenu.setPosition(450f-onlineMenu.getWidth()/2f, 80f);
        stage.addActor(onlineMenu);
    }
}
