package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.somboi.rodaimpian.gdx.config.GameConfig;

public class TileBase extends Image {
    private String letter;
    private final TextureAtlas atlas;

    public TileBase(TextureAtlas atlas, String letter) {
        super(atlas.findRegion("blank"));
        this.atlas = atlas;
        this.letter = letter;
        changeSprite(letter);
        setSize(57f, 78f);
    }

    public void changeSprite(String letter) {
        String region = getRegionString(letter);
        if (region != null) {
            this.setDrawable(new SpriteDrawable(atlas.createSprite(region)));
        }
    }

    private String getRegionString(String letter) {
        String loLetter = letter.toLowerCase();
        String region = "";
        if (loLetter.equals("á")) {
            region = "atwo";
        } else if (loLetter.equals("à")) {
            region = "aone";
        } else if (loLetter.equals("â")) {
            region = "athree";
        } else if (loLetter.equals("ã")) {
            region = "afour";
        } else if (loLetter.equals("ç")) {
            region = "cone";
        } else if (loLetter.equals("é")) {
            region = "etwo";
        } else if (loLetter.equals("è")) {
            region = "eone";
        } else if (loLetter.equals("ë")) {
            region = "ethree";
        } else if (loLetter.equals("ê")) {
            region = "efour";
        } else if (loLetter.equals("í")) {
            region = "itwo";
        } else if (loLetter.equals("ì")) {
            region = "ione";
        } else if (loLetter.equals("î")) {
            region = "ithree";
        } else if (loLetter.equals("ï")) {
            region = "ifour";
        } else if (loLetter.equals("ó")) {
            region = "oone";
        } else if (loLetter.equals("ò")) {
            region = "otwo";
        } else if (loLetter.equals("ô")) {
            region = "othree";
        } else if (loLetter.equals("ö")) {
            region = "ofour";
        } else if (loLetter.equals("õ")) {
            region = "ofive";
        } else if (loLetter.equals("ú")) {
            region = "uone";
        } else if (loLetter.equals("ù")) {
            region = "utwo";
        } else if (loLetter.equals("û")) {
            region = "uthree";
        } else if (loLetter.equals("ü")) {
            region = "ufour";
        } else if (loLetter.equals("\'")) {
            region = "oppost";
        } else if (loLetter.equals("-")) {
            region = "minus";
        } else {
            if (GameConfig.ALLOWEDCHAR.contains(loLetter)) {
                region = loLetter;

            } else {
                return null;
            }
        }
        return "tile" + region;
    }
}