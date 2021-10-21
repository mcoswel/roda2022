package com.somboi.rodaimpian.gdx.online.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class KickBtn extends TextButton {
    public KickBtn(String text, Skin skin, int index) {
        super(text, skin);
        this.pack();
        if (index == 0){
            this.setPosition(0f-20f,426f);
        }else if (index == 1){
            this.setPosition(300f-20f,426f);
        }else{
            this.setPosition(600f-20f,426f);
        }

    }

    @Override
    public float getPrefWidth() {
        return 300f;
    }

}

