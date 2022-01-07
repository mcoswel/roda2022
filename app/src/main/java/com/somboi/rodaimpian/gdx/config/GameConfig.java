package com.somboi.rodaimpian.gdx.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;

public class GameConfig {
    public static final float SCWIDTH = 900f;
    public static final float SCHEIGHT = 1600f;
    public static final Array<Character> GROUP_CONS_ONE = new Array<>(new Character[]{'S','N','M','R','T','L','K','P','G'});
    public static final Array<Character> VOCALS = new Array<>(new Character[]{'A','E','I','O','U'});
    public static final Array<Character> GROUP_CONS_TWO = new Array<>(new Character[]{'B','C','D','F','H','J'});
    public static final Array<Character> GROUP_CONS_THREE = new Array<>(new Character[]{'Y','Z','V','W','X','Q'});
    public static final Array<Character> CONSONANTS = new Array<>(new Character[]{'B','C','D','F','G','H','J','K','L','M','N','P','Q','R','S','T','V','W','X','Y','Z'});
    public static final Color VISIBLE = new Color(1f,1f,1f,1f);
    public static final Color INVISIBLE = new Color(1f,1f,1f,0f);
    public static final SequenceAction BLINKS = new SequenceAction(Actions.color(INVISIBLE,0.3f), Actions.color(VISIBLE,0.3f));
    public static final Array<String> TABLECOLOR = new Array<>(new String[]{"tableblue","tablegold","tablegreen","tablepurple","tablered","tableyellow"});
    public static final String ALLOWEDCHAR = "abcdefghijklmnopqrstuvwxyz";
    public static final Color NORMAL_COLOR = new Color(1f,1f,1f,1f);

    public static void clearScreen(){
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
