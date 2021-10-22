package com.somboi.rodaimpian.gdx.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.somboi.rodaimpian.gdx.actor.BonusGiftImg;

public class Bonus {

    private BonusGiftImg bonusImage;
    private final TextureAtlas textureAtlas;
    private int bonusIndex;
    public Bonus(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_motorcycle"));
    }
    public Bonus(TextureAtlas textureAtlas, int index) {
        this.textureAtlas = textureAtlas;
        bonusImage = BonusGiftImg.getBonus(textureAtlas, index);

    }
    public void getWheelResult(WheelParam wheelParam, String lastContact) {

        switch (lastContact) {
            case "n0":
                bonusIndex = 1;
                wheelParam.resultValue = 1500;
                wheelParam.results = "$1500";
                break;
            case "n1":
                bonusIndex = 2;
                wheelParam.resultValue = 150;
                wheelParam.results = "$150";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_neskopi"));
                break;
            case "n2":
                bonusIndex = 3;
                wheelParam.resultValue = 350;
                wheelParam.results = "$350";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_nintendo"));
                break;
            case "n3":
                bonusIndex = 4;
                wheelParam.resultValue = 200;
                wheelParam.results = "$200";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_polystation"));
                break;
            case "n4":
                bonusIndex = 5;
                wheelParam.resultValue = 500;
                wheelParam.results = "$500";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_predator"));

                break;
            case "n5":
            case "n6":
            case "n7":
                bonusIndex = 6;
                wheelParam.resultValue = 1000;
                wheelParam.results = "$1000";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_ring"));

                break;
            case "n8":
                bonusIndex = 7;
                wheelParam.resultValue = 800;
                wheelParam.results = "$800";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_toyoda"));

                break;
            case "n9":
                bonusIndex = 8;
                wheelParam.resultValue = 750;
                wheelParam.results = "$750";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_kawansakit"));

                break;
            case "n10":
                bonusIndex = 9;
                wheelParam.resultValue = 650;
                wheelParam.results = "$650";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_laptop"));

                break;
            case "n11":
                bonusIndex = 10;
                wheelParam.resultValue = 210;
                wheelParam.results = "$210";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_wash"));

                break;
            case "n12":
                bonusIndex = 11;
                wheelParam.resultValue = 50;
                wheelParam.results = "$50";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_lintah"));

                break;
            case "n13":
                bonusIndex = 12;
                wheelParam.resultValue = 185;
                wheelParam.results = "$185";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_microwave"));

                break;
            case "n14":
                bonusIndex = 13;
                wheelParam.resultValue = 5000;
                wheelParam.results = "$5000";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_car"));

                break;
            case "n15":
                bonusIndex = 14;
                wheelParam.resultValue = 100;
                wheelParam.results = "$100";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_chair"));

                break;
            case "n16":
                bonusIndex = 15;
                wheelParam.resultValue = 800;
                wheelParam.results = "$800";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_earring"));

                break;
            case "n17":
                bonusIndex = 16;
                wheelParam.resultValue = 400;
                wheelParam.results = "$400";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_ferraro"));

                break;
            case "n18":
                bonusIndex = 17;
                wheelParam.resultValue = 525;
                wheelParam.results = "$525";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_flattv"));

                break;
            case "n19":
                bonusIndex = 18;
                wheelParam.resultValue = 300;
                wheelParam.results = "$300";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_fridge"));

                break;
            case "n20":
                bonusIndex = 19;
                wheelParam.resultValue = 3000;
                wheelParam.results = "$3000";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_frog"));

                break;
            case "n21":
                bonusIndex = 20;
                wheelParam.resultValue = 950;
                wheelParam.results = "$950";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_golf"));

                break;
            case "n22":
                bonusIndex = 21;
                wheelParam.resultValue = 75;
                wheelParam.results = "$75";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_headphone"));

                break;
            case "n23":
                bonusIndex = 22;
                wheelParam.resultValue = 15;
                wheelParam.results = "$15";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_jersey"));

                break;
            case "n24":
                bonusIndex = 23;
                wheelParam.resultValue = 780;
                wheelParam.results = "$780";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_jewel"));
                break;
            case "n25":
                bonusIndex = 24;
                wheelParam.resultValue = 170;
                wheelParam.results = "$170";
                bonusImage = new BonusGiftImg(textureAtlas.findRegion("7_b_airasia"));
                break;

        }
    }

    public int getBonusIndex() {
        return bonusIndex;
    }

    public BonusGiftImg getBonusImage() {
        return bonusImage;
    }
}
