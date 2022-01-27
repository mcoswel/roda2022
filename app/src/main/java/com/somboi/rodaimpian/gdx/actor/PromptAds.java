package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;

public class PromptAds extends Dialog {
    public PromptAds(Skin skin) {
        super(StringRes.INFO, skin,"clear");
        Label text = new Label(StringRes.ADSPROMPT, skin, "chat"){
            @Override
            public float getPrefWidth() {
                return 600f;
            }
        };

        text.setWrap(true);
        text.pack();
        this.text(text);
        this.button(StringRes.WATCHADS, true);
        this.button(StringRes.NO, false);

    }
}
