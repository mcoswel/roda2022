package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.somboi.rodaimpian.gdxnew.games.BaseGame;

public class ClockTimer extends Label {
    private float counter = 45f;
    private boolean stop;
    private final BaseGame baseGame;

    public ClockTimer(Skin skin, BaseGame baseGame) {
        super("0:45", skin, "score");
        this.baseGame = baseGame;
        setPosition(880f - getWidth(), 888f);
    }

    @Override
    public void act(float delta) {
        if (!stop) {
            counter -= delta;
            int sec = (int) counter;
            if (counter >= 10) {
                setText("0:" + sec);
            } else {
                setText("0:0" + sec);
            }
            if (sec == 5 || sec == 4 || sec == 3 || sec == 2 || sec == 1 || sec == 0) {
                baseGame.playClockSound();
            }

            if (sec <= 0) {
                stop = true;
                baseGame.loseBonus();
            }
        }
    }
}
