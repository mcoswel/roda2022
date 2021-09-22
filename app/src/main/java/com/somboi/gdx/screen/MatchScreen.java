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
import com.badlogic.gdx.utils.Array;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.actor.WheelActor;
import com.somboi.gdx.base.BaseScreen;
import com.somboi.gdx.utils.BodyEditorLoader;

public class MatchScreen extends BaseScreen {
    private final World world = new World(new Vector2(0, -9f), false);
    private final BodyEditorLoader bodyLoader = new BodyEditorLoader(Gdx.files.internal("roda.json"));
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private final Array<Body> wheelBodies = new Array<>();
    private WheelActor wheelActor;
    private Vector2 jointVector = new Vector2(4.5f,13.15f);
    float rotateRandom = 0;
    Body wheelJoint;

    public MatchScreen(RodaImpian rodaImpian) {
        super(rodaImpian);
        loadWheelJoint();

        for (int i = 0; i < 26; i++) {
            loadWheelBody("n" + i);
        }

        loadNeedleBody();
        wheelActor = new WheelActor(new Texture(Gdx.files.internal("wheel.png")));
        worldStage.addActor(wheelActor);
        worldCamera.zoom=0.8f;

    }

    private void loadWheelJoint() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = false;
        bodyDef.position.set(4.5f, 8f);
        wheelJoint = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5f;
        fixtureDef.friction = 0f;
        bodyLoader.attachFixture(wheelJoint, "centerjoint", fixtureDef, 0.01f, "centerjoint");
        Fixture fixture = wheelJoint.createFixture(fixtureDef);
        fixture.setUserData("centerjoint");
        //  wheelFixture.add(fixture);
        wheelJoint.setUserData("centerjoint");
    }

    private void loadNeedleBody() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = false;
        bodyDef.position.set(jointVector);
        Body needleBody;
        needleBody = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5f;
        fixtureDef.friction = 0f;

        bodyLoader.attachFixture(needleBody, "needle", fixtureDef, 0.01f, "needle");
        Fixture fixture = needleBody.createFixture(fixtureDef);
        fixture.setUserData("needle");
        //  wheelFixture.add(fixture);
        needleBody.setUserData("needle");


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
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.enableMotor = true;
        revoluteJointDef.initialize(jointBody, needleBody, jointVector);
        world.createJoint(revoluteJointDef);
        revoluteJointDef.lowerAngle=2f;
    }


    public void loadWheelBody(String name) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(4.5f, 8f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 3f;
        fixtureDef.friction = 3f;
        //     fixtureDef.restitution = 0.8f; // Make it bounce a little bit
        Body body;
        body = world.createBody(bodyDef);
        bodyLoader.attachFixture(body, name, fixtureDef, 0.011f, name);
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(name);
        //  wheelFixture.add(fixture);
        body.setUserData(name);
        wheelBodies.add(body);


        // wheelBody.add(body);
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
            rotateRandom = MathUtils.random(10f,20f);
        }

        if (rotateRandom>0) {
            rotateRandom-=delta;
            for (Body body : wheelBodies) {
             //   body.applyAngularImpulse(100 ,false);
                wheelActor.setRotation(wheelActor.getRotation() -rotateRandom*delta);
                body.setTransform(4.5f, 8f, (float) (wheelActor.getRotation() * (Math.PI / 180)));
            }
        }

    }
}
