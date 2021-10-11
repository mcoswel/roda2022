package com.somboi.gdx.actor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class Vanna extends Image {
    private Animation<TextureRegion> relaxAnime;
    private Animation<TextureRegion> sideHandRightAnime;
    private Animation<TextureRegion> sideHandLeftAnime;
    private Animation<TextureRegion> wrongAnime;

    private final Animation<TextureRegion> relaxA;
    private final Animation<TextureRegion> relaxB;
    private final Animation<TextureRegion> sideRight;
    private final Animation<TextureRegion> sideLeft;
    private final Animation<TextureRegion> sideHandRightA;
    private final Animation<TextureRegion> sideHandRightB;
    private final Animation<TextureRegion> sideHandRightC;

    private final Animation<TextureRegion> sideHandLeftA;
    private final Animation<TextureRegion> sideHandLeftB;
    private final Animation<TextureRegion> sideHandLeftC;
    private final Animation<TextureRegion> wrongA;
    private final Animation<TextureRegion> wrongB;
    private final Animation<TextureRegion> wrongC;

    private final Animation<TextureRegion> walkRight;
    private final Animation<TextureRegion> walkLeft;
    private final Animation<TextureRegion> thumbsUp;

    private boolean isLeft, isWalking;
    private final float duration = 0.2f;

    private final Array<Animation<TextureRegion>> relaxes = new Array<>();
    private final Array<Animation<TextureRegion>> sideHandRights = new Array<>();
    private final Array<Animation<TextureRegion>> sideHandLefts = new Array<>();
    private final Array<Animation<TextureRegion>> wrongs = new Array<>();

    private int random;

    private Vector2 position;

    private float stateTime;
    private Animation<TextureRegion> host;

    public Vanna(TextureAtlas textureAtlas) {
        relaxA = new Animation<TextureRegion>(duration, new TextureRegion[]{textureAtlas.findRegion("relax1"), textureAtlas.findRegion("relax2")});
        relaxB = new Animation<TextureRegion>(duration, new TextureRegion[]{textureAtlas.findRegion("relax3"), textureAtlas.findRegion("relax4")});

        relaxes.add(relaxA);
        relaxes.add(relaxB);

        TextureRegion sideRegion1 = textureAtlas.findRegion("side1");
        TextureRegion sideRegion2 = textureAtlas.findRegion("side2");
        TextureRegion sideRegion1Left = textureAtlas.findRegion("sidel1");
        TextureRegion sideRegion2Left = textureAtlas.findRegion("sidel2");


        sideRight = new Animation<TextureRegion>(duration, new TextureRegion[]{sideRegion1, sideRegion2});
        sideLeft = new Animation<TextureRegion>(duration, new TextureRegion[]{sideRegion1Left, sideRegion2Left});

        TextureRegion sideHand1 = textureAtlas.findRegion("sidehand1");
        TextureRegion sideHand2 = textureAtlas.findRegion("sidehand2");
        TextureRegion sideHand3 = textureAtlas.findRegion("sidehand3");
        TextureRegion sideHand4 = textureAtlas.findRegion("sidehand4");

        TextureRegion sideHand1Left = textureAtlas.findRegion("sidehandl1");
        TextureRegion sideHand2Left = textureAtlas.findRegion("sidehandl2");
        TextureRegion sideHand3Left = textureAtlas.findRegion("sidehandl3");
        TextureRegion sideHand4Left = textureAtlas.findRegion("sidehandl4");


        sideHandRightA = new Animation<TextureRegion>(duration, new TextureRegion[]{sideHand1, sideHand2});
        sideHandRightB = new Animation<TextureRegion>(duration, new TextureRegion[]{sideHand3, sideHand4});
        sideHandRightC = new Animation<TextureRegion>(duration, new TextureRegion[]{sideHand1, sideHand2, sideHand3, sideHand4});
        sideHandRights.add(sideHandRightA);
        sideHandRights.add(sideHandRightB);
        sideHandRights.add(sideHandRightC);

        sideHandLeftA = new Animation<TextureRegion>(duration, new TextureRegion[]{sideHand1Left, sideHand2Left});
        sideHandLeftB = new Animation<TextureRegion>(duration, new TextureRegion[]{sideHand3Left, sideHand4Left});
        sideHandLeftC = new Animation<TextureRegion>(duration, new TextureRegion[]{sideHand1Left, sideHand2Left, sideHand3Left, sideHand4Left});
        sideHandLefts.add(sideHandLeftA);
        sideHandLefts.add(sideHandLeftB);
        sideHandLefts.add(sideHandLeftC);

        wrongA = new Animation<TextureRegion>(duration, new TextureRegion[]{textureAtlas.findRegion("wrong1"), textureAtlas.findRegion("wrong2")});
        wrongB = new Animation<TextureRegion>(duration, new TextureRegion[]{textureAtlas.findRegion("wrong3"), textureAtlas.findRegion("wrong4")});
        wrongC = new Animation<TextureRegion>(duration, new TextureRegion[]{textureAtlas.findRegion("wrong1"), textureAtlas.findRegion("wrong2"), textureAtlas.findRegion("wrong3"), textureAtlas.findRegion("wrong4")});
        wrongs.add(wrongA);
        wrongs.add(wrongB);
        wrongs.add(wrongC);

        TextureRegion walkRight1 = textureAtlas.findRegion("walk1");
        TextureRegion walkRight2 = textureAtlas.findRegion("walk2");

        TextureRegion walkLeft1 = textureAtlas.findRegion("walkl1");
        TextureRegion walkLeft2 = textureAtlas.findRegion("walkl2");


        walkRight = new Animation<TextureRegion>(duration, new TextureRegion[]{walkRight1, walkRight2});
        walkLeft = new Animation<TextureRegion>(duration, new TextureRegion[]{walkLeft1, walkLeft2});

        thumbsUp = new Animation<TextureRegion>(duration, new TextureRegion[]{textureAtlas.findRegion("thumbsup1"), textureAtlas.findRegion("thumbsup2"), textureAtlas.findRegion("thumbsup3")});

        relaxAnime = relaxB;
        sideHandRightAnime = sideHandRightC;
        sideHandLeftAnime = sideHandLeftC;
        wrongAnime = wrongC;
        host = thumbsUp;

        this.setPosition(700f, 1075f);
        this.setSize(185f, 325f);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        this.setDrawable(new SpriteDrawable(new Sprite(host.getKeyFrame(stateTime, true))));
        if (this.getX()==700f || this.getX()==-30f){
            if (isWalking){
                isWalking = false;
                hostRelax();
                if (this.getX()==-30f){
                    isLeft =true;
                }else{
                    isLeft = false;
                }
            }
        }
    }

    public void hostSide() {
        if (!isWalking) {

            if (isLeft) {
                host = sideLeft;
            } else {
                host = sideRight;
            }
        }
    }

    public void hostWalk() {
        if (!isWalking) {
            isWalking = true;
            host = walkLeft;
            if (isLeft) {
                host = walkLeft;
                this.addAction(Actions.moveTo(700f, 1075f, 6f));
            } else {
                host = walkRight;
                this.addAction(Actions.moveTo(-30f, 1075f, 6f));
            }
        }
    }


    public void hostRelax() {
        randomize();
        if (!isWalking) {
            host = relaxAnime;
        }

    }

    public void hostWrong() {
        randomize();
        if (!isWalking) {
            host = wrongAnime;
        }
    }

    public void hostCorrect() {
        randomize();
        if (!isWalking) {
            if (isLeft) {
                host = sideHandLeftAnime;
            } else {
                host = sideHandRightAnime;
            }
        }
    }

    public void hostThumbsUp() {
        if (!isWalking) {
            host = thumbsUp;
        }
    }


    private void randomize() {
        random = MathUtils.random(0, 2);
        if (random == 0) {
            relaxAnime = relaxA;
            sideHandRightAnime = sideHandRightA;
            sideHandLeftAnime = sideHandLeftA;
            wrongAnime = wrongA;
        } else if (random == 1) {
            relaxAnime = relaxB;
            sideHandRightAnime = sideHandRightB;
            sideHandLeftAnime = sideHandLeftB;
            wrongAnime = wrongB;
        } else if (random == 2) {
            relaxAnime = relaxA;
            sideHandRightAnime = sideHandRightC;
            sideHandLeftAnime = sideHandLeftC;
            wrongAnime = wrongC;
        }
    }
}
