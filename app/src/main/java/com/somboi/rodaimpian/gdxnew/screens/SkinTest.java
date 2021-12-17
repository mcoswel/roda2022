package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.actors.MenuButton;
import com.somboi.rodaimpian.gdxnew.actors.SmallButton;

public class SkinTest extends BaseScreenNew {
    public SkinTest(RodaImpianNew rodaImpianNew) {
        super(rodaImpianNew);
        Gdx.input.setInputProcessor(stage);
        MenuButton one = new MenuButton(StringRes.VOKAL, skin);
        MenuButton two = new MenuButton(StringRes.PUTAR, skin);
        MenuButton three = new MenuButton(StringRes.LENGKAPKAN, skin);
        Table table = new Table();
        table.defaults().pad(10f);
        table.setFillParent( true);
        table.center();

        SmallButton sone = new SmallButton(StringRes.VOKAL, skin);
        SmallButton stwo = new SmallButton(StringRes.PUTAR, skin);
        stwo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpianNew.setScreen(new SpinScreen(rodaImpianNew));
            }
        });
        SmallButton sthree = new SmallButton(StringRes.LENGKAPKAN, skin);

        table.add(one).row();
        table.add(two).row();
        table.add(three).row();
        table.add(sone).row();
        table.add(stwo).row();
        table.add(sthree).row();
        stage.addActor(table);

    }

}
