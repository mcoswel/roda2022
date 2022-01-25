package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.GameSound;
import com.somboi.rodaimpian.gdxnew.actors.SlotResultLabel;
import com.somboi.rodaimpian.gdxnew.box2d.WorldFactory;
import com.somboi.rodaimpian.gdxnew.interfaces.WorldContact;

public class SpinScreen extends BaseScreenNew {
    private final WorldFactory worldFactory;
    //private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    protected final Body wheelBody;
    private final Body needleBody;
    protected final RevoluteJoint needleJoint;
    private float angularForce;
    private float yInitial, yFinal;
    protected boolean startRotation;
    private final Array<Body> slotSensors;
    protected final WorldContact worldContact;
    protected final SlotResultLabel slotResult;
    protected boolean rotated;
    protected final GameSound gameSound;
    private final boolean cpuMoves;
    private boolean bonus;

    public SpinScreen(RodaImpianNew rodaImpianNew, boolean cpuMoves, boolean bonus) {
        super(rodaImpianNew);
        this.cpuMoves = cpuMoves;
        this.bonus = bonus;
        gameSound = new GameSound(assetManager);
        worldContact = new WorldContact(gameSound);
        world.setContactListener(worldContact);
        slotResult = new SlotResultLabel(skin);
        worldFactory = new WorldFactory(world);
        wheelBody = worldFactory.createStopper();
        worldFactory.createWheelRevoluteJoint(wheelBody);
        needleBody = worldFactory.createNeedle();
        needleJoint = worldFactory.createNeedleJoint(needleBody);
        slotSensors = worldFactory.createWheelSlotSensor();

    }


    @Override
    public void show() {
        if (!cpuMoves) {
            Gdx.input.setInputProcessor(stage);
            stage.addListener(new DragListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Vector2 vector2 = stage.screenToStageCoordinates(
                            new Vector2(Gdx.input.getX(), Gdx.input.getY())
                    );
                    yInitial = vector2.y;
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
                    if (angularForce > 500f && !rotated) {
                        //  wheelJoint.setMotorSpeed(angularForce);
                        applyAngularForce(angularForce);
                    }
                }
            });
        } else {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    wheelBody.applyAngularImpulse(-(MathUtils.random(2800, 4000)), true);
                    startRotation = true;
                    rotated = true;
                    needleJoint.setMotorSpeed(60f);
                }
            }, 1f);
        }
        stage.addActor(new Image(assetManager.get(AssetDesc.WHEELBG)));
        actorFactory.createWheel(wheelBody);
        actorFactory.createNeedle(needleBody);
        actorFactory.createLogo(wheelBody);
        wheelBody.setTransform(wheelBody.getPosition(), rodaImpianNew.getWheelParams().getAngle());
        if (bonus) {
            actorFactory.createWheelBonus();
        }
    }

    public void applyAngularForce(float angularForce) {
        wheelBody.applyAngularImpulse(-(angularForce + MathUtils.random(10f, 30f)) * 3, true);
        startRotation = true;
        rotated = true;
        needleJoint.setMotorSpeed(60f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        // debugRenderer.render(world, worldStage.getCamera().combined);
        //logger.debug("wheel rev speed "+wheelBody.getAngularVelocity());
        //logger.debug("angle "+revoluteJoint.getJointAngle());
    }

    @Override
    public void update(float delta) {
        for (Body body : slotSensors) {
            body.setTransform(body.getPosition(), wheelBody.getAngle());
        }
        rodaImpianNew.getWheelParams().setAngle(wheelBody.getAngle());


        if (startRotation) {
            if ((int) wheelBody.getAngularVelocity() == 0) {
                if (needleJoint.getJointAngle() <= -0.5) {
                    needleJoint.setMotorSpeed(1f);
                    needleJoint.setLimits(0, 0);
                } else {
                    needleJoint.setMotorSpeed(0);
                }
                startRotation = false;
                finishSpin();
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

    public void finishSpin(){
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ////after spin
                if (worldContact.getLastContact() != null) {
                    slotResult.addAction(new SequenceAction(Actions.fadeOut(0f), Actions.fadeIn(1f)));
                    stage.addActor(slotResult);
                    if (actorFactory.isWheelBonus()) {
                        rodaImpianNew.getWheelParams().getBonusResult(worldContact.getLastContact());
                    } else {
                        rodaImpianNew.getWheelParams().getResult(worldContact.getLastContact());
                    }
                    slotResult.setText(rodaImpianNew.getWheelParams().getScoreStrings());
                    if (rodaImpianNew.getWheelParams().getScores() == 0) {
                        gameSound.playAww();
                    }
                }
/*
                        logger.debug(
                                "last contact " +
                                        worldContact.getLastContact() +
                                        ", wheel params " +
                                        rodaImpianNew.getWheelParams().getScores() + ", " + rodaImpianNew.getWheelParams().getScoreStrings());*/
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        rodaImpianNew.finishSpin();
                    }
                }, 1.5f);
            }
        }, 2f);
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
    }
}
