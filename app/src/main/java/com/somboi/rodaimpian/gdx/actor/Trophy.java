package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Trophy extends Image {

    public Trophy(TextureAtlas textureAtlas, int rank, Vector2 playerPos) {
        TextureRegion region = textureAtlas.findRegion("bronze");
        if (rank == 0) {
            region = textureAtlas.findRegion("gold");
            this.addAction(BlinkActions.blinkAction());
        } else if (rank == 1) {
            region = textureAtlas.findRegion("silver");
        }
        this.setDrawable(new SpriteDrawable(new Sprite(region)));
        this.setSize(118f,162f);
        this.setPosition(playerPos.x+66, playerPos.y+280f);


    }

}
