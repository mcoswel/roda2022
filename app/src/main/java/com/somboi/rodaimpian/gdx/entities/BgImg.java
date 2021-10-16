package com.somboi.rodaimpian.gdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BgImg extends Image {
    public BgImg(Texture texture) {
        super(texture);
        this.setSize(900f, 1600f);
        this.setPosition(0,0);
    }
}
