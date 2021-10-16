package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.somboi.rodaimpian.gdx.assets.GameSound;

public class TimerLimit extends Label {
    private float limit = 60f;
    private boolean start;
    private boolean changeTurn;
    private final GameSound gameSound;
    private boolean playClock;
    public TimerLimit(Skin skin, GameSound gameSound) {
        super("0:59", skin);
        this.setPosition(900f - this.getWidth() - 10f, 972f);
        this.gameSound = gameSound;
        this.setColor(Color.GREEN);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (start && limit>0) {
            limit -= delta;
            if (limit>10) {
                this.setText("0:" + (int) limit);
            }else{
                this.setText("0:0" + (int) limit);
            }

            int limitInt = (int)limit;
            if (limitInt==4 && !playClock){
                gameSound.playClockSound();
                this.setColor(Color.RED);
                playClock = true;
            }
            if (limitInt==0 && playClock) {
                gameSound.stopClockSound();
                gameSound.playWrong();
                playClock = true;
                changeTurn = true;
            }
        }
    }

    public void start(){
        start = true;
    }

    public void stop(){
        start = false;
    }

    public void reset(){
        this.setColor(Color.GREEN);
        limit = 60f;
        changeTurn = false;
        playClock = false;
    }

    public boolean isChangeTurn() {
        return changeTurn;
    }
}
