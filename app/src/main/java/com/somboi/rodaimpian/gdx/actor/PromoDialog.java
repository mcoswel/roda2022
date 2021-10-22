package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.assets.StringRes;

public class PromoDialog extends Dialog {
    public PromoDialog(Skin skin, TextureRegion textureRegion) {
        super("Promo", skin);
        PlayerImage playerImage = new PlayerImage(textureRegion);
        this.getContentTable().defaults().pad(20f);
        Label label = new Label("Scrabble Bahasa Melayu",skin);
        this.getContentTable().add(label).row();
        this.getContentTable().add(playerImage).row();
        this.button("PlayStore", true);
        this.button("Tak Nak!", false);
    }
}

