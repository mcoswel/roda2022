package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class CpuFactory {
    private final Array<String> cpuName = new Array<>(new String[]{"Adam", "Diana", "Joe", "Lizzy", "Nabilah", "Nur", "Wati", "Rajan"});
    private final TextureAtlas atlas;
    private final float imageSize = 250f;


    public CpuFactory(TextureAtlas atlas) {
        this.atlas = atlas;
        cpuName.shuffle();
    }

    public void createCpu(int humanPlayerCount) {
        int cpuCount = 3 - humanPlayerCount;

    }

    private class CpuImage extends Image {
        private final Sprite animate;

        private CpuImage(String name) {
            super(atlas.findRegion(name));
            animate = atlas.createSprite(name + "two");
            setSize(imageSize, imageSize);
        }
    }
}

