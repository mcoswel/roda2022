package com.somboi.rodaimpian.gdxnew.interfaces;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.somboi.rodaimpian.gdxnew.actors.TileBase;

public class KeyListen extends InputListener {
    private final Array<TileBase>incomplete;
    private int counter;
    private final Logger logger = new Logger(this.getClass().getName(),3);
    public KeyListen(Array<TileBase> incomplete) {
        this.incomplete = incomplete;
    }

    @Override
    public boolean keyTyped(InputEvent event, char character) {
        if (incomplete.get(counter).typeLetter(String.valueOf(character))){
            counter++;
            if (counter==incomplete.size){
                counter = 0;
            }
            logger.debug("key typed");
        }
        return super.keyTyped(event, character);
    }
}
