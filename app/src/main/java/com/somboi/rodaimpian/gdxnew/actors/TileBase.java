package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TileBase extends Image {
    private char letter;
    private final TextureAtlas atlas;
    public TileBase(TextureAtlas atlas, char letter){
        super(atlas.findRegion("blank"));
        this.atlas = atlas;
        this.letter = letter;
        setSize(57f, 78f);
    }
}
