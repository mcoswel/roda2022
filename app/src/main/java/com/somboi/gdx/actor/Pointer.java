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
        this.setSize(0.55f,0.9f);
      //  this.setPosition(4.5f-this.getWidth(),15f);
        this.setOrigin(this.getWidth()/2,this.getHeight()-0.2f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setPosition(needleBody.getPosition().x-this.getWidth()/2f, needleBody.getPosition().y-0.7f);

    }
}
