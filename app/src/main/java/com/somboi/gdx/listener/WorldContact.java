package com.somboi.gdx.listener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;

public class WorldContact implements ContactListener {

    private final Logger logger = new Logger(this.getClass().getName(), 3);
    private final Array<Fixture>wheelFixture;

    public WorldContact(Array<Fixture> wheelFixture) {
        this.wheelFixture = wheelFixture;
    }

    @Override
    public void beginContact(Contact contact) {
        for (Fixture f: wheelFixture){
            if (contact.getFixtureA()!=null){
                if (f.getUserData().equals(contact.getFixtureA().getUserData())){
                    logger.debug("contact "+contact.getFixtureA().getUserData());
                }
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
