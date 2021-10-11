package com.somboi.gdx.actor;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BonusGiftImg extends Image {
    public BonusGiftImg(TextureRegion region) {
        super(region);
        this.setPosition(325f, 1100f);
    }

    public static BonusGiftImg getGifts(TextureAtlas textureAtlas, int giftIndex) {
        BonusGiftImg giftImages = new BonusGiftImg(textureAtlas.findRegion("5_b_ipong"));
        if (giftIndex == 2) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_b_urut"));

        } else if (giftIndex == 3) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_bicycle"));
        } else if (giftIndex == 4) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_canned"));
        } else if (giftIndex == 5) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_drone"));
        } else if (giftIndex == 6) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_elvis"));
        } else if (giftIndex == 7) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_facial"));
        } else if (giftIndex == 8) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_kitchen"));
        } else if (giftIndex == 9) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_lepoo"));
        } else if (giftIndex == 10) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_makeup"));
        } else if (giftIndex == 11) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_pumba"));
        } else if (giftIndex == 12) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_razor"));
        } else if (giftIndex == 13) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_ricecooker"));
        } else if (giftIndex == 14) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_roles"));
        } else if (giftIndex == 15) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_shoe"));
        } else if (giftIndex == 16) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_socks"));
        } else if (giftIndex == 17) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_speaker"));
        } else if (giftIndex == 18) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_starbock"));
        } else if (giftIndex == 19) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_sweater"));
        } else if (giftIndex == 20) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_tablet"));
        } else if (giftIndex == 21) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_teddybear"));
        } else if (giftIndex == 22) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_ultraman"));
        } else if (giftIndex == 23) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_voucher"));
        }

        return giftImages;
    }

}
