package com.somboi.gdx.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.somboi.gdx.actor.BonusGiftImg;
import com.somboi.gdx.assets.StringRes;

public class Bonus {

    private BonusGiftImg bonusImage;
    private final TextureAtlas textureAtlas;

    public Bonus(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_motorcycle"));
    }

    public void getWheelResult(WheelParam wheelParam, String lastContact) {
        wheelParam.results = StringRes.BONUS;
        switch (lastContact) {
            case "n0":
                wheelParam.resultValue = 1500;
                break;
            case "n1":
                wheelParam.resultValue = 150;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_neskopi"));
                break;
            case "n2":
                wheelParam.resultValue = 350;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_nintendo"));
                break;
            case "n3":
                wheelParam.resultValue = 200;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_polystation"));
                break;
            case "n4":
                wheelParam.resultValue = 500;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_predator"));

                break;
            case "n5":
            case "n6":
            case "n7":
                wheelParam.resultValue = 1000;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_ring"));

                break;
            case "n8":
                wheelParam.resultValue = 800;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_toyoda"));

                break;
            case "n9":
                wheelParam.resultValue = 750;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_kawansakit"));

                break;
            case "n10":
                wheelParam.resultValue = 650;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_laptop"));

                break;
            case "n11":
                wheelParam.resultValue = 210;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_wash"));

                break;
            case "n12":
                wheelParam.resultValue = 50;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_lintah"));

                break;
            case "n13":
                wheelParam.resultValue = 185;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_microwave"));

                break;
            case "n14":
                wheelParam.resultValue = 5000;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_car"));

                break;
            case "n15":
                wheelParam.resultValue = 100;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_chair"));

                break;
            case "n16":
                wheelParam.resultValue = 800;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_earring"));

                break;
            case "n17":
                wheelParam.resultValue = 400;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_ferraro"));

                break;
            case "n18":
                wheelParam.resultValue = 525;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_flattv"));

                break;
            case "n19":
                wheelParam.resultValue = 300;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_fridge"));

                break;
            case "n20":
                wheelParam.resultValue = 3000;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_frog"));

                break;
            case "n21":
                wheelParam.resultValue = 950;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_golf"));

                break;
            case "n22":
                wheelParam.resultValue = 75;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_headphone"));

                break;
            case "n23":
                wheelParam.resultValue = 15;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_jersey"));

                break;
            case "n24":
                wheelParam.resultValue = 780;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_jewel"));
                break;
            case "n25":
                wheelParam.resultValue = 170;
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_airasia"));
                break;

        }
    }

    public BonusGiftImg getBonusImage() {
        return bonusImage;
    }
}
