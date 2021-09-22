package com.somboi.gdx.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class GameConfig {
    public static final float SCWIDTH = 900f;
    public static final float SCHEIGHT = 1600f;
    public static void clearScreen(){
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
