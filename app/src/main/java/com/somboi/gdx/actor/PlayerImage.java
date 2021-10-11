package com.somboi.gdx.actor;

import androidx.annotation.NonNull;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class PlayerImage extends Image implements Cloneable{
    private Sprite animate = null;
    private TextureRegion thisRegion;
    public PlayerImage(Texture texture) {
        super(texture);
        setUp();
    }

    public void animate() {
        this.setDrawable(new SpriteDrawable(animate));
    }

    public PlayerImage(TextureRegion region) {
        super(region);
        thisRegion = region;
        setUp();
    }

    private void setUp() {
        this.setSize(250f, 250f);
    }

    public void setAnimate(TextureRegion region) {
        animate = new Sprite(region);
    }


    public TextureRegion getThisRegion() {
        return thisRegion;
    }

    public Sprite getAnimate() {
        return animate;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
