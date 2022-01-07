package com.somboi.rodaimpian.gdxnew.interfaces;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdxnew.actors.TileBase;

public class KeyListen extends InputListener {
    private final Array<TileBase>incomplete;
    private int counter;
    public KeyListen(Array<TileBase> incomplete) {
        this.incomplete = incomplete;
        incomplete.get(counter).setColor(Color.GREEN);
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if (keycode == Input.Keys.BACKSPACE || keycode == Input.Keys.DEL){
            if (counter>0){
                counter--;
                incomplete.get(counter).setColor(Color.GREEN);
            }
        }
        return super.keyDown(event, keycode);
    }

    @Override
    public boolean keyTyped(InputEvent event, char character) {

        if (incomplete.get(counter).typeLetter(String.valueOf(character))){
            incomplete.get(counter).setColor(GameConfig.NORMAL_COLOR);
            counter++;
            if (counter==incomplete.size){
                counter = 0;
            }
            incomplete.get(counter).setColor(Color.GREEN);
        }
        return super.keyTyped(event, character);
    }
}
