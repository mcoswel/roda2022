package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class StatusLabel extends Label {
    public StatusLabel(Skin skin) {
        super("", skin);
    }

    public void updateText(String text) {
        setText(text);
        pack();
        setWrap(true);
        setAlignment(Align.center);
        setPosition(25f, 888f);
    }

    @Override
    public float getPrefWidth() {
        return 850f;
    }
}
