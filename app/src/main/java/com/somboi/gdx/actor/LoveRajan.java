package com.somboi.gdx.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;

public class LoveRajan extends Image {
private final Vector2 rajanPos;
private final float distance;
    public LoveRajan(TextureRegion region, Vector2 rajanPos, float humanPos) {
        super(region);
        this.rajanPos = rajanPos;
        distance = (humanPos+125f) - rajanPos.x;
        this.setSize(46f, 43f);
        this.setOrigin(-5f,-5f);
    }

    public void animate(Stage stage){
        this.setPosition(rajanPos.x+125f, rajanPos.y+100);
        this.addAction(new ParallelAction(Actions.moveBy(distance, 0f,4f),Actions.rotateBy(1080,4f)));
        stage.addActor(this);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                LoveRajan.this.remove();
            }
        },5f);
    }

}
