package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

import java.util.ArrayList;
import java.util.HashMap;

public class CpuFactory {
    private final Array<String> cpuName = new Array<>(new String[]{"Adam", "Diana", "Joe", "Lizzy", "Nabilah", "Nur", "Wati", "Rajan"});
    private final TextureAtlas atlas;
    private int count = 0;
    public CpuFactory(TextureAtlas atlas) {
        this.atlas = atlas;
        cpuName.shuffle();
    }

    public PlayerGuis createCpu(Skin skin) {
        PlayerNew playerNew = new PlayerNew();
        playerNew.setName(cpuName.get(count));
        playerNew.setAi(true);
        playerNew.setPlayerGifts(new ArrayList<>());
        playerNew.setPlayerBonus(new ArrayList<>());
        PlayerGuis playerGuis = new PlayerGuis();
        playerGuis.setPlayerNew(playerNew);

        playerGuis.setNameLabel(new Label(playerNew.getName().toUpperCase(), skin,"name"));
        playerGuis.setProfilePic(new ProfilePic(atlas.findRegion(playerNew.getName().toLowerCase()), null, playerNew,0));


        playerGuis.setScoreLabel(new Label("$"+playerNew.getScore(), skin, "score"));
        playerGuis.setFulLScoreLabel(new Label("$"+playerNew.getFullScore(), skin, "arial36"));
        playerGuis.setFreeTurn(new Label(StringRes.FREETURN, skin, "free"));
        count++;
        if (count>=cpuName.size){
            count = 0;
        }

        return playerGuis;
    }
}

