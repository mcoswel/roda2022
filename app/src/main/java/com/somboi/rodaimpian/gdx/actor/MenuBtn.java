package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuBtn extends TextButton {

    public MenuBtn(String text, Skin skin) {
        super(text, skin,"smallmenu");
        this.getStyle().font.getData().setScale(1.5f);
    }

    @Override
    public float getPrefWidth() {
        return 300f;
    }

    @Override
    public float getPrefHeight() {
        return 80f;
    }
}
