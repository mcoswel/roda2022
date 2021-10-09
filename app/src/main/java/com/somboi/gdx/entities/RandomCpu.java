package com.somboi.gdx.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.somboi.gdx.actor.PlayerImage;

public class RandomCpu {
    private final TextureAtlas textureAtlas;
    private final Array<Integer> cpuIntegers = new Array<>(new Integer[]{0, 1, 2, 3, 4, 5});

    public RandomCpu(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        cpuIntegers.shuffle();
    }

    public Player getPlayer(int i) {
        int cpuNo = cpuIntegers.get(i);
        Player player = new Player();
        player.greediness = MathUtils.random(0f, 1f);

        player.isAi = true;
        if (cpuNo == 0) {
            player.name = "Adam";
        } else if (cpuNo == 1) {
            player.name = "Diana";
        } else if (cpuNo == 2) {
            player.name = "Nabilah";
        } else if (cpuNo == 3) {
            player.name = "Sugiano";
        } else if (cpuNo == 4) {
            player.name = "Rajan";
        } else if (cpuNo == 5) {
            player.name = "Siti";
        }
        return player;
    }

    public PlayerImage getImage(int i) {
        int cpuNo = cpuIntegers.get(i);
        TextureRegion region = textureAtlas.findRegion("3_adam");
        TextureRegion regionA = textureAtlas.findRegion("3_adam2");

        if (cpuNo == 1) {
            region = textureAtlas.findRegion("3_diana");
            regionA = textureAtlas.findRegion("3_diana2");

        } else if (cpuNo == 2) {
            region = textureAtlas.findRegion("3_nabilah");
            regionA = textureAtlas.findRegion("3_nabilah2");

        } else if (cpuNo == 3) {
            region = textureAtlas.findRegion("3_paksugi");
            regionA = textureAtlas.findRegion("3_paksugi2");
        } else if (cpuNo == 4) {
            region = textureAtlas.findRegion("3_rajan");
            regionA = textureAtlas.findRegion("3_rajan2");
        } else if (cpuNo == 5) {
            region = textureAtlas.findRegion("3_siti");
            regionA = textureAtlas.findRegion("3_siti2");
        }
        PlayerImage playerImage = new PlayerImage(region);
        playerImage.setAnimate(regionA);
        return playerImage;
    }
}

