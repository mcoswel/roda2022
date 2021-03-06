package com.somboi.rodaimpian.gdx.listener;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.somboi.rodaimpian.gdx.base.ModeBase;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdx.entities.Tiles;

import java.util.List;

public class InputCompleteKey implements InputProcessor {
    private final List<Tiles> completeTiles;
    private final ModeBase modeBase;
    private int cursorIndex;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    private Tiles activeTiles;
    private boolean check;
    public InputCompleteKey(List<Tiles> completeTiles, ModeBase modeBase) {
        this.completeTiles = completeTiles;
        this.modeBase = modeBase;
        completeTiles.get(cursorIndex).setColor(Color.YELLOW);
        activeTiles = completeTiles.get(cursorIndex);
        check = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ENTER){
            modeBase.checkCompleteAnswer();
            check = true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (!check) {
            activeTiles = completeTiles.get(cursorIndex);
            if (character == '\b') {
                activeTiles.deleteLetter();
                decreaseIndex();
                return false;
            }
          //  logger.debug("Complete char "+character);
            Character typedCharacther = Character.toUpperCase(character);
            String letter = String.valueOf(character);
            letter = checkDiacritic(letter.toLowerCase());
            activeTiles.typeLetter(letter, typedCharacther);
            increaseIndex();
        }
        return false;
    }

    private String checkDiacritic(String letter) {
        if (letter.equals("??")){
            letter = "a_one";
        }
       else if (letter.equals("??")){
            letter = "a_two";
        }
        else if (letter.equals("??")){
            letter = "a_three";
        }
        else if (letter.equals("??")){
            letter = "a_four";
        }
        else     if (letter.equals("??")){
            letter = "c_one";
        }
        else       if (letter.equals("??")){
            letter = "e_one";
        }
        else       if (letter.equals("??")){
            letter = "e_two";
        }
        else        if (letter.equals("??")){
            letter = "e_three";
        }
        else       if (letter.equals("??")){
            letter = "e_four";
        }
        else        if (letter.equals("??")){
            letter = "i_one";
        }
        else       if (letter.equals("??")){
            letter = "i_two";
        }
        else       if (letter.equals("??")){
            letter = "i_three";
        }
        else       if (letter.equals("??")){
            letter = "i_four";
        }
        else      if (letter.equals("??")){
            letter = "o_one";
        }
        else        if (letter.equals("??")){
            letter = "o_two";
        }
        else      if (letter.equals("??")){
            letter = "o_three";
        }
        else     if (letter.equals("??")){
            letter = "o_four";
        }
        else       if (letter.equals("??")){
            letter = "o_five";
        }
        else     if (letter.equals("??")){
            letter = "u_one";
        }
        else    if (letter.equals("??")){
            letter = "u_two";
        }
        else      if (letter.equals("??")){
            letter = "u_three";
        }
        else if (letter.equals("??")){
            letter = "u_four";
        }

        return letter;

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void increaseIndex() {
        completeTiles.get(cursorIndex).setColor(GameConfig.VISIBLE);
        cursorIndex = MathUtils.clamp(cursorIndex + 1, 0, (completeTiles.size() - 1));
        completeTiles.get(cursorIndex).setColor(Color.YELLOW);
    }

    public void decreaseIndex() {
        completeTiles.get(cursorIndex).setColor(GameConfig.VISIBLE);
        cursorIndex = MathUtils.clamp(cursorIndex - 1, 0, (completeTiles.size() - 1));
        completeTiles.get(cursorIndex).setColor(Color.YELLOW);
        activeTiles = completeTiles.get(cursorIndex);

    }
}
