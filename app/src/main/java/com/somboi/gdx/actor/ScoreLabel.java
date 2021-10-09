package com.somboi.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.somboi.gdx.base.ModeBase;
import com.somboi.gdx.entities.Player;

public class ScoreLabel extends Label {
    private final Player player;
    public ScoreLabel(CharSequence text, Skin skin, Player player) {
        super(text, skin, "player");
        this.setAlignment(Align.center);
        this.setColor(Color.GOLDENROD);
        this.player = player;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (player.boardScore != player.currentScore) {
            if (player.boardScore < player.currentScore) {
                player.boardScore += delta * 1000;
            } else if (player.boardScore > player.currentScore) {
                player.boardScore = player.currentScore;
            }
        }
        this.setText("$" + player.boardScore);
    }
}
