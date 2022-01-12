package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bulb extends Image {
    private final String colorMove = "_move";
    private final String type;
    public Bulb(TextureRegion region, String type) {
        super(region);
        this.type = type;
        this.setSize(150f, 150f);
    }


    public String getType() {
        return type;
    }
}
