package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.somboi.rodaimpian.gdx.assets.StringRes;

public class FBPrompt extends Dialog {

    public FBPrompt(Skin skin) {
        super(StringRes.LOGINFB, skin);
        this.getContentTable().defaults().pad(10f);
        Label label = new Label(StringRes.LOGINFBFORONLINE,skin){
            @Override
            public float getPrefWidth() {
                return 700f;
            }
        };
        label.pack();
        label.setWrap(true);
        this.text(label);
        this.button(StringRes.OK, true);
        this.button(StringRes.NO, false);
    }
}
