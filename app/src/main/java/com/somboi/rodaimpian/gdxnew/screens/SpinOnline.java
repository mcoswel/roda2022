package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdxnew.interfaces.OnInterface;
import com.somboi.rodaimpian.gdxnew.onlineclasses.PlayerStates;
import com.somboi.rodaimpian.gdxnew.wheels.WheelParams;

public class SpinOnline extends SpinScreen {
    private final OnInterface onInterface;
    public SpinOnline(RodaImpianNew rodaImpianNew, boolean bonus, OnInterface onInterface) {
        super(rodaImpianNew, false, bonus);
        this.onInterface = onInterface;
    }

    @Override
    public void show() {
        super.show();
        if (!onInterface.isTurn()){
            Gdx.input.setInputProcessor(null);
        }
    }

    @Override
    public void finishSpin() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ////after spin
                if (worldContact.getLastContact() != null) {
                    slotResult.addAction(new SequenceAction(Actions.fadeOut(0f), Actions.fadeIn(1f)));
                    stage.addActor(slotResult);
                    if (actorFactory.isWheelBonus()) {
                        if (onInterface.isTurn()){
                            WheelParams wheelParams = new WheelParams();
                            wheelParams.getBonusResult(worldContact.getLastContact());
                            onInterface.sendObjects(wheelParams);
                        }
                        //rodaImpianNew.getWheelParams().getBonusResult(worldContact.getLastContact());

                    } else {
                        if (onInterface.isTurn()){
                            WheelParams wheelParams = new WheelParams();
                            wheelParams.getResult(worldContact.getLastContact());
                            onInterface.sendObjects(wheelParams);
                        }
                      //  rodaImpianNew.getWheelParams().getResult(worldContact.getLastContact());

                    }

                }


            }
        }, 2f);
    }

    public void showResults(WheelParams wheelParams){
        rodaImpianNew.setWheelParams(wheelParams);
        slotResult.setText(rodaImpianNew.getWheelParams().getScoreStrings());
        if (rodaImpianNew.getWheelParams().getScores() == 0) {
            gameSound.playAww();
        }
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                onInterface.sendObjects(PlayerStates.FINISHSPIN);
            }
        }, 1.5f);
    }
}
