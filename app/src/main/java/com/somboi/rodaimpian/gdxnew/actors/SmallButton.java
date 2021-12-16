package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SmallButton extends TextButton {
    public SmallButton(String text, Skin skin) {
        super(text, skin,"small");
    }

    @Override
    public float getPrefWidth() {
        return 400f;
    }
}
