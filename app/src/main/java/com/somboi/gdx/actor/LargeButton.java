package com.somboi.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LargeButton extends TextButton {
    public LargeButton(String text, Skin skin) {
        super(text,skin);
    }

    @Override
    public float getPrefWidth() {
        return 450f;
    }

}
