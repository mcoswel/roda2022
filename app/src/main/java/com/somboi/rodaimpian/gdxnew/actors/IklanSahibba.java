package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.somboi.rodaimpian.activities.RodaImpianNew;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;

public class IklanSahibba extends Dialog {
    public IklanSahibba(Skin skin, RodaImpianNew rodaImpianNew) {
        super("Wordle Bahasa Malaysia", skin);
        Texture texture = new Texture(Gdx.files.internal("sahiba.png"));
        Image image = new Image(texture);
        getContentTable().defaults().pad(15f);


        Label label = new Label("Admin menjemput anda mencuba permainan Sahibba yang boleh menyokong Offline & Online hingga 4 Pemain ", skin);
        label.setAlignment(Align.center);
        label.pack();
        label.setWrap(true);

        UltraSmallBtn lihat = new UltraSmallBtn("PlayStore", skin);
        lihat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpianNew.openPlayStore();
                hide();
            }
        });

        Table first = new Table();
        first.add(image).size(150f,150f);
        first.add(label).width(650f);

        UltraSmallBtn tidak = new UltraSmallBtn(StringRes.NO3, skin);
        tidak.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });

        getContentTable().add(first).center().width(800f).row();
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
