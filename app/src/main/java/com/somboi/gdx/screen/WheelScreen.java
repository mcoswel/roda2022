package com.somboi.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.actor.Pointer;
import com.somboi.gdx.actor.ResultLabel;
import com.somboi.gdx.actor.WheelActor;
import com.somboi.gdx.assets.StringRes;
import com.somboi.gdx.base.BaseScreen;
import com.somboi.gdx.base.ModeBase;
import com.somboi.gdx.entities.WheelParam;
import com.somboi.gdx.listener.WorldContact;
import com.somboi.gdx.utils.BodyEditorLoader;

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
    private final WheelParam wheelParam;
    private final Image centerlogo;
    private final WorldContact contact;
    private final ModeBase modeBase;

    public WheelScreen(RodaImpian rodaImpian, ModeBase modeBase) {
        super(rodaImpian);
        this.wheelParam = modeBase.getWheelParam();
        loadWheelRigidBOdy();
        this.modeBase = modeBase;
        wheelActor = new WheelActor(textureAtlas.findRegion("wheel"));
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
    }

    @Override
    public void show() {
        worldStage.addActor(centerlogo);
        worldStage.addActor(wheelActor);
        wheelActor.setPosition(4.5f - wheelActor.getWidth() / 2, 8f - wheelActor.getHeight() / 2 - 0.08f);
        worldStage.addActor(pointer);
        readyToSpin();
    }

    public void readyToSpin() {
        Gdx.input.setInputProcessor(null);

        if (!modeBase.getActivePlayer().isAi && modeBase.getActivePlayer().turn) {
            Gdx.input.setInputProcessor(worldStage);
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
        Fixture fixture = needleBody.createFixture(fixtureDef);
        fixture.setUserData("needle");
        //  wheelFixture.add(fixture);
        needleBody.setUserData("needle");
        pointer = new Pointer(textureAtlas.findRegion("pointer"), needleBody);

        BodyDef jointbodyDef = new BodyDef();
        jointbodyDef.type = BodyDef.BodyType.StaticBody;
        jointbodyDef.fixedRotation = false;
        jointbodyDef.position.set(jointVector);
        Body jointBody;
        jointBody = world.createBody(jointbodyDef);
        FixtureDef jointfixtureDef = new FixtureDef();
        jointfixtureDef.density = 4f;
        jointfixtureDef.friction = 0f;
        bodyLoader.attachFixture(needleBody, "needlejoint", jointfixtureDef, 0.01f, "needlejoint");
        Fixture jointfixture = jointBody.createFixture(jointfixtureDef);
        jointfixture.setUserData("needlejoint");
        //  wheelFixture.add(fixture);

        jointBody.setUserData("needlejoint");
        RevoluteJointDef revoluteJointDef;
        revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.enableMotor = false;
        revoluteJointDef.enableLimit = true;
        revoluteJointDef.lowerAngle = 0f;
        revoluteJointDef.initialize(jointBody, needleBody, jointVector);

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
        world.step(1 / 60f, 6, 2);

        wheelActor.setRotation((float) (wheelBody.getAngle() * (180 / Math.PI)));
        pointer.setRotation((float) (needleBody.getAngle() * (180 / Math.PI)));
        for (Body body : wheelSlots) {
            body.setTransform(wheelBody.getPosition(), wheelBody.getAngle());
        }
        if (wheelActor.getDeltaY() > 2f) {
            wheelBody.applyAngularImpulse(-MathUtils.random(49f, 69f) * wheelActor.getDeltaY(), false);
            wheelActor.resetDeltaY();
            startRotate = true;
            contact.setSpinning(true);
        }
        if (startRotate) {
            if ((int) wheelBody.getAngularVelocity() == 0) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        checkContact();
                    }
                }, 1.5f);
                startRotate = false;
                contact.setSpinning(false);
            }
        }

        wheelParam.wheelangle = wheelBody.getAngle();
    }

    private void checkContact() {
        switch (contact.getLastContact()) {
            case "n0":
                wheelParam.resultValue = 2500;
                wheelParam.results = "$2500";
                break;
            case "n1":
            case "n4":
            case "n24":
                wheelParam.resultValue = 300;
                wheelParam.results = "$300";
                break;
            case "n2":
            case "n14":
                wheelParam.resultValue = 900;
                wheelParam.results = "$900";
                break;
            case "n3":
                wheelParam.results = StringRes.GIFT;
                modeBase.setGiftsStage();
                wheelParam.resultValue = modeBase.getGifts().getGiftsValue();
                break;

            case "n5":
            case "n7":
            case "n15":
            case "n25":
                wheelParam.resultValue = 0;
                wheelParam.results = StringRes.BANKRUPT;

                break;
            case "n6":
                wheelParam.resultValue = 5000;
                wheelParam.results = "Bonus $5000";
                break;
            case "n8":
                wheelParam.resultValue = 550;
                wheelParam.results = "$550";
                break;
            case "n9":
                wheelParam.resultValue = 400;
                wheelParam.results = "$400";
                break;
            case "n10":
            case "n13":
            case "n21":
            case "n23":
                wheelParam.resultValue = 500;
                wheelParam.results = "$500";
                break;
            case "n11":
                wheelParam.resultValue = 600;
                wheelParam.results = "$600";
                break;
            case "n12":
                wheelParam.resultValue = 350;
                wheelParam.results = "$350";
                break;
            case "n16":
                wheelParam.resultValue = 650;
                wheelParam.results = "$650";
                break;
            case "n17":
                wheelParam.resultValue = 250;
                wheelParam.results = StringRes.FREETURN;
                break;
            case "n18":
                wheelParam.resultValue = 700;
                wheelParam.results = "$700";
                break;
            case "n19":
                wheelParam.resultValue = 0;
                wheelParam.results = StringRes.LOSTTURN;
                break;
            case "n20":
                wheelParam.resultValue = 800;
                wheelParam.results = "$800";
                break;
            case "n22":
                wheelParam.resultValue = 450;
                wheelParam.results = "$450";
                break;
        }
        final ResultLabel resultLabel = new ResultLabel(wheelParam.results, skin);
        if (wheelParam.resultValue==5000){
            resultLabel.setColor(Color.GOLDENROD);
        }

        if (wheelParam.results.equals(StringRes.BANKRUPT)){
            resultLabel.setColor(Color.RED);
        }

        stage.addActor(resultLabel);
        if (wheelParam.results.equals(StringRes.BANKRUPT)|| wheelParam.results.equals(StringRes.LOSTTURN)){
            gameSound.playAww();
        }
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                resultLabel.remove();
                if(wheelParam.results.equals(StringRes.BANKRUPT)){
                    modeBase.getActivePlayer().currentScore = 0;
                    modeBase.changeTurn();
                }else if(wheelParam.results.equals(StringRes.LOSTTURN)){
                    modeBase.changeTurn();
                } else if (modeBase.getActivePlayer().isAi) {
                    modeBase.cpuChooseConsonants();
                }
                else {
                    if (modeBase.getActivePlayer().turn) {
                        modeBase.showConsonants();
                    }
                }

                rodaImpian.gotoMatch();
            }
        }, 2f);
    }
}
