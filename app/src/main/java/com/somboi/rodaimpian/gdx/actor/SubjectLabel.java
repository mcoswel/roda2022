package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class SubjectLabel extends Label {
    public SubjectLabel(CharSequence text, Skin skin) {
        super(text, skin, "whitearial");
        this.setPosition(450f-this.getWidth()/2f,978f);
        this.setAlignment(Align.center);
        this.addAction(Actions.fadeOut(0f));
        this.addAction(Actions.fadeIn(2f));

    }
}
