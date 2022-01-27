package com.somboi.rodaimpian.gdxnew.interfaces;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.gdxnew.assets.GameConfig;
import com.somboi.rodaimpian.gdxnew.actors.PlayerMenu;
import com.somboi.rodaimpian.gdxnew.actors.TileBase;

public class KeyListen extends InputListener {
    private final Array<TileBase>incomplete;
    private int counter;
    private final PlayerMenu playerMenu;
    public KeyListen(Array<TileBase> incomplete, PlayerMenu playerMenu) {
        this.incomplete = incomplete;
        this.playerMenu = playerMenu;
        incomplete.get(counter).setColor(Color.GREEN);
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if (keycode == Input.Keys.BACKSPACE || keycode == Input.Keys.DEL){
            incomplete.get(counter).setColor(GameConfig.NORMAL_COLOR);
            if (counter>0){
                counter--;
                incomplete.get(counter).setColor(Color.GREEN);
            }else{
                counter = incomplete.size-1;
                incomplete.get(counter).setColor(Color.GREEN);
            }
        }
        if (keycode == Input.Keys.ENTER){
            playerMenu.checkAnswers();
        }
        return super.keyDown(event, keycode);
    }

    @Override
    public boolean keyTyped(InputEvent event, char character) {

        if (incomplete.get(counter).typeLetter(String.valueOf(character))){
            incomplete.get(counter).setColor(GameConfig.NORMAL_COLOR);
            incomplete.get(counter).setLetter(String.valueOf(character));
            counter++;
            if (counter==incomplete.size){
                counter = 0;
            }
            incomplete.get(counter).setColor(Color.GREEN);
        }
        return super.keyTyped(event, character);
    }
}
