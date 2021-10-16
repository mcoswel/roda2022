package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

public class NameField extends TextField {

    public NameField(String text, Skin skin) {
        super(text, skin);
        this.setAlignment(Align.center);
        this.setMaxLength(15);
    }

    @Override
    public float getPrefWidth() {
        return 450f;
    }
}
