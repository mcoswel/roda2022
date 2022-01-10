package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;

public class Confetti extends Actor {
    private final Animation<TextureRegion> animation;
    private float stateTime;
    private Vector2 position;
    public Confetti(Texture confettiTexture, int playerIndex) {
        TextureRegion[][] tmp = TextureRegion.split(confettiTexture,
                confettiTexture.getWidth() / 8,
                confettiTexture.getHeight() / 8);
        TextureRegion[] winFrames = new TextureRegion[8 * 8];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                winFrames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(0.025f, winFrames);
        if (playerIndex == 0){
            position = new Vector2(150f-250f, 160f);
        }else if (playerIndex ==1 ){
            position = new Vector2(450f-250f, 160f);
        }else{
            position = new Vector2(750f-250f, 160f);
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                remove();
            }
        },3f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(animation.getKeyFrame(stateTime,true), position.x, position.y);
    }

    @Override
    public void act(float delta) {
        stateTime+=delta;
    }
}
