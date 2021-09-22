package com.somboi.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.actor.Pointer;
import com.somboi.gdx.actor.WheelActor;
import com.somboi.gdx.base.BaseScreen;
import com.somboi.gdx.listener.WorldContact;
import com.somboi.gdx.utils.BodyEditorLoader;

public class MatchScreen extends BaseScreen {
    private final World world = new World(new Vector2(0, -9f), false);
    private final BodyEditorLoader bodyLoader = new BodyEditorLoader(Gdx.files.internal("roda.json"));
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private WheelActor wheelActor;
    private Vector2 jointVector = new Vector2(4.5f, 12.8f);
    float rotateRandom = 0;
    float initialRotation = 0;
    private Body needleBody;
    private Pointer pointer;
    private boolean startRotate;
    private float balanceDegree;
    private Body wheelBody;
    private final Array<Body>wheelSlots = new Array<>();
    private final Array<Fixture>wheelFixture = new Array<>();

    public MatchScreen(RodaImpian rodaImpian) {
        super(rodaImpian);

        for (int i = 0; i < 26; i++) {
            loadWheelBody("n" + i);
        }

        loadWheelRigidBOdy();
        wheelActor = new WheelActor(new Texture(Gdx.files.internal("wheel.png")));
    worldStage.addActor(wheelActor);
        loadNeedleBody();
        worldCamera.zoom = 0.8f;
        world.setContactListener(new WorldContact(wheelFixture));

    }

    private void loadWheelRigidBOdy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(4.5f, 8f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5f;
        fixtureDef.friction = 0f;
        //     fixtureDef.restitution = 0.8f; // Make it bounce a little bit

        wheelBody = world.createBody(bodyDef);
        bodyLoader.attachFixture(wheelBody, "wheelbody", fixtureDef, 0.0106f, "wheelbody");
        Fixture fixture = wheelBody.createFixture(fixtureDef);
        wheelBody.setUserData("wheelbody");
    }


    private void loadNeedleBody() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = false;
        bodyDef.position.set(jointVector);

        needleBody = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.05f;
        fixtureDef.friction = 0.25f;
      //  fixtureDef.restitution = 0.5f;
        bodyLoader.attachFixture(needleBody, "needle", fixtureDef, 0.009f, "needle");
        Fixture fixture = needleBody.createFixture(fixtureDef);
        fixture.setUserData("needle");
        //  wheelFixture.add(fixture);
        needleBody.setUserData("needle");
        pointer = new Pointer(new Texture(Gdx.files.internal("pointer.png")), needleBody);
     worldStage.addActor(pointer);

        BodyDef jointbodyDef = new BodyDef();
        jointbodyDef.type = BodyDef.BodyType.StaticBody;
        jointbodyDef.fixedRotation = false;
        jointbodyDef.position.set(jointVector);
        Body jointBody;
        jointBody = world.createBody(jointbodyDef);
        FixtureDef jointfixtureDef = new FixtureDef();
        jointfixtureDef.density = 4f;
        jointfixtureDef.friction = 0f;
        bodyLoader.attachFixture(needleBody, "needlejoint", jointfixtureDef, 0.0104f, "needlejoint");
        Fixture jointfixture = jointBody.createFixture(jointfixtureDef);
        jointfixture.setUserData("needlejoint");
        //  wheelFixture.add(fixture);

        jointBody.setUserData("needlejoint");
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.enableMotor = true;
        revoluteJointDef.initialize(jointBody, needleBody, jointVector);
        world.createJoint(revoluteJointDef);
        revoluteJointDef.lowerAngle = 2f;
    }


    public void loadWheelBody(String name) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(4.5f, 8f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
       // fixtureDef.density = 3f;
       // fixtureDef.friction = 3f;
        //     fixtureDef.restitution = 0.8f; // Make it bounce a little bit
        Body body;
        body = world.createBody(bodyDef);
        bodyLoader.attachFixture(body, name, fixtureDef, 0.0110f, name);
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(name);
        body.setUserData(name);
        wheelSlots.add(body);
        wheelFixture.add(fixture);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        debugRenderer.render(world, worldStage.getCamera().combined);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        world.step(1 / 60f, 6, 2);

        if (Gdx.input.isTouched()) {
            logger.debug("balance degree 1"+pointer.getRotation());
            rotateRandom = MathUtils.random(4f, 6f);
            initialRotation = new Float(rotateRandom);
            startRotate = true;
        }

        if (rotateRandom > 0 && startRotate ) {
            rotateRandom -= delta ;
            wheelActor.setRotation(wheelActor.getRotation() - (rotateRandom / initialRotation) * 10);
            if (wheelActor.getRotation() <= 0) {
                wheelActor.setRotation(360);
            }

            if (rotateRandom <= 0) {
                balanceDegree = new Float(pointer.getRotation());
                startRotate = false;
            }
        }

        if (!startRotate){
            if (balanceDegree>0){
                logger.debug("wheel rotation "+balanceDegree);

                wheelActor.addAction(Actions.rotateBy(5.3388147f, 3f));
                balanceDegree = 0;

            }
        }


            //   body.applyAngularImpulse(100 ,false);
    for(Body body: wheelSlots){
        body.setTransform(4.5f, 8f, (float) (wheelActor.getRotation() * (Math.PI / 180)));

    }
        wheelBody.setTransform(4.5f, 8f, (float) ((wheelActor.getRotation()-2.6) * (Math.PI / 180)));

        pointer.setRotation((float) (needleBody.getAngle() * (180f / Math.PI)));
    }
}
