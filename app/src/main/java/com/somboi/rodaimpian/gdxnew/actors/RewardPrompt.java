package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.somboi.rodaimpian.activities.RodaImpianNew;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;

public class RewardPrompt extends Dialog {
    public RewardPrompt(Skin skin, RodaImpianNew rodaImpianNew) {
        super(StringRes.SORRY, skin);
        getContentTable().defaults().pad(20f);
        Label label = new Label(StringRes.ADSPROMPT, skin);
        label.setAlignment(Align.center);
        label.pack();
        label.setWrap(true);

        UltraSmallBtn lihat = new UltraSmallBtn(StringRes.WATCHADS, skin);
        lihat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpianNew.showRewarded();
                hide();
            }
        });
        UltraSmallBtn tidak = new UltraSmallBtn(StringRes.NO3, skin);
        tidak.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        getContentTable().add(label).center().width(800f).row();
        Table table = new Table();
        table.defaults().pad(15f);
        table.add(lihat);
        table.add(tidak);
        getContentTable().add(table).center();

    }

    @Override
    public float getPrefWidth() {
        return 850f;
    }
}
