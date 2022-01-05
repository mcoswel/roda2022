package com.somboi.rodaimpian.gdxnew.entitiesnew;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdxnew.actors.PlayerGuis;
import com.somboi.rodaimpian.gdxnew.actors.PlayerMenu;
import com.somboi.rodaimpian.gdxnew.actors.TileBase;
import com.somboi.rodaimpian.gdxnew.games.BaseGame;
import com.somboi.rodaimpian.gdxnew.games.CpuMovement;

public class AiMoves {
    private final BaseGame baseGame;
    private float completion;
    private PlayerMenu playerMenu;
    private Array<TileBase> tileBases;
    private PlayerGuis playerGuis;
    public AiMoves(BaseGame baseGame) {
        this.baseGame = baseGame;
    }

    public void execute(Array<TileBase> tileBases, PlayerMenu playerMenu, PlayerNew playerNew, PlayerGuis playerGuis) {
        final Array<CpuMovement> availableMoves = new Array<>();
        completion = percentageComplete(tileBases);
        this.playerMenu = playerMenu;
        this.tileBases = tileBases;
        this.playerGuis = playerGuis;
        if (playerNew.getScore()>=250 && playerMenu.vocalAvailable()){
            availableMoves.add(CpuMovement.CHOOSEVOCAL);
        }

        if (playerMenu.consonantAvailable()){
            availableMoves.add(CpuMovement.SPIN);
        }

        if (completion>0.75f){
            availableMoves.add(CpuMovement.COMPLETE);
        }

        availableMoves.shuffle();
        CpuMovement executeMove = availableMoves.first();
        if (executeMove.equals(CpuMovement.SPIN)){
            playerGuis.chat(StringRes.CPUPUTAR.random());
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    baseGame.spinWheel(true);
                }
            },1.7f);
        }

    }

    private void wrongComplete(){

    }

    private void truComplete(){

    }

    private void chooseCorrectVocals(){

    }

    private void chooseRandomVocals(){

    }

    public void chooseConsonants(){
        float random = MathUtils.random(0,1f);
        if (random<completion){
            chooseCorrectConsonants();
        }else{
            chooseRandomConsonants();
        }
    }

    private void chooseCorrectConsonants(){
        Array<String>correctCons = new Array<>();
        for (TileBase t: tileBases){
            correctCons.add(t.getLetter());
        }
        correctCons.shuffle();
        playerGuis.chat(StringRes.CPUCHOOSECONSONANTS.random()+correctCons.first());
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                baseGame.checkAnswer(correctCons.first());
            }
        },1.3f);
    }

    private void chooseRandomConsonants(){
        GameConfig.GROUP_CONS_ONE.shuffle();
        GameConfig.GROUP_CONS_TWO.shuffle();
        GameConfig.GROUP_CONS_THREE.shuffle();
        for (Character c: GameConfig.GROUP_CONS_ONE){
            String c1 = String.valueOf(c);
            if (playerMenu.getConsonantLetter().toString().contains(c1)){
                chooseConsGroupOne(c1);
                return;
            }
        }

        for (Character c: GameConfig.GROUP_CONS_TWO){
            String c1 = String.valueOf(c);
            if (playerMenu.getConsonantLetter().toString().contains(c1)){
                chooseConsGroupTwo(c1);
                return;
            }
        }
        for (Character c: GameConfig.GROUP_CONS_THREE){
            String c1 = String.valueOf(c);
            if (playerMenu.getConsonantLetter().toString().contains(c1)){
                chooseConsGroupThree(c1);
                return;
            }
        }
    }

    private void chooseConsGroupOne(String answer){
        playerGuis.chat(StringRes.CPUCHOOSECONSONANTS.random()+answer);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                baseGame.checkAnswer(answer);
            }
        },1f);
    }
    private void chooseConsGroupTwo(String answer){

    }
    private void chooseConsGroupThree(String answer){

    }


    public static float percentageComplete(Array<TileBase> tileBases) {
        int revealed = 0;
        for (TileBase t : tileBases) {
            if (t.isRevealed()) {
                revealed += 1;
            }
        }
        return (float) revealed / tileBases.size;
    }

    public static boolean questionHaveVocals(Array<TileBase> tileBases) {
        String questions = "";
        for (TileBase t : tileBases) {
            questions += t.getLetter();
        }
        String vocals = "AEIOU";
        if (questions.contains(vocals)) {
            return true;
        }
        return false;
    }


    public static boolean questionHaveConsonants(Array<TileBase> tileBases) {
        String questions = "";
        for (TileBase t : tileBases) {
            questions += t.getLetter();
        }
        String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
        if (questions.contains(consonants)) {
            return true;
        }
        return false;
    }
}
