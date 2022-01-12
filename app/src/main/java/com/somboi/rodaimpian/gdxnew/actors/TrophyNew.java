package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;

public class TrophyNew extends Image {
    private final Vector2 position;

    public TrophyNew(Vector2 position, TextureRegion region) {
        super(region);
        this.position = position;
        this.setSize(118f,162f);
        this.setPosition(position.x+66, position.y+280f);
        SequenceAction sequenceAction = new SequenceAction(Actions.fadeOut(0.5f), Actions.fadeIn(0.5f));
        addAction(Actions.forever(sequenceAction));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                remove();
            }
        },4f);
    }
}
