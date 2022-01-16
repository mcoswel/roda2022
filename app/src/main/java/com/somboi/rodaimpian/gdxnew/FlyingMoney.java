package com.somboi.rodaimpian.gdxnew;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;

public class FlyingMoney extends Image {
    public FlyingMoney(TextureRegion region, Vector2 position) {
        super(region);
        this.setSize(69f, 69f);
        this.setPosition(position.x + 125f, position.y + 200f);
        this.addAction(
                new ParallelAction(
                        Actions.moveBy(0, 400, 4f),
                        Actions.rotateBy(1080f, 4f),
                        Actions.fadeOut(5f)
                )
        );

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                FlyingMoney.this.remove();
            }
        }, 5f);
    }
}
