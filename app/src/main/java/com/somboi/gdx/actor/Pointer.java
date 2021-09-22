package com.somboi.gdx.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pointer extends Image {
    private final Body needleBody;
    public Pointer(Texture texture, Body needleBody){
        super(texture);
        this.needleBody = needleBody;
        this.setSize(0.35f,1.28f);
        this.setPosition(4.5f-this.getWidth()/2,12.9f-this.getHeight());
        this.setOrigin(this.getWidth()/2,this.getHeight()-0.2f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setPosition(needleBody.getPosition().x-this.getWidth()/2f, needleBody.getPosition().y-this.getHeight()+this.getWidth()/2f);

    }
}
