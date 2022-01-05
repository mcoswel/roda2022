package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;

public class ChatBubble {
    private final Skin skin;
    private Vector2 position;
    private final Queue<BubbleTalk> bubbleTalkQueue = new Queue<>();
    private final Stage stage;
    private float timer = 2f;
    public ChatBubble(int playerIndex, Stage stage, Skin skin) {
        this.skin = skin;
        this.stage = stage;
        position = new Vector2(0, 580f);
        if (playerIndex == 1) {
            position = new Vector2(300f, 580f);
        } else if (playerIndex == 2) {
            position = new Vector2(600, 580f);
        }
    }

    public void createBubble(String text) {
        bubbleTalkQueue.addLast(new BubbleTalk(text));
    }

    public void updateChat(float delta) {
        if (!bubbleTalkQueue.isEmpty()){
            stage.addActor(bubbleTalkQueue.first());
            timer-=delta;
            if (timer<=0){
                timer = 1.25f;
                bubbleTalkQueue.first().remove();
                bubbleTalkQueue.removeFirst();
            }
        }
    }


    private class BubbleTalk extends Label {
        public BubbleTalk(CharSequence text) {
            super(text, skin, "chat");
            setPosition(position.x, position.y);
            setWrap(true);
            setAlignment(Align.center);
            pack();
        }

        @Override
        public float getPrefWidth() {
            return 300f;
        }
    }
}

