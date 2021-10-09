package com.somboi.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class ChatBubble extends Label {
    public ChatBubble(CharSequence text, Skin skin, int position) {
        super(text, skin,"chat");
        this.setAlignment(Align.center);
        this.setPosition(0, 600f);
        if (position==1) {
            this.setPosition(450f - this.getWidth() / 2f, 600);
        }else if (position==2){
            this.setPosition(900f-this.getWidth(), 600);
        }
        this.setWrap(true);
        this.pack();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ChatBubble.this.remove();
            }
        },2f);
    }

    @Override
    public float getPrefWidth() {
        return 300f;
    }

}
