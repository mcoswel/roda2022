package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;

public class ErrorDialog extends Dialog {

    public ErrorDialog(String errorText, Skin skin) {
        super(StringRes.ERRORTITLE, skin);
        Label text = new Label(errorText, skin, "whitearial"){
            @Override
            public float getPrefWidth() {
                return 600f;
            }
        };

        text.setWrap(true);
        this.text(text);
        this.button(StringRes.OK);
    }

    @Override
    public float getPrefWidth() {
        return 650f;
    }
}
