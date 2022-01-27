package com.somboi.rodaimpian.gdx.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdxnew.assets.GameSound;

public class RandomCpu {
    private final TextureAtlas textureAtlas;
    private final Array<Integer> cpuIntegers = new Array<>(new Integer[]{0, 1, 2, 3, 4, 5});
    private final GameSound gameSound;
    private boolean rajanSlap;

    public RandomCpu(TextureAtlas textureAtlas, GameSound gameSound) {
        this.textureAtlas = textureAtlas;
        this.gameSound = gameSound;
        cpuIntegers.shuffle();
    }

    public Player getPlayer(int i) {
        int cpuNo = cpuIntegers.get(i);
        //  int cpuNo = i;
        Player player = new Player();
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

    public PlayerImage getImageByName(String name){
        TextureRegion region = textureAtlas.findRegion("3_adam");
        TextureRegion regionA = textureAtlas.findRegion("3_adam2");
        if (name.equals("Diana")) {
            region = textureAtlas.findRegion("3_diana");
            regionA = textureAtlas.findRegion("3_diana2");

        } else  if (name.equals("Nabilah")) {
            region = textureAtlas.findRegion("3_nabilah");
            regionA = textureAtlas.findRegion("3_nabilah2");

        } else  if (name.equals("Sugiano")) {
            region = textureAtlas.findRegion("3_paksugi");
            regionA = textureAtlas.findRegion("3_paksugi2");
        } else  if (name.equals("Rajan")) {
            region = textureAtlas.findRegion("3_rajan");
            regionA = textureAtlas.findRegion("3_rajan2");
        } else  if (name.equals("Siti")) {
            region = textureAtlas.findRegion("3_siti");
            regionA = textureAtlas.findRegion("3_siti2");
        }
        PlayerImage playerImage = new PlayerImage(region);
        playerImage.setAnimate(regionA);
        playerImage.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (name.equals("Rajan") || name.equals("Sugiano")) {
                    gameSound.playSlapSound();
                    rajanSlap = true;
                }
                playerImage.animate();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        return playerImage;
    }

    public PlayerImage getImage(int i) {
        int cpuNo = cpuIntegers.get(i);
        //int cpuNo = i;
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
        playerImage.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (cpuNo == 3 || cpuNo == 4) {
                    gameSound.playSlapSound();
                    rajanSlap = true;
                }
                playerImage.animate();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        return playerImage;
    }

    public boolean isRajanSlap() {
        return rajanSlap;
    }
}

