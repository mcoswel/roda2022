package com.somboi.gdx.actor;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Sparkling extends Actor {
    private final Animation<TextureRegion>sparkling;
    private float stateTime;
    public Sparkling(Texture tx) {
        TextureRegion[][] tmp = TextureRegion.split(tx,
                tx.getWidth() / 8,
                tx.getHeight() / 4);
        TextureRegion[] frames = new TextureRegion[8 * 4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        sparkling = new Animation<TextureRegion>(0.025f, frames);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sparkling.getKeyFrame(stateTime, true), 350f, 1130f,200,200f );
    }

    @Override
    public void act(float delta) {
        stateTime+=delta;
    }
}
