package com.somboi.rodaimpian.gdxnew.entitiesnew;

import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.gdxnew.actors.TileBase;
import com.somboi.rodaimpian.gdxnew.games.CpuMovement;

public class CpuMoves {
    public static CpuMovement execute(Array<TileBase> tileBases, boolean haveVocals, boolean haveConsonants, PlayerNew playerNew) {
        final Array<CpuMovement> availableMoves = new Array<>();
        if (haveVocals && playerNew.getScore() >= 250) {
            availableMoves.add(CpuMovement.CHOOSEVOCAL);
        }

        if (haveConsonants) {
            availableMoves.add(CpuMovement.SPIN);
        }


        float percentageComplete = percentageComplete(tileBases);
        if (percentageComplete >= 0.7f) {
            availableMoves.add(CpuMovement.COMPLETE);
        }
        availableMoves.shuffle();

        return availableMoves.first();
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
