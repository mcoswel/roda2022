package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.activities.RodaImpianNew;
import com.somboi.rodaimpian.gdxnew.interfaces.OnInterface;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ApplyForce;
import com.somboi.rodaimpian.gdxnew.onlineclasses.PlayerStates;
import com.somboi.rodaimpian.gdxnew.onlineclasses.WheelBodyAngle;
import com.somboi.rodaimpian.gdxnew.wheels.WheelParams;

public class SpinOnline extends SpinScreen {
    private final OnInterface onInterface;
    private boolean spun;
    public SpinOnline(RodaImpianNew rodaImpianNew, boolean bonus, OnInterface onInterface) {
        super(rodaImpianNew, false, bonus);
        this.onInterface = onInterface;
    }

    @Override
    public void show() {
        super.show();
        spun = false;
        startRotation = false;
        rotated = false;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void applyAngularForce(float angularForce) {
        if (!spun && onInterface.isTurn()) {
            ApplyForce applyForce = new ApplyForce();
            int force = (int) angularForce;
            applyForce.setForce(force);
            onInterface.sendObjects(applyForce);
            spun = true;
        }

    }

    public void spinWheel(ApplyForce applyForce) {
        //   logger.debug("angular force");
        if (!rotated) {
            wheelBody.applyAngularImpulse(-(applyForce.getForce()) * 2, true);
            startRotation = true;
            rotated = true;
            needleJoint.setMotorSpeed(60f);
        }
    }

    public void setWheelBodyAngle(WheelBodyAngle wheelBodyAngle){
        wheelBody.setTransform(wheelBody.getPosition(), wheelBodyAngle.getAngle());
    }

    @Override
    public void finishSpin() {
        if (onInterface.isTurn()) {
           sendWheelAngle();
        }
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ////after spin
                if (worldContact.getLastContact() != null) {
                    if (onInterface.isTurn()) {
                        sendWheelAngle();
                    }
                    slotResult.addAction(new SequenceAction(Actions.fadeOut(0f), Actions.fadeIn(1f)));
                    stage.addActor(slotResult);
                    if (onInterface.isTurn()) {
                        WheelParams wheelParams = new WheelParams();
                        wheelParams.setAngle(wheelParams.getAngle());
                        if (actorFactory.isWheelBonus()) {
                            wheelParams.getBonusResult(worldContact.getLastContact());
                        } else {
                            wheelParams.getResult(worldContact.getLastContact());
                        }
                        onInterface.sendObjects(wheelParams);

                        //rodaImpianNew.getWheelParams().getBonusResult(worldContact.getLastContact());
                    }
                    //  rodaImpianNew.getWheelParams().getResult(worldContact.getLastContact());
                }


            }
        }, 2f);
    }

    private void sendWheelAngle(){
        WheelBodyAngle wheelBodyAngle = new WheelBodyAngle();
        wheelBodyAngle.setAngle(wheelBody.getAngle());
        onInterface.sendObjects(wheelBodyAngle);
    }

    public void showResults(WheelParams wheelParams) {
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
