package com.somboi.gdx.actor;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Fingers extends Image {
    private final SequenceAction sequenceAction = new SequenceAction(
            Actions.moveBy(0,-600f*0.01f,2f),
            Actions.moveBy(0,600f*0.01f,0f)
    );

    private final TextureAtlas textureAtlas;

    public Fingers(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        this.setSize(154f*0.01f,179f*0.01f);
        this.setPosition(660f*0.01f,1000f*0.01f);
        this.addAction(Actions.forever(sequenceAction));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.getY()<800f*0.01f){
            this.setDrawable(new SpriteDrawable(new Sprite(textureAtlas.findRegion("hand2"))));
        }else{
            this.setDrawable(new SpriteDrawable(new Sprite(textureAtlas.findRegion("hand1"))));
        }
    }
}
