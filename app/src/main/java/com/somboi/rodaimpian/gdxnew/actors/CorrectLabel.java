package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;

public class CorrectLabel extends Label {
    public CorrectLabel(CharSequence text, Skin skin) {
        super(text, skin,"spin");
        pack();
        setPosition(450f - getWidth() / 2f, 800f);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                remove();
            }
        }, 1.5f);

    }
}
