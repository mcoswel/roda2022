package com.somboi.gdx.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Envelopes extends Image {
    private TextureRegion opened;
    public Envelopes(TextureAtlas textureAtlas, int index) {
        TextureRegion region = textureAtlas.findRegion("6_blueenvel");
        opened = textureAtlas.findRegion("6_blueenvelopen");

        if (index == 1){
            region = textureAtlas.findRegion("6_greenenvel");
            opened = textureAtlas.findRegion("6_greenenvelopen");
        }else if (index == 2){
            region = textureAtlas.findRegion("6_yellowenvel");
            opened = textureAtlas.findRegion("6_yellowenvelopen");

        }
        this.setSize(250f,252f);
        this.setDrawable(new SpriteDrawable(new Sprite(region)));
    }

    public void opened(){
        this.setDrawable(new SpriteDrawable(new Sprite(opened)));

    }

}
