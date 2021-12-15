package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class ActorFactory {
    private final TextureAtlas atlas;
    private final Stage worldStage;
    private BodyImage wheelImg;
    private Image centerLogo;
    private BodyImage needleImg;
    private boolean isWheelBonus;

    public ActorFactory(TextureAtlas atlas, Stage worldStage) {
        this.atlas = atlas;
        this.worldStage = worldStage;
    }

    public void createWheel(Body wheelBody) {
        wheelImg = new BodyImage(atlas.findRegion("wheel"), wheelBody);
        wheelImg.setOrigin(5.75f, 5.75f);
        wheelImg.setSize(11.5f, 11.5f);
        wheelImg.setPosition(wheelBody.getPosition().x - 5.75f, wheelBody.getPosition().y - 5.75f);
        worldStage.addActor(wheelImg);

    }

    public void createWheelBonus() {
        wheelImg.setDrawable(new SpriteDrawable(atlas.createSprite("wheelbonus")));
        centerLogo.setDrawable(new SpriteDrawable(atlas.createSprite("logobonus")));
        needleImg.setColor(Color.BLUE);
        isWheelBonus = true;
    }

    public void createNeedle(Body needleBody) {
         needleImg = new BodyImage(atlas.findRegion("needle"), needleBody);
        needleImg.setOrigin(5.75f, 11.4f);
        needleImg.setPosition(needleBody.getPosition().x, needleBody.getPosition().y);
        needleImg.setSize(needleImg.getWidth() / 100, needleImg.getHeight() / 100);
        worldStage.addActor(needleImg);
    }

    public void createLogo(Body wheelBody) {
        centerLogo = new Image(atlas.findRegion("logocenter"));
        centerLogo.setScale(0.01f);
        centerLogo.setPosition(wheelBody.getPosition().x - 5.75f, wheelBody.getPosition().y - 5.75f);
        worldStage.addActor(centerLogo);
    }

    public boolean isWheelBonus() {
        return isWheelBonus;
    }
}
