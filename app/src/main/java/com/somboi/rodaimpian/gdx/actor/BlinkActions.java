package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class BlinkActions {
    public static RepeatAction blinkAction() {
        return Actions.repeat(4, new SequenceAction(
                Actions.color(new Color(1f, 1f, 1f, 0f), 0.5f),
                Actions.color(new Color(1f, 1f, 1f, 1f), 0.5f)));
    }
}
