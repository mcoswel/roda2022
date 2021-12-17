package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdxnew.actors.ActorFactory;
import com.somboi.rodaimpian.gdxnew.actors.BodyImage;
import com.somboi.rodaimpian.gdxnew.actors.SlotResultLabel;
import com.somboi.rodaimpian.gdxnew.box2d.WorldFactory;
import com.somboi.rodaimpian.gdxnew.interfaces.WorldContact;

public class SpinScreen extends BaseScreenNew {
    private final WorldFactory worldFactory;
   // private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private final Body wheelBody;
    private final Body needleBody;
    private final RevoluteJoint needleJoint;
    private final RevoluteJoint wheelJoint;
    private float angularForce;
    private float yInitial, yFinal;
    private boolean startRotation;
    private final Array<Body>slotSensors;
    private final WorldContact worldContact = new WorldContact();
    private final SlotResultLabel slotResult;
    public SpinScreen(RodaImpianNew rodaImpianNew) {
        super(rodaImpianNew);
        Gdx.input.setInputProcessor(stage);
        world.setContactListener(worldContact);
        slotResult = new SlotResultLabel(skin);
        worldFactory = new WorldFactory(world);
        wheelBody = worldFactory.createStopper();
        wheelJoint = worldFactory.createWheelRevoluteJoint(wheelBody);
        needleBody = worldFactory.createNeedle();
        needleJoint = worldFactory.createNeedleJoint(needleBody);
        slotSensors = worldFactory.createWheelSlotSensor();
        stage.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 vector2 = stage.screenToStageCoordinates(
                        new Vector2(Gdx.input.getX(), Gdx.input.getY())
                );
                yInitial = vector2.y;
                logger.debug("stage coordinate "+vector2);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Vector2 vector2 = stage.screenToStageCoordinates(
                        new Vector2(Gdx.input.getX(), Gdx.input.getY())
                );
                yFinal = vector2.y;
                angularForce = (yInitial - yFinal);
                if (angularForce > 500f) {
                  //  wheelJoint.setMotorSpeed(angularForce);
                    wheelBody.applyAngularImpulse(-(angularForce+MathUtils.random(10f,30f)), true);
                    startRotation = true;
                    needleJoint.setMotorSpeed(60f);
                }
            }
        });

    }

    @Override
    public void show() {
        stage.addActor(new Image(assetManager.get(AssetDesc.WHEELBG)));
        stage.addActor(slotResult);
        actorFactory.createWheel(wheelBody);
        actorFactory.createNeedle(needleBody);
        actorFactory.createLogo(wheelBody);
        //actorFactory.createWheelBonus();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
      //  debugRenderer.render(world, worldStage.getCamera().combined);
        //logger.debug("wheel rev speed "+wheelBody.getAngularVelocity());
        //logger.debug("angle "+revoluteJoint.getJointAngle());
    }

    @Override
    public void update(float delta) {
        for (Body body: slotSensors){
            body.setTransform(body.getPosition(),wheelBody.getAngle());
        }
        rodaImpianNew.getWheelParams().setAngle(wheelBody.getAngle()* MathUtils.radDeg);

        if (worldContact.getLastContact()!=null) {
            if (actorFactory.isWheelBonus()) {
                rodaImpianNew.getWheelParams().getBonusResult(worldContact.getLastContact());
            } else {
                rodaImpianNew.getWheelParams().getResult(worldContact.getLastContact());
            }

            slotResult.setText(rodaImpianNew.getWheelParams().getScoreStrings());
        }

        if (startRotation) {
            if ((int) wheelBody.getAngularVelocity() == 0) {
                if (needleJoint.getJointAngle()<=-0.5){
                    needleJoint.setMotorSpeed(1f);
                    needleJoint.setLimits(0,0);
                }else{
                    needleJoint.setMotorSpeed(0);
                }
                startRotation = false;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                       ////after spin
                        logger.debug(
                                "last contact "+
                                        worldContact.getLastContact()+
                                        ", wheel params "+
                                        rodaImpianNew.getWheelParams().getScores()+", "+rodaImpianNew.getWheelParams().getScoreStrings());
                    }
                },2f);

            }
        }

/*

        if ((int)wheelBody.getAngularVelocity()<0){
            needleJoint.setMotorSpeed(20f);
        }else{
            needleJoint.setMotorSpeed(25f);
        }

        if (needleJoint.getJointAngle()<0){
            wheelJoint.setMotorSpeed(-10f);
        }else{
            wheelJoint.setMotorSpeed(0f);
        }
*/

    }
}
