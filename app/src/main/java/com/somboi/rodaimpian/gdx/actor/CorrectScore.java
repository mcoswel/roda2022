package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.gdx.config.GameConfig;

public class CorrectScore extends Label {


    public CorrectScore(CharSequence text, Skin skin) {
        super(text, skin, "wheel");
        this.addAction(Actions.forever(GameConfig.BLINKS));
        this.setAlignment(Align.center);

    }
    public void show(Group tilesGroup){
        this.pack();
        this.setPosition(450f - this.getWidth() / 2f, 850f);
        tilesGroup.addActor(this);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                CorrectScore.this.remove();
            }
        },3f);
    }
    public void showBonusString(Group tilesGroup){
        this.remove();
        this.pack();
        this.setPosition(450f - this.getWidth() / 2f, 850f);
        tilesGroup.addActor(this);
    }


}
