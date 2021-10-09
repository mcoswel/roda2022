package com.somboi.gdx.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class PlayerImage extends Image {
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
        this.addListener(new DragListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                animate();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void flip(){

    }
}
