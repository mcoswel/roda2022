package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SlotResultLabel extends Label {
    public SlotResultLabel(Skin skin) {
        super("", skin,"spin");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        pack();
        setPosition(450f-getWidth()/2f, 1400f);
    }
}
