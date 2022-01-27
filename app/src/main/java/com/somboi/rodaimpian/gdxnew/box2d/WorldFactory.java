package com.somboi.rodaimpian.gdxnew.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.gdxnew.utils.BodyEditorLoader;

public class WorldFactory {
    private final World world;
    private BodyEditorLoader bodyLoader = new BodyEditorLoader(Gdx.files.internal("box2d/box2d.json"));
    private Body wheelJointBody;
    public WorldFactory(World world) {
        this.world = world;
    }
    private final float offsetNeedle = 0.1f;
    public Body createStopper() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 10f;
        fixtureDef.friction = 0f;
        Body body = world.createBody(bodyDef);
        bodyLoader.attachFixture(body, "stopper", fixtureDef, 0.01f, "stopper");
        body.setTransform(body.getPosition().x + (5.25f) - 0.6f, body.getPosition().y + 7f, 0);
        return body;
    }

    public Body createNeedle() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 2.5f;
        fixtureDef.friction = 5f;
        Body body = world.createBody(bodyDef);
        bodyLoader.attachFixture(body, "needle", fixtureDef, 0.01f, "needle");
        body.setTransform(body.getPosition().x - 1.25f, body.getPosition().y + 1.25f-offsetNeedle, 0);
        return body;
    }



    private Body createWheelJointBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density =0f;
        fixtureDef.friction = 0f;
        Body body = world.createBody(bodyDef);
        body.setTransform(body.getPosition().x + (5.25f) - 0.6f, body.getPosition().y + 7f, 0);
        bodyLoader.attachFixture(body, "wheeljoint", fixtureDef, 0.01f, "wheeljoint");
        return body;
    }

    public Array<Body> createWheelSlotSensor(){
        final Array<Body> slotBodies = new Array<>();
        for (int i=1; i<=26; i++){
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.KinematicBody;
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density =0f;
            fixtureDef.isSensor = true;
            fixtureDef.friction = 0f;
            Body body = world.createBody(bodyDef);
            body.setTransform(body.getPosition().x + (5.25f) - 0.6f, body.getPosition().y + 7f, 0);
            bodyLoader.attachFixture(body, "s"+i, fixtureDef, 0.01f, "s"+i);
            slotBodies.add(body);
        }
        return slotBodies;
    }

    public RevoluteJoint createWheelRevoluteJoint(Body wheelbody) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = wheelbody;
        wheelJointBody = createWheelJointBody();
        jointDef.bodyB = wheelJointBody;
        jointDef.collideConnected = false;

        return (RevoluteJoint) world.createJoint(jointDef);
    }

    private Body createNeedJointBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0f;
        fixtureDef.friction = 0f;
        Body body = world.createBody(bodyDef);
        body.setTransform(body.getPosition().x - 1.25f, body.getPosition().y + 1.25f-offsetNeedle, 0);
        bodyLoader.attachFixture(body, "needlejoint", fixtureDef, 0.01f, "needlejoint");

        return body;
    }

    public RevoluteJoint createNeedleJoint(Body needle) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = needle;
        jointDef.bodyB = createNeedJointBody();
        jointDef.collideConnected = false;
        jointDef.localAnchorA.set(5.75f, 11.4f);
        jointDef.localAnchorB.set(5.75f, 11.4f);
        jointDef.enableLimit = true;
        jointDef.lowerAngle = 0;
        jointDef.upperAngle = 0;
        jointDef.enableMotor = true;
        jointDef.maxMotorTorque = 100f;
        return (RevoluteJoint) world.createJoint(jointDef);
    }

}
