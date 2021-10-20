package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.screen.LoadingScreen;

public class MenuPrompt extends Dialog {
    private final RodaImpian rodaImpian;
    public MenuPrompt(RodaImpian rodaImpian, Skin skin) {
        super(StringRes.PAUSEMENU, skin);
        this.rodaImpian = rodaImpian;
        this.getContentTable().defaults().pad(20f);
        LargeButton mainmenu = new LargeButton(StringRes.MAINMENU, skin);
        mainmenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.setScreen(new LoadingScreen(rodaImpian));
            }
        });

        LargeButton exit = new LargeButton(StringRes.EXIT, skin);
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        LargeButton back = new LargeButton(StringRes.BACK, skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MenuPrompt.this.clear();
                MenuPrompt.this.remove();            }
        });

        this.getContentTable().defaults().pad(20f);
        this.getContentTable().add(mainmenu).row();
        this.getContentTable().add(back).row();
        this.getContentTable().add(exit).row();
    }


}
