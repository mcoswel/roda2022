package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Bonuses extends Image{
    private final TextureAtlas textureAtlas;
    private final int bonusIndex;
    public Bonuses(TextureAtlas textureAtlas,int bonusIndex) {
        super(textureAtlas.findRegion("7_b_motorcycle"));
        this.textureAtlas = textureAtlas;
        this.bonusIndex = bonusIndex;
        setSize(200, 200);
        getBonus();
    }

    public void getBonus() {
        if (bonusIndex == 2) {
            setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_neskopi")));

        } else if (bonusIndex == 3) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_nintendo")));
        } else if (bonusIndex == 4) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_polystation")));
        } else if (bonusIndex == 5) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_predator")));
        } else if (bonusIndex == 6) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_ring")));
        } else if (bonusIndex == 7) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_toyoda")));
        } else if (bonusIndex == 8) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_kawansakit")));
        } else if (bonusIndex == 9) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_laptop")));
        } else if (bonusIndex == 10) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_wash")));
        } else if (bonusIndex == 11) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_lintah")));
        } else if (bonusIndex == 12) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_microwave")));
        } else if (bonusIndex == 13) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_car")));
        } else if (bonusIndex == 14) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_chair")));
        } else if (bonusIndex == 15) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_earring")));
        } else if (bonusIndex == 16) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_ferraro")));
        } else if (bonusIndex == 17) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_flattv")));
        } else if (bonusIndex == 18) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_fridge")));
        } else if (bonusIndex == 19) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_frog")));
        } else if (bonusIndex == 20) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_golf")));
        } else if (bonusIndex == 21) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_headphone")));
        } else if (bonusIndex == 22) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_jersey")));
        } else if (bonusIndex == 23) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_jewel")));
        }else if (bonusIndex == 24) {
             setDrawable(new SpriteDrawable(textureAtlas.createSprite("7_b_airasia")));
        }
    }


}
