package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class UltraSmallBtn extends TextButton {
    public UltraSmallBtn(String text, Skin skin) {
        super(text, skin,"small");
    }

    @Override
    public float getPrefWidth() {
        return 280f;
    }

    @Override
    public float getPrefHeight() {
        return 65f;
    }
}
