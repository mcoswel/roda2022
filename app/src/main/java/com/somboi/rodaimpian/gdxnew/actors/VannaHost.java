package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AddListenerAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;

public class VannaHost extends Image {
    private final Animation<Sprite> thumbsUp;

    private final Animation<Sprite> wrongOne;
    private final Animation<Sprite> wrongTwo;

    private final Animation<Sprite> relaxOne;
    private final Animation<Sprite> relaxTwo;

    private final Animation<Sprite> sideR;
    private final Animation<Sprite> sideHandOneR;
    private final Animation<Sprite> sideHandTwoR;
    private final Animation<Sprite> walkR;

    private final Animation<Sprite> sideL;
    private final Animation<Sprite> sideHandOneL;
    private final Animation<Sprite> sideHandTwoL;
    private final Animation<Sprite> walkL;
    private float stateTime;
    private float duration = 0.25f;
    private int randomNo;
    private float randomTimer = 10f;
    private Animation<Sprite> currentAnimation;
    private boolean leftSide;
    private boolean walking;
    private int vannaCounter;
    public VannaHost(TextureAtlas atlas) {
        super(atlas.findRegion("relax1"));
        setPosition(800f, 1149f);
        setSize(126f, 249f);
        thumbsUp = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("thumbsup1"), atlas.createSprite("thumbsup2"), atlas.createSprite("thumbsup3")
                }), Animation.PlayMode.LOOP);

        wrongOne = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("wrong1"), atlas.createSprite("wrong2")
                }), Animation.PlayMode.LOOP);

        wrongTwo = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("wrong3"), atlas.createSprite("wrong4")
                }), Animation.PlayMode.LOOP);

        relaxOne = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("relax1"), atlas.createSprite("relax2")
                }), Animation.PlayMode.LOOP);

        relaxTwo = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("relax3"), atlas.createSprite("relax4")
                }), Animation.PlayMode.LOOP);

        sideR = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("side1"), atlas.createSprite("side2")
                }), Animation.PlayMode.LOOP);

        sideL = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("sidel1"), atlas.createSprite("sidel2")
                }), Animation.PlayMode.LOOP);

        sideHandOneR = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("sidehand1"), atlas.createSprite("sidehand2")
                }), Animation.PlayMode.LOOP);

        sideHandTwoR = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("sidehand3"), atlas.createSprite("sidehand4")
                }), Animation.PlayMode.LOOP);


        sideHandOneL = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("sidehandl1"), atlas.createSprite("sidehandl2")
                }), Animation.PlayMode.LOOP);

        sideHandTwoL = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("sidehandl3"), atlas.createSprite("sidehandl4")
                }), Animation.PlayMode.LOOP);

        walkR = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("walk1"), atlas.createSprite("walk2")
                }), Animation.PlayMode.LOOP);

        walkL = new Animation<Sprite>(duration,
                new Array<>(new Sprite[]{
                        atlas.createSprite("walkl1"), atlas.createSprite("walkl2")
                }), Animation.PlayMode.LOOP);

        currentAnimation = thumbsUp;

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (randomTimer > 0) {
            randomTimer -= delta;
            if (randomTimer <= 0) {
                randomTimer = 10f;
                randomize();
            }
        }
        stateTime += delta;
        setDrawable(new SpriteDrawable(currentAnimation.getKeyFrame(stateTime)));
        if (getX() < 450f) {
            leftSide = true;
        } else {
            leftSide = false;
        }

        if (walking){
            if (getX()==800f || getX() == -30f){
                walking = false;
                relax();
            }
        }

    }

    public void correct() {
        if (!walking) {
            if (leftSide) {
                currentAnimation = sideHandL();
            } else {
                currentAnimation = sideHandR();
            }
        }
    }

    public void relax() {
        if (!walking) {
            if (randomNo == 0) {
                currentAnimation = relaxOne;
            } else {
                currentAnimation = relaxTwo;
            }
        }
    }

    public void wrong() {
        if (!walking) {
            if (randomNo == 0) {
                currentAnimation = wrongOne;
            } else {
                currentAnimation = wrongTwo;
            }
        }
    }

    public void waitingAnswer() {
        if (!walking) {
            if (leftSide) {
                currentAnimation = sideL;
            } else {
                currentAnimation = sideR;
            }
        }
    }

    public void dance() {
        if (!walking) {
            currentAnimation = thumbsUp;
        }
    }

    public void walk() {
        walking = true;
        if (leftSide) {
            currentAnimation = walkL;
            addAction(Actions.moveTo(800f, 1149f, 7f));
        } else {
            currentAnimation = walkR;
            addAction(Actions.moveTo(-30f, 1149f, 7f));
        }



    }


    private Animation sideHandL() {
        if (randomNo == 0) {
            return sideHandOneL;
        }
        return sideHandTwoL;
    }

    private Animation sideHandR() {
        if (randomNo == 0) {
            return sideHandOneR;
        }
        return sideHandTwoR;
    }


    private void randomize() {
        randomNo = MathUtils.random(0, 1);
    }

    public void increaseCount(){
        vannaCounter++;
        if (vannaCounter%2==0 && !walking){
            walk();
        }
    }

}
