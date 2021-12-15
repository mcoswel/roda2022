package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BodyImage extends Image {
    private final Body body;

    public BodyImage(TextureRegion region, Body body) {
        super(region);
        this.body = body;
        setPosition(body.getPosition().x, body.getPosition().y);
    }

    public BodyImage(Texture texture, Body body) {
        super(texture);
        this.body = body;
    }

    @Override
    public void act(float delta) {
        setRotation(body.getAngle() * MathUtils.radDeg);
    }
}
