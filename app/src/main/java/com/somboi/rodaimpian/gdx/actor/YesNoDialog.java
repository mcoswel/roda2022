package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;

public class YesNoDialog extends Dialog {
    public YesNoDialog(String infoText, Skin skin) {
        super(StringRes.INFO, skin);
        Label text = new Label(infoText, skin, "whitearial"){
            @Override
            public float getPrefWidth() {
                return 600f;
            }
        };

        text.setWrap(true);
        text.pack();
        this.text(text);
        this.button(StringRes.YES, true);
        this.button(StringRes.NOTWO, false);
    }
}
