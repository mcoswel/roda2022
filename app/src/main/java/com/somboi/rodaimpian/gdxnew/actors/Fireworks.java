package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.gdx.config.GameConfig;

public class Fireworks extends Actor {
    private final int FIRE_COLS = 6, FIRE_ROWS = 5;
    private final Animation<TextureRegion> fireAnime;
    private float stateTime;
    public Fireworks(Texture winTexture) {
        TextureRegion[][] tmp2 = TextureRegion.split(winTexture,
                winTexture.getWidth() / FIRE_COLS,
                winTexture.getHeight() / FIRE_ROWS);
        TextureRegion[] winFrames2 = new TextureRegion[FIRE_COLS * FIRE_ROWS];
        int index2 = 0;
        for (int i = 0; i < FIRE_ROWS; i++) {
            for (int j = 0; j < FIRE_COLS; j++) {
                winFrames2[index2++] = tmp2[i][j];
            }
        }
        fireAnime = new Animation<>(0.025f, winFrames2);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                remove();
            }
        },3f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(fireAnime.getKeyFrame(stateTime, true), 450f-GameConfig.SCWIDTH/2f, 600f, GameConfig.SCWIDTH,  GameConfig.SCWIDTH);
    }

    @Override
    public void act(float delta) {
        stateTime+=delta;
    }
}
