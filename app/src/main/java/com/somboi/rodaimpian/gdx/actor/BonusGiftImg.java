package com.somboi.rodaimpian.gdx.actor;

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


    public static BonusGiftImg getBonus(TextureAtlas textureAtlas, int bonusIndex) {
        BonusGiftImg bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_motorcycle"));
        if (bonusIndex == 2) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_neskopi"));

        } else if (bonusIndex == 3) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_nintendo"));
        } else if (bonusIndex == 4) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_polystation"));
        } else if (bonusIndex == 5) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_predator"));
        } else if (bonusIndex == 6) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_ring"));
        } else if (bonusIndex == 7) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_toyoda"));
        } else if (bonusIndex == 8) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_kawansakit"));
        } else if (bonusIndex == 9) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_laptop"));
        } else if (bonusIndex == 10) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_wash"));
        } else if (bonusIndex == 11) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_lintah"));
        } else if (bonusIndex == 12) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_microwave"));
        } else if (bonusIndex == 13) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_car"));
        } else if (bonusIndex == 14) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_chair"));
        } else if (bonusIndex == 15) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_earring"));
        } else if (bonusIndex == 16) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_ferraro"));
        } else if (bonusIndex == 17) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_flattv"));
        } else if (bonusIndex == 18) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_fridge"));
        } else if (bonusIndex == 19) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_frog"));
        } else if (bonusIndex == 20) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_golf"));
        } else if (bonusIndex == 21) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_headphone"));
        } else if (bonusIndex == 22) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_jersey"));
        } else if (bonusIndex == 23) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_jewel"));
        }else if (bonusIndex == 24) {
            bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_airasia"));
        }

        return bonusImage;
    }

}
