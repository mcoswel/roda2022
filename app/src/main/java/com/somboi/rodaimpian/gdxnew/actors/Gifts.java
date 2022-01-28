package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import java.util.ArrayList;
import java.util.HashMap;

public class Gifts {
    private final TextureAtlas atlas;
    private final Sparkling sparkling;
    private GiftsImg gifts;
    private GiftsImg giftBox;
    private boolean prepareGift;

    public Gifts(TextureAtlas atlas, Texture sparkTex) {
        this.atlas = atlas;
        this.sparkling = new Sparkling(sparkTex);
    }

    public void prepareGift(Stage stage) {
        stage.addActor(sparkling);
        giftBox = new GiftsImg(atlas.findRegion("unopened_gift"));
        stage.addActor(giftBox);
        prepareGift = true;
    }

    public int getGiftValue(int giftIndex) {
        if (giftIndex == 2) {
            return 35;
        }
        if (giftIndex == 3) {
            return 800;
        }
        if (giftIndex == 4) {
            return 45;
        }
        if (giftIndex == 5) {
            return 700;
        }
        if (giftIndex == 6) {
            return 550;
        }
        if (giftIndex == 7) {
            return 400;
        }
        if (giftIndex == 8) {
            return 320;
        }
        if (giftIndex == 9) {
            return 125;
        }
        if (giftIndex == 10) {
            return 150;
        }
        if (giftIndex == 11) {
            return 375;
        }
        if (giftIndex == 12) {
            return 270;
        }
        if (giftIndex == 13) {
            return 130;
        }
        if (giftIndex == 14) {
            return 990;
        }
        if (giftIndex == 15) {
            return 80;
        }
        if (giftIndex == 16) {
            return 25;
        }
        if (giftIndex == 17) {
            return 35;
        }
        if (giftIndex == 18) {
            return 100;
        }
        if (giftIndex == 19) {
            return 140;
        }
        if (giftIndex == 20) {
            return 880;
        }
        if (giftIndex == 21) {
            return 90;
        }
        if (giftIndex == 22) {
            return 35;
        }
        if (giftIndex == 23) {
            return 1000;
        }
        return 1500;
    }

    public static String getGiftRegion(int giftIndex) {
        if (giftIndex == 1) {
            return "5_b_ipong";
        }
        if (giftIndex == 2) {
            return "5_b_urut";
        }
        if (giftIndex == 3) {
            return "5_g_bicycle";
        }
        if (giftIndex == 4) {
            return "5_g_canned";
        }
        if (giftIndex == 5) {
            return "5_g_drone";
        }
        if (giftIndex == 6) {
            return "5_g_elvis";
        }
        if (giftIndex == 7) {
            return "5_g_facial";
        }
        if (giftIndex == 8) {
            return "5_g_kitchen";
        }
        if (giftIndex == 9) {
            return "5_g_lepoo";
        }
        if (giftIndex == 10) {
            return "5_g_makeup";
        }
        if (giftIndex == 11) {
            return "5_g_pumba";
        }
        if (giftIndex == 12) {
            return "5_g_razor";
        }
        if (giftIndex == 13) {
            return "5_g_ricecooker";
        }
        if (giftIndex == 14) {
            return "5_g_roles";
        }
        if (giftIndex == 15) {
            return "5_g_shoe";
        }
        if (giftIndex == 16) {
            return "5_g_socks";
        }
        if (giftIndex == 17) {
            return "5_g_speaker";
        }
        if (giftIndex == 18) {
            return "5_g_starbock";
        }
        if (giftIndex == 19) {
            return "5_g_sweater";
        }
        if (giftIndex == 20) {
            return "5_g_tablet";
        }
        if (giftIndex == 21) {
            return "5_g_teddybear";
        }
        if (giftIndex == 22) {
            return "5_g_ultraman";
        }

        return "5_g_voucher";
    }

    public void loseGifts() {
        sparkling.remove();
        if (giftBox != null) {
            giftBox.remove();
        }

    }

    public void winGifts(int giftIndex, PlayerGuis playerGuis, Stage stage) {
        Vector2 position = playerGuis.getPosition();
        if (playerGuis.getPlayerNew().getPlayerGifts() == null) {
            playerGuis.getPlayerNew().setPlayerGifts(new ArrayList<>());
        }

        int giftSize = playerGuis.getGiftsWon().size();
        int giftCounter = giftSize % 5;

        if (giftBox != null) {
            giftBox.openBox();
        }
        gifts = new GiftsImg(giftIndex);
        stage.addActor(gifts);
        gifts.addAction(
                new SequenceAction(new ParallelAction(
                        Actions.scaleBy(3f, 3f, 3f),
                        Actions.moveTo(100f, 300f, 3f)),
                        new ParallelAction(
                                Actions.sizeTo(25f, 25f, 1.5f),
                                Actions.moveTo(position.x - 25 + (70 * giftCounter), position.y, 1.5f))
                )
        );

        playerGuis.addGifts(giftIndex);
    }

    private class GiftsImg extends Image {
        public GiftsImg(TextureRegion region) {
            super(region);
            setup();
        }

        public GiftsImg(int giftIndex) {
            super(atlas.findRegion(getGiftRegion(giftIndex)));
            setup();
        }

        private void setup() {
            setSize(200, 200);
            setPosition(350f, 1110f);
        }

        private void openBox() {
            setDrawable(new SpriteDrawable(atlas.createSprite("opened_gift")));
        }

    }



    public boolean isPrepareGift() {
        return prepareGift;
    }

    public void setPrepareGift(boolean prepareGift) {
        if (!prepareGift) {
            loseGifts();

        }
        this.prepareGift = prepareGift;
    }
}
