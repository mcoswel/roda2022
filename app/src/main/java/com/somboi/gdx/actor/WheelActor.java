package com.somboi.gdx.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Logger;

public class WheelActor extends Image {
    private final TextureRegion textureRegion;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    float y1, y2;
    float deltaY;
    public WheelActor(TextureRegion textureRegion) {
        super(textureRegion);
        this.textureRegion = textureRegion;
        this.setPosition(25f*0.01f,375f*0.01f);
        this.setSize(850f*0.01f,850f*0.01f);
        this.setOrigin((850f/2)*0.01f,(850f/2)*0.01f);
        this.addListener(new DragListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                y1 =event.getStageY();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                y2 = event.getStageY();
                deltaY = Math.abs(y1-y2);
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    public float getDeltaY() {
        return deltaY;
    }
    public void resetDeltaY(){
        deltaY = 0;
    }

    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }
}
