package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.activities.RodaImpianNew;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;

public class SubMenu extends Dialog {
    private final RodaImpianNew rodaImpianNew;
    public SubMenu(RodaImpianNew rodaImpianNew, Skin skin) {
        super(StringRes.PAUSEMENU, skin);
        this.rodaImpianNew = rodaImpianNew;
        getContentTable().defaults().pad(20f);
        SmallButton offMusic = new SmallButton(StringRes.MUSICOFF, skin);
        offMusic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpianNew.getMusic().stop();
                hide();
            }
        });
        SmallButton keluar = new SmallButton(StringRes.EXIT2, skin);

        keluar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
                keluarFunc();
            }
        });
        SmallButton cancel = new SmallButton(StringRes.BACK, skin);
        cancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        getContentTable().add(offMusic).row();
        getContentTable().add(keluar).row();
        getContentTable().add(cancel).row();
    }

    public void keluarFunc(){
        rodaImpianNew.restart();
    }
}
