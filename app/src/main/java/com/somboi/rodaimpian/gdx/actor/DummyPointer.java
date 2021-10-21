package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class DummyPointer extends Image {
    public DummyPointer(TextureRegion texture) {
        super(texture);
        this.setSize(0.55f, 0.9f);
        this.setPosition(4.5f, 12f-0.2f);
        // this.setOrigin(this.getWidth()/2,this.getHeight()-0.2f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
