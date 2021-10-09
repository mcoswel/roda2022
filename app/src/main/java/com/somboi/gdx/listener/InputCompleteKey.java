package com.somboi.gdx.listener;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.somboi.gdx.base.ModeBase;
import com.somboi.gdx.config.GameConfig;
import com.somboi.gdx.entities.Tiles;

import java.util.List;

public class InputCompleteKey implements InputProcessor {
    private final List<Tiles> completeTiles;
    private final ModeBase modeBase;
    private int cursorIndex;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    private Tiles activeTiles;

    public InputCompleteKey(List<Tiles> completeTiles, ModeBase modeBase) {
        this.completeTiles = completeTiles;
        this.modeBase = modeBase;
        completeTiles.get(cursorIndex).setColor(Color.YELLOW);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ENTER){
            modeBase.checkCompleteAnswer();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        activeTiles = completeTiles.get(cursorIndex);
        if (character == '\b') {
            decreaseIndex();
            activeTiles.deleteLetter();
            return false;
        }
        String letter = String.valueOf(character).toLowerCase();
        letter = checkDiacritic(letter);
        activeTiles.typeLetter(letter);
        increaseIndex();
        return false;
    }

    private String checkDiacritic(String letter) {
        if (letter.equals("á")){
            letter = "a_one";
        }
        if (letter.equals("à")){
            letter = "a_two";
        }
        if (letter.equals("â")){
            letter = "a_three";
        }
        if (letter.equals("ã")){
            letter = "a_four";
        }
        if (letter.equals("ç")){
            letter = "c_one";
        }
        if (letter.equals("é")){
            letter = "e_one";
        }
        if (letter.equals("è")){
            letter = "e_two";
        }
        if (letter.equals("ë")){
            letter = "e_three";
        }
        if (letter.equals("ê")){
            letter = "e_four";
        }
        if (letter.equals("í")){
            letter = "i_one";
        }
        if (letter.equals("ì")){
            letter = "i_two";
        }
        if (letter.equals("î")){
            letter = "i_three";
        }
        if (letter.equals("ï")){
            letter = "i_four";
        }
        if (letter.equals("ó")){
            letter = "o_one";
        }
        if (letter.equals("ò")){
            letter = "o_two";
        }
        if (letter.equals("ô")){
            letter = "o_three";
        }
        if (letter.equals("ö")){
            letter = "o_four";
        }
        if (letter.equals("õ")){
            letter = "o_five";
        }
        if (letter.equals("ú")){
            letter = "u_one";
        }
        if (letter.equals("ù")){
            letter = "u_two";
        }
        if (letter.equals("û")){
            letter = "u_three";
        }
        if (letter.equals("ü")){
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
