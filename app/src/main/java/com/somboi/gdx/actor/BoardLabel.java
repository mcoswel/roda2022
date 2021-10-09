package com.somboi.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class BoardLabel extends Label {
    public BoardLabel(CharSequence text, Skin skin) {
        super(text, skin,"player");
        this.setAlignment(Align.center);
    }


}
