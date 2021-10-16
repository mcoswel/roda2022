package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.gdx.base.ModeBase;
import com.somboi.rodaimpian.gdx.entities.PlayerGui;

public class FirstTurnWheel extends Image {
    private float rotations;
    private float initialRotation;
    private final Array<PlayerGui> playerGuis;
    private boolean stopRotate;
    private ModeBase modeBase;
    private final Image pointer;
    public FirstTurnWheel(TextureAtlas textureAtlas, ModeBase modeBase) {
        super(textureAtlas.findRegion("wheelturn"));
        this.modeBase = modeBase;
        this.setSize(850f,850f);
        this.setOrigin((850f/2),(850f/2));
        this.setPosition(25f,375f);
        this.playerGuis = modeBase.getPlayerGuis();

        pointer = new Image(textureAtlas.findRegion("pointer"));
        pointer.setSize(65f,120f);
        pointer.setPosition(450f-(65f/2f),1150f);
    }

    public void spin(){
        rotations = MathUtils.random(5f,9f);
        initialRotation = new Float(rotations);
    }

    public void spin(float rotations){
        this.rotations = rotations;
        initialRotation = new Float(rotations);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (rotations>0 && !stopRotate){
            this.setRotation(this.getRotation()-(rotations/initialRotation)*25f);
            rotations-=delta;
        }
        if (rotations<0 && !stopRotate){
            stopRotate = true;
            int i = 1;
            if (this.getRotation()>0 && this.getRotation()<=120){
                i=2;
            }else if (this.getRotation()>120 && this.getRotation()<=240){
                i=0;
            }
            modeBase.setActivePlayer(i);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    modeBase.animatePosition();
                    FirstTurnWheel.this.remove();
                    pointer.remove();
                    modeBase.showPlayerBoard();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            modeBase.startRound();
                        }
                    },1f);
                }
            },2f);

        }


        if (this.getRotation()<0){
            this.setRotation(360);
        }
        for (PlayerGui p: playerGuis){
            p.getImage().setRotation(this.getRotation());
        }
    }




    public Image getPointer() {
        return pointer;
    }
}
