package com.somboi.rodaimpian.gdxnew.wheels;

import com.somboi.rodaimpian.gdxnew.assets.StringRes;

public class WheelParams {
    private float angle;
    private int scores;
    private String scoreStrings;
    private boolean gifts;
    private boolean bonus;
    private int bonusIndex;
    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getBonusIndex() {
        return bonusIndex;
    }

    public void setBonusIndex(int bonusIndex) {
        this.bonusIndex = bonusIndex;
    }

    public void getBonusResult(String lastContact){
        switch (lastContact){
            case "s1":
                setScores(150);
                setScoreStrings("$"+getScores());
                bonusIndex = 1;
                break;
            case "s2":
                setScores(350);
                setScoreStrings("$"+getScores());
                bonusIndex = 2;
                break;
            case "s3":
                setScores(200);
                setScoreStrings("$"+getScores());
                bonusIndex = 3;
                break;
            case "s4":
                setScores(500);
                setScoreStrings("$"+getScores());
                bonusIndex = 4;
                break;
            case "s5":
                setScores(1000);
                setScoreStrings("$"+getScores());
                bonusIndex = 5;
                break;
            case "s6":
                setScores(800);
                setScoreStrings("$"+getScores());
                bonusIndex = 6;
                break;
            case "s7":
                setScores(750);
                setScoreStrings("$"+getScores());
                bonusIndex = 7;
                break;
            case "s8":
                setScores(650);
                setScoreStrings("$"+getScores());
                bonusIndex = 8;
                break;
            case "s9":
                setScores(210);
                setScoreStrings("$"+getScores());
                bonusIndex = 9;
                break;
            case "s10":
                setScores(50);
                setScoreStrings("$"+getScores());
                bonusIndex = 10;
                break;
            case "s11":
                setScores(185);
                setScoreStrings("$"+getScores());
                bonusIndex = 11;
                break;
            case "s12":
                setScores(5000);
                setScoreStrings("$"+getScores());
                bonusIndex = 12;
                break;
            case "s13":
            case "s14":
            case "s15":
                setScores(100);
                setScoreStrings("$"+getScores());
                bonusIndex = 13;
                break;
            case "s16":
                setScores(800);
                setScoreStrings("$"+getScores());
                bonusIndex = 14;
                break;

            case "s17":
                setScores(400);
                setScoreStrings("$"+getScores());
                bonusIndex = 15;
                break;
            case "s18":
                setScores(525);
                setScoreStrings("$"+getScores());
                bonusIndex = 16;
                break;
            case "s19":
                setScores(300);
                setScoreStrings("$"+getScores());
                bonusIndex = 17;
                break;
            case "s20":
                setScores(3000);
                setScoreStrings("$"+getScores());
                bonusIndex = 18;
                break;
            case "s21":
                setScores(950);
                setScoreStrings("$"+getScores());
                bonusIndex = 19;
                break;
            case "s22":
                setScores(75);
                setScoreStrings("$"+getScores());
                bonusIndex = 20;
                break;
            case "s23":
                setScores(15);
                setScoreStrings("$"+getScores());
                bonusIndex = 21;
                break;
            case "s24":
                setScores(780);
                setScoreStrings("$"+getScores());
                bonusIndex = 22;
                break;
            case "s25":
                setScores(170);
                setScoreStrings("$"+getScores());
                bonusIndex = 23;
                break;
            case "s26":
                setScores(1500);
                setScoreStrings("$"+getScores());
                bonusIndex = 24;
                break;
            default:
                break;
        }
    }

    public void getResult(String lastContact){
        switch (lastContact){
            case "s1":
            case "s7":
            case "s13":
            case "s15":
                setScores(0);
                setScoreStrings(StringRes.BANKRUPT);
                break;
            case "s2":
                setScores(850);
                setScoreStrings("$"+getScores());
                break;
            case "s3":
                setScores(650);
                setScoreStrings("$"+getScores());
                break;
            case "s19":
                setScores(600);
                setScoreStrings("$"+getScores());
                break;
            case "s4":
                setScores(250);
                setScoreStrings(StringRes.FREETURN);
                break;
            case "s5":
            case "s18":
                setScores(500);
                setScoreStrings("$"+getScores());
                break;
            case "s6":
            case "s9":
            case "s12":
                setScores(300);
                setScoreStrings("$"+getScores());
                break;
            case "s8":
                setScores(2500);
                setScoreStrings("$"+getScores());
                break;
            case "s10":
                setScores(900);
                setScoreStrings("$"+getScores());
                break;
            case "s11":
                setScores(100);
                setScoreStrings(StringRes.GIFT);
                break;
            case "s14":
                setScores(5000);
                setScoreStrings("$"+getScores()+"!");
                break;
            case "s16":
                setScores(550);
                setScoreStrings("$"+getScores());
                break;
            case "s17":
            case "s23":
                setScores(400);
                setScoreStrings("$"+getScores());
                break;
            case "s20":
                setScores(750);
                setScoreStrings("$"+getScores());
                break;
            case "s21":
                setScores(0);
                setScoreStrings(StringRes.LOSTTURN);
                break;
            case "s22":
                setScores(800);
                setScoreStrings("$"+getScores());
                break;
            case "s24":
                setScores(450);
                setScoreStrings("$"+getScores());
                break;
            case "s25":
                setScores(350);
                setScoreStrings("$"+getScores());
                break;
            case "s26":
                setScores(950);
                setScoreStrings("$"+getScores());
                break;
            default:
                setScores(0);
                setScoreStrings("$"+getScores());
        }
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public String getScoreStrings() {
        return scoreStrings;
    }

    public void setScoreStrings(String scoreStrings) {
        this.scoreStrings = scoreStrings;
    }

    public boolean isGifts() {
        return gifts;
    }

    public void setGifts(boolean gifts) {
        this.gifts = gifts;
    }

    public boolean isBonus() {
        return bonus;
    }

    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }
}
