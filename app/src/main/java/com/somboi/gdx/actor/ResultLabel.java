package com.somboi.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class ResultLabel extends Label {

    public ResultLabel(CharSequence text, Skin skin) {
        super(text, skin,"wheel");
        this.setPosition(450f-this.getWidth()/2f,100f);
        this.setAlignment(Align.center);
        this.setColor(new Color(1f,1f,1f,0f));
        ParallelAction parallelAction = new ParallelAction(Actions.fadeIn(1f), Actions.moveBy(0f, 50f,1f));
        this.addAction(parallelAction);
    }

}
