package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class StatusLabel extends Label {

    public StatusLabel(CharSequence text, Skin skin) {
        super(text, skin);
        this.setPosition(450f-this.getWidth()/2f, 800f);
        this.setColor(new Color(1f,1f,1f,0.3f));
        this.setWidth(800f);
        this.setWrap(true);
        //SequenceAction sequenceAction = new SequenceAction(Actions.color(new Color(1f,1f,1f,0f),0.5f), Actions.color(Color.GREEN,0.5f));
//        this.addAction(Actions.forever(sequenceAction));
    }

   public void changeStatus(String text){
        setText(text);
        this.pack();
       this.setPosition(450f-this.getWidth()/2f, 600f);
   }
}
