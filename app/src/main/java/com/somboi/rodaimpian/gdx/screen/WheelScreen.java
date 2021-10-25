package com.somboi.rodaimpian.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.DummyPointer;
import com.somboi.rodaimpian.gdx.actor.DummyWheel;
import com.somboi.rodaimpian.gdx.actor.Fingers;
import com.somboi.rodaimpian.gdx.actor.MenuPrompt;
import com.somboi.rodaimpian.gdx.actor.Pointer;
import com.somboi.rodaimpian.gdx.actor.ResultLabel;
import com.somboi.rodaimpian.gdx.actor.StatusLabel;
import com.somboi.rodaimpian.gdx.actor.WheelActor;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.base.BaseScreen;
import com.somboi.rodaimpian.gdx.base.ModeBase;
import com.somboi.rodaimpian.gdx.entities.Bonus;
import com.somboi.rodaimpian.gdx.entities.WheelParam;
import com.somboi.rodaimpian.gdx.listener.WorldContact;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.online.entities.BonusIndex;
import com.somboi.rodaimpian.gdx.online.entities.PlayerState;
import com.somboi.rodaimpian.gdx.online.newentities.FinishSpin;
import com.somboi.rodaimpian.gdx.utils.BodyEditorLoader;

public class WheelScreen extends BaseScreen {
    private final World world = new World(new Vector2(0, -12f), false);
    private BodyEditorLoader bodyLoader = new BodyEditorLoader(Gdx.files.internal("roda.json"));
    private WheelActor wheelActor;
    private Vector2 jointVector = new Vector2(4.5f, 12.45f);
    private Body needleBody;
    private Pointer pointer;
    private boolean startRotate;
    private Body wheelBody;
    private final Array<Body> wheelSlots = new Array<>();
    private Body centerJoint;
    private WheelParam wheelParam;
    private final Image centerlogo;
    private final WorldContact contact;
    private final ModeBase modeBase;
    private boolean firstSpin = true;
    private final Fingers fingers;
    private float idleTimer;
    private float initialRotation;
    private float wheelImpulse;
    private float wheelTimer = 5f;
    private Body needleJoint;
    private StatusLabel statusLabel;
    private boolean showingResult;
    private boolean finishSpin;
    //private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private DummyWheel dummyWheel;
    private DummyPointer dummyPointer;

    public WheelScreen(RodaImpian rodaImpian, ModeBase modeBase) {
        super(rodaImpian);
        this.wheelParam = modeBase.getWheelParam();
        loadWheelRigidBOdy();
        this.modeBase = modeBase;
        wheelActor = new WheelActor(textureAtlas.findRegion("wheel"));
        fingers = new Fingers(textureAtlas);
        loadNeedleBody();
        worldCamera.zoom = 0.8f;
        centerlogo = new Image(textureAtlas.findRegion("centerlogo"));
        centerlogo.setSize(3.2f, 3.2f);
        centerlogo.setPosition(4.5f - 3.2f / 2f, 7.9f - 3.2f / 2f);
        contact = new WorldContact(gameSound);
        world.setContactListener(contact);
        for (int i = 0; i < 26; i++) {
            loadWheelSlots("n" + i);
        }

        worldStage.addActor(centerlogo);
        worldStage.addActor(wheelActor);
        wheelActor.setPosition(4.5f - wheelActor.getWidth() / 2, 8f - wheelActor.getHeight() / 2 - 0.08f);
        worldStage.addActor(pointer);

        if (rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
            dummyWheel = new DummyWheel(textureAtlas.findRegion("wheel"));
            dummyPointer = new DummyPointer(textureAtlas.findRegion("pointer"));
        }
    }

    @Override
    public void show() {
        idleTimer = 20f;
        startRotate = false;
        showingResult = false;
        finishSpin = false;
        contact.setSpinning(false);
        wheelActor.resetDeltaY();
        //wheelParam.wheelImpulse = 0;
        wheelImpulse = 0;

        if (modeBase.getGameRound() == 3) {
            wheelActor.setDrawable(new SpriteDrawable(new Sprite(textureAtlas.findRegion("wheelbonus"))));
        }
        if (!modeBase.getActivePlayer().isAi && firstSpin && !rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
            worldStage.addActor(fingers);
        } else {
            fingers.remove();
        }
        firstSpin = false;

        /*else{
            fingers.remove();
        }*/
        //  wheelBody.setTransform(wheelBody.getPosition(), modeBase.getWheelParam().wheelangle);

        if (rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
            if (!rodaImpian.getPlayer().turn) {
                wheelActor.remove();
                pointer.remove();
                worldStage.addActor(dummyWheel);
                worldStage.addActor(dummyPointer);

                if (modeBase.getGameRound() == 3) {
                    dummyWheel.setDrawable(new SpriteDrawable(new Sprite(textureAtlas.findRegion("wheelbonus"))));
                }

            } else {
                dummyWheel.remove();
                dummyPointer.remove();
                worldStage.addActor(wheelActor);
                worldStage.addActor(pointer);
            }
        }

        readyToSpin();
    }

    public void readyToSpin() {
        Gdx.input.setInputProcessor(null);

        if (!modeBase.getActivePlayer().isAi) {
            if (rodaImpian.getPlayer().turn) {
                Gdx.input.setInputProcessor(worldStage);
            }
            if (rodaImpian.getGameModes().equals(GameModes.LOCALMULTI)) {
                Gdx.input.setInputProcessor(worldStage);
            }
        } else {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    wheelActor.setDeltaY(MathUtils.random(150f, 250f));
                }
            }, 1.5f);

        }

    }


    private void loadWheelRigidBOdy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0f, 0f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5f;
        fixtureDef.friction = 2f;
        //     fixtureDef.restitution = 0.8f; // Make it bounce a little bit
        wheelBody = world.createBody(bodyDef);

        bodyLoader.attachFixture(wheelBody, "wheelrigid", fixtureDef, 0.01f, null);
        wheelBody.createFixture(fixtureDef);


        BodyDef jointbodyDef = new BodyDef();
        jointbodyDef.type = BodyDef.BodyType.StaticBody;
        jointbodyDef.fixedRotation = true;
        jointbodyDef.position.set(4.5f, 8f);

        centerJoint = world.createBody(jointbodyDef);
        FixtureDef jointfixtureDef = new FixtureDef();
        jointfixtureDef.density = 4f;
        jointfixtureDef.friction = 0f;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(3f);
        Fixture fixture = centerJoint.createFixture(circleShape, 1);
        circleShape.dispose();


        RevoluteJointDef wheelRigidJoint = new RevoluteJointDef();
        wheelRigidJoint.enableMotor = false;
        wheelRigidJoint.maxMotorTorque = 360;
        wheelRigidJoint.motorSpeed = 10f * MathUtils.degreesToRadians;
        wheelRigidJoint.initialize(wheelBody, centerJoint, new Vector2(4.5f, 8f - 0.05f));
        world.createJoint(wheelRigidJoint);


    }


    private void loadNeedleBody() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = false;
        bodyDef.position.set(jointVector);

        needleBody = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.2f;
        fixtureDef.friction = 0.25f;
        //  fixtureDef.restitution = 0.5f;
        bodyLoader.attachFixture(needleBody, "needle", fixtureDef, 0.01f, "needle");
        Fixture needleBodyFixture = needleBody.createFixture(fixtureDef);
        needleBodyFixture.setUserData("needle");

        //  wheelFixture.add(fixture);
        needleBody.setUserData("needle");
        pointer = new Pointer(textureAtlas.findRegion("pointer"), needleBody);

        BodyDef jointbodyDef = new BodyDef();
        jointbodyDef.type = BodyDef.BodyType.StaticBody;
        jointbodyDef.fixedRotation = false;
        jointbodyDef.position.set(jointVector);
        needleJoint = world.createBody(jointbodyDef);
        FixtureDef jointfixtureDef = new FixtureDef();
        jointfixtureDef.density = 4f;
        jointfixtureDef.friction = 0f;
        bodyLoader.attachFixture(needleBody, "needlejoint", jointfixtureDef, 0.01f, "needlejoint");
        Fixture jointfixture = needleJoint.createFixture(jointfixtureDef);
        jointfixture.setUserData("needlejoint");
        //  wheelFixture.add(fixture);

        needleJoint.setUserData("needlejoint");
        RevoluteJointDef revoluteJointDef;
        revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.enableMotor = false;
        revoluteJointDef.enableLimit = true;
        revoluteJointDef.lowerAngle = 0f;
        revoluteJointDef.initialize(needleJoint, needleBody, jointVector);

        world.createJoint(revoluteJointDef);

        // revoluteJointDef.lowerAngle = 2f;
    }


    public void loadWheelSlots(String name) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(0f, 0f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        // fixtureDef.density = 3f;
        // fixtureDef.friction = 3f;
        //     fixtureDef.restitution = 0.8f; // Make it bounce a little bit
        Body body;
        body = world.createBody(bodyDef);
        bodyLoader.attachFixture(body, name, fixtureDef, 0.01f, name);
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(name);
        body.setUserData(name);
        wheelSlots.add(body);
        // wheelFixture.add(fixture);
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.enableMotor = true;
        revoluteJointDef.initialize(body, centerJoint, new Vector2(4.5f, 8f - 0.05f));
        world.createJoint(revoluteJointDef);


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //debugRenderer.render(world, worldStage.getCamera().combined);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (!modeBase.getActivePlayer().isAi) {
            if (rodaImpian.getPlayer().turn) {
                Gdx.input.setInputProcessor(worldStage);
            }
            if (rodaImpian.getGameModes().equals(GameModes.LOCALMULTI)) {
                Gdx.input.setInputProcessor(worldStage);
            }
        }
        idleTimer -= delta;
        if (!startRotate && rodaImpian.getPlayer().turn) {
            if (idleTimer <= 0) {

                wheelActor.setDeltaY(-MathUtils.random(150f, 250f));

                idleTimer = 20f;
            }
        }


        world.step(1 / 60f, 6, 2);
        wheelActor.setRotation((float) (wheelBody.getAngle() * (180 / Math.PI)));
        pointer.setRotation((float) (needleBody.getAngle() * (180 / Math.PI)));

        for (Body body : wheelSlots) {
            body.setTransform(wheelBody.getPosition(), wheelBody.getAngle());
        }


        if (wheelActor.getDeltaY() > 2f && !startRotate) {
            fingers.remove();
            float impulse = -MathUtils.random(49f, 69f) * wheelActor.getDeltaY();
            wheelBody.applyAngularImpulse(impulse, false);
            wheelActor.resetDeltaY();
            startRotate = true;
            contact.setSpinning(true);
        }

        //  logger.debug("Wheel impulse "+wheelBody.isActive());

        if (startRotate && !rodaImpian.getGameModes().equals(GameModes.ONLINE) && !finishSpin) {
            //  logger.debug("Wheel velocit "+wheelBody.getAngularVelocity());
            if (wheelBody.getAngularVelocity() > 0) {
                //  wheelBody.setTransform(wheelBody.getPosition(), modeBase.getWheelParam().wheelangle);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (!showingResult) {
                            if (modeBase.getGameRound() == 3) {
                                Bonus bonus = new Bonus(textureAtlas);
                                bonus.getWheelResult(wheelParam, contact.getLastContact());
                                modeBase.setBonus(bonus);
                                showResult();
                            } else {
                                checkContact();
                            }
                            showingResult = true;
                        }
                    }
                }, 2.5f);
                finishSpin = true;
            }
        }
        if (startRotate && rodaImpian.getGameModes().equals(GameModes.ONLINE) && !finishSpin) {
            if (rodaImpian.getPlayer().turn) {
                wheelParam.wheelangle = wheelBody.getAngle();
                modeBase.sendObject(wheelParam);
                if (wheelBody.getAngularVelocity() > 0) {
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if (!showingResult) {
                                if (modeBase.getGameRound() == 3) {
                                    Bonus bonus = new Bonus(textureAtlas);
                                    bonus.getWheelResult(wheelParam, contact.getLastContact());
                                    BonusIndex bonusIndex = new BonusIndex();
                                    bonusIndex.index = bonus.getBonusIndex();
                                    bonusIndex.value = wheelParam.resultValue;
                                    modeBase.sendObject(bonusIndex);
                                } else {
                                    checkContact();
                                }
                                showingResult = true;
                            }
                        }
                    }, 3f);

                        /*Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                modeBase.sendObject(GameState.WHEELALMOSTSTOP);
                            }
                        },1.5f);*/

                    finishSpin = true;
                }
            }
        }
        if (rodaImpian.getGameModes().equals(GameModes.ONLINE) && !rodaImpian.getPlayer().turn) {
            wheelBody.setTransform(wheelBody.getPosition(), modeBase.getWheelParam().wheelangle);
            dummyWheel.setRotation((float) (wheelBody.getAngle() * (180 / Math.PI)));
        }

        ///////////online

        // wheelParam.wheelangle = wheelBody.getAngle();


        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            MenuPrompt menuPrompt = new MenuPrompt(rodaImpian, skin);
            menuPrompt.show(stage);
        }

    }


    public void checkContact() {

        switch (contact.getLastContact()) {
            case "n0":
                wheelParam.resultValue = 2500;
                wheelParam.results = "$2500";
                wheelParam.giftIndex = 0;
                break;
            case "n1":
            case "n4":
            case "n24":
                wheelParam.resultValue = 300;
                wheelParam.results = "$300";
                wheelParam.giftIndex = 0;
                break;
            case "n2":
            case "n14":
                wheelParam.resultValue = 900;
                wheelParam.results = "$900";
                wheelParam.giftIndex = 0;
                break;
            case "n3":
                wheelParam.results = StringRes.GIFT;
                if (!rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
                    modeBase.setGiftsStage();
                } else {
                    modeBase.getGifts().generateGifts();
                }
                wheelParam.giftIndex = modeBase.getGiftIndex();
                wheelParam.resultValue = modeBase.getGifts().getGiftsValue();
                break;

            case "n5":
            case "n7":
            case "n15":
            case "n25":
                wheelParam.resultValue = 0;
                wheelParam.results = StringRes.BANKRUPT;
                wheelParam.giftIndex = 0;
                break;
            case "n6":
                wheelParam.resultValue = 5000;
                wheelParam.results = "Bonus $5000";
                wheelParam.giftIndex = 0;
                break;
            case "n8":
                wheelParam.resultValue = 550;
                wheelParam.results = "$550";
                wheelParam.giftIndex = 0;
                break;
            case "n9":
                wheelParam.resultValue = 400;
                wheelParam.results = "$400";
                wheelParam.giftIndex = 0;
                break;
            case "n10":
            case "n13":
            case "n21":
            case "n23":
                wheelParam.resultValue = 500;
                wheelParam.results = "$500";
                wheelParam.giftIndex = 0;
                break;
            case "n11":
                wheelParam.resultValue = 600;
                wheelParam.results = "$600";
                wheelParam.giftIndex = 0;
                break;
            case "n12":
                wheelParam.resultValue = 350;
                wheelParam.results = "$350";
                wheelParam.giftIndex = 0;
                break;
            case "n16":
                wheelParam.resultValue = 650;
                wheelParam.results = "$650";wheelParam.giftIndex = 0;

                break;
            case "n17":
                wheelParam.resultValue = 250;
                wheelParam.results = StringRes.FREETURN;
                wheelParam.giftIndex = 0;
                break;
            case "n18":
                wheelParam.resultValue = 700;
                wheelParam.results = "$700";wheelParam.giftIndex = 0;

                break;
            case "n19":
                wheelParam.resultValue = 0;
                wheelParam.results = StringRes.LOSTTURN;
                wheelParam.giftIndex = 0;
                break;
            case "n20":
                wheelParam.resultValue = 800;
                wheelParam.results = "$800";
                wheelParam.giftIndex = 0;
                break;
            case "n22":
                wheelParam.resultValue = 450;
                wheelParam.results = "$450";
                wheelParam.giftIndex = 0;
                break;
        }


        if (rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
            if (rodaImpian.getPlayer().turn) {
                WheelParam tobeSend = new WheelParam();
                tobeSend.results = wheelParam.results;
                tobeSend.wheelangle = wheelBody.getAngle();
                tobeSend.resultValue = wheelParam.resultValue;
                tobeSend.giftIndex = wheelParam.giftIndex;
                FinishSpin finishSpin = new FinishSpin();
                finishSpin.wheelParam = tobeSend;
                modeBase.sendObject(finishSpin);
            }
        } else {
            showResult();
        }

    }

    public void applyImpulse(float wheelImpulse) {

        wheelBody.applyAngularImpulse(wheelImpulse, false);
        wheelActor.resetDeltaY();
        startRotate = true;
        contact.setSpinning(true);

    }

    public void setWheelParamResults(WheelParam wheelParam) {
        this.wheelParam.results = wheelParam.results;
        this.wheelParam.resultValue = wheelParam.resultValue;
    }

    public void showResult() {

        final ResultLabel resultLabel = new ResultLabel(wheelParam.results, skin);
        if (wheelParam.resultValue == 5000) {
            resultLabel.setColor(Color.GOLDENROD);
            modeBase.getVanna().hostThumbsUp();

        }

        if (wheelParam.results.equals(StringRes.BANKRUPT)) {
            resultLabel.setColor(Color.RED);
            modeBase.flyingMoney();
        }
        if (wheelParam.results.equals(StringRes.GIFT)) {
            modeBase.getVanna().hostThumbsUp();
        }

        stage.addActor(resultLabel);

        if (wheelParam.results.equals(StringRes.BANKRUPT) || wheelParam.results.equals(StringRes.LOSTTURN)) {
            gameSound.playAww();
            modeBase.getVanna().hostWrong();

        }
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                resultLabel.remove();
                if (wheelParam.results.equals(StringRes.BANKRUPT)) {
                    modeBase.getActivePlayer().bankrupt++;
                    modeBase.getActivePlayer().currentScore = 0;
                    if (modeBase.getActivePlayer().freeTurn) {
                        modeBase.getActivePlayer().freeTurn = false;
                        modeBase.getPlayerGuis().get(modeBase.getActivePlayer().guiIndex).removeFreeTurn();
                    }
                    modeBase.changeTurn();
                } else if (wheelParam.results.equals(StringRes.LOSTTURN)) {
                    modeBase.changeTurn();
                } else if (modeBase.getActivePlayer().isAi) {
                    modeBase.cpuChooseConsonants();
                } else if (modeBase.getGameRound() == 3) {
                    modeBase.bonusRound();
                }


                if (wheelParam.resultValue > 0 && !modeBase.getActivePlayer().isAi && modeBase.getGameRound() != 3) {
                    if (rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
                        if (rodaImpian.getPlayer().turn) {
                            modeBase.showConsonants();
                        }
                    } else {
                        modeBase.showConsonants();
                    }
                }

                if (!rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
                    rodaImpian.gotoMatch();
                } else {
                    if (wheelParam.resultValue == 0) {
                        modeBase.sendObject(PlayerState.BANKRUPT);
                    } else {
                        modeBase.hideMenu();
                        modeBase.sendObject(PlayerState.GOTOMATCH);
                    }

                }
            }
        }, 2f);
    }


    public void stop() {
        playing = false;
    }
}
