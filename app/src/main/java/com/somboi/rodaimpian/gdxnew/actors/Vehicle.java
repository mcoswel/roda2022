package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Vehicle extends Image {
    private int count = 0;
    private final String type;
    public Vehicle(TextureRegion region, String type) {
        super(region);
        this.type = type;
        this.setSize(200f, 350f);
        if (type.equals("firetruck")) {
            this.setPosition(133f, 78-218f);
        } else if (type.equals("ambulance")) {
            this.setPosition(351f, 78f-218f);
        } else {
            this.setPosition(569f, 78f-218f);
        }
    }

    public void move() {
        this.addAction(Actions.moveBy(0, 218f, 2f));
        count++;
    }

    public int getCount() {
        return count;
    }

    public String getType() {
        return type;
    }
}
