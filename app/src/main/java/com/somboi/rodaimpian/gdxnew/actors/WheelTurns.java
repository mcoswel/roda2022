package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.gdx.entities.PlayerGui;
import com.somboi.rodaimpian.gdxnew.games.BaseGame;
import com.somboi.rodaimpian.gdxnew.games.SinglePlayer;

public class WheelTurns extends Image {
    private float rotations;
    private float initialRotation;
    private boolean stopRotate;
    private final Array<PlayerGuis> playerGuis;
    private final Image pointer;
    private final BaseGame baseGame;

    public WheelTurns(TextureRegion wheelRegion, TextureRegion needle, Array<PlayerGuis> playerGuis, BaseGame baseGame) {
        super(wheelRegion);
        this.playerGuis = playerGuis;
        this.setSize(850f, 850f);
        this.setOrigin((850f / 2), (850f / 2));
        this.setPosition(25f, 375f);
        this.baseGame = baseGame;
        pointer = new Image(needle);
        pointer.setSize(65f, 120f);
        pointer.setPosition(450f - (65f / 2f), 1150f);
    }

    public void spin() {
        rotations = MathUtils.random(5f, 9f);
        initialRotation = new Float(rotations);
    }

    @Override
    public void act(float delta) {
        if (rotations > 0 && !stopRotate) {
            this.setRotation(this.getRotation() - (rotations / initialRotation) * 25f);
            rotations -= delta;
        }
        if (rotations < 0 && !stopRotate) {
            stopRotate = true;
            int i = 1;
            if (this.getRotation() > 0 && this.getRotation() <= 120) {
                i = 2;
            } else if (this.getRotation() > 120 && this.getRotation() <= 240) {
                i = 0;
            }
            playerGuis.get(i).getPlayerNew().setTurn(true);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    baseGame.animatePicture();
                    remove();
                    pointer.remove();

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            //modeBase.startRound();
                        }
                    }, 1f);
                }
            }, 2f);

            if (this.getRotation()<0){
                this.setRotation(360);
            }
            for (PlayerGuis p: playerGuis){
                p.getProfilePic().setRotation(this.getRotation());
            }

        }
    }
}
