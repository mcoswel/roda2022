package com.somboi.rodaimpian.gdxnew.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.somboi.rodaimpian.gdxnew.assets.AssetDesc;

public class GameSound {
    private final Sound awwSound;
    private final Sound rotateSound;
    private final Sound correctSound;
    private final Sound wrongSound;
    private final Sound winSound;
    private final Sound cheerSound;
    private final Music clockSound;
    private final Sound slapSound;
    private final Sound police;
    private final Sound ambulance;
    private final Sound fire;
    private final Sound twank;

    public GameSound(AssetManager assetManager) {
        awwSound = assetManager.get(AssetDesc.AWWSOUND);
        rotateSound = assetManager.get(AssetDesc.ROTATESOUND);
        correctSound = assetManager.get(AssetDesc.CORRECTSOUND);
        wrongSound = assetManager.get(AssetDesc.WRONGSOUND);
        winSound = assetManager.get(AssetDesc.WINSOUND);
        cheerSound = assetManager.get(AssetDesc.CHEERSOUND);
        clockSound = assetManager.get(AssetDesc.CLOCKSOUND);
        slapSound = assetManager.get(AssetDesc.SLAPSOUND);
        police = assetManager.get(AssetDesc.POLICE);
        ambulance = assetManager.get(AssetDesc.AMBULANCE);
        fire = assetManager.get(AssetDesc.FIRETRUCK);
        twank = assetManager.get(AssetDesc.TWANK);

    }

    public void playAww() {
        awwSound.play();
    }

    public void playRotate() {
        rotateSound.play();
    }
    public void playTwank() {
        twank.play();
    }

    public void playCorrect() {
        correctSound.play();
    }

    public void playWrong() {
        wrongSound.play();
    }

    public void playCheer() {
        cheerSound.play();
    }

    public void playWinSound() {
        winSound.play();
    }

    public void playClockSound() {
        if (!clockSound.isPlaying()) {
            clockSound.play();
        }
    }


    public void playSlapSound() {
        slapSound.play();
    }

    public void playFire() {
        fire.play();
    }

    public void playAmbulance() {
        ambulance.play();
    }

    public void playPolice() {
        police.play();
    }

    public void dispose() {
        awwSound.dispose();
        rotateSound.dispose();
        correctSound.dispose();
        wrongSound.dispose();
        winSound.dispose();
        cheerSound.dispose();
        clockSound.dispose();
        slapSound.dispose();
        police.dispose();
        ambulance.dispose();
        fire.dispose();
    }

}
