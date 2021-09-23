package com.somboi.gdx.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class WheelActor extends Image {
    private final Texture textureRegion;
    public WheelActor(Texture textureRegion) {
        super(textureRegion);
        this.textureRegion = textureRegion;
        this.setPosition(25f*0.01f,375f*0.01f);
        this.setSize(850f*0.01f,850f*0.01f);
        this.setOrigin((850f/2)*0.01f,(850f/2)*0.01f);

    }
}
