package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Logger;

public class DummyWheel extends Image {
    private final TextureRegion textureRegion;
    public DummyWheel(TextureRegion textureRegion) {
        super(textureRegion);
        this.textureRegion = textureRegion;
        this.setPosition(25f*0.01f,375f*0.01f);
        this.setSize(850f*0.01f,850f*0.01f);
        this.setOrigin((850f/2)*0.01f,(850f/2)*0.01f);

    }

}
