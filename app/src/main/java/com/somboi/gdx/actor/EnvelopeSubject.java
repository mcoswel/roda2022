package com.somboi.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class EnvelopeSubject extends Table {
    public EnvelopeSubject(Skin skin, String subjects, float xPosition) {
        Label subject = new Label(subjects,skin, "bonus");
        subject.setAlignment(Align.center);
        subject.pack();
        subject.setWrap(true);
        this.add(subject).width(230f);
        this.pack();
        this.setPosition(xPosition-this.getWidth()/2f,800f);
    }
}
