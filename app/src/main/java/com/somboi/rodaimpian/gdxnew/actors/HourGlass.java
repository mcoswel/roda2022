package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HourGlass extends Actor {
    private final Animation<TextureRegion> animation;
    private float stateTime;
    private float rotation;
    private Vector2 position = new Vector2(-600f, -600f);

    public HourGlass(Texture texture) {
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / 4,
                texture.getHeight() / 2);
        TextureRegion[] frames = new TextureRegion[4 * 2];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(0.2f, frames);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(animation.getKeyFrame(stateTime, true), position.x + 125f, position.y + 250, 25f, 37.5f, 50f, 75f, 1, 1, rotation);
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        rotation += 10 * delta;
        if (rotation >= 360) {
            rotation = 0;
        }
    }

    public void changePos(PlayerGuis activePlayerGui) {
        position = new Vector2(activePlayerGui.getPosition().x - 25f, activePlayerGui.getPosition().y);
    }
}
