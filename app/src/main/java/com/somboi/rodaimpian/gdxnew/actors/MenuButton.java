package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuButton extends TextButton {
    public MenuButton(String text, Skin skin) {
        super(text, skin);
    }

    @Override
    public float getPrefWidth() {
        return 500f;
    }

    @Override
    public float getPrefHeight() {
        return 100f;
    }
}
