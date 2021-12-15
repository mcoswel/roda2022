package com.somboi.rodaimpian.gdxnew.interfaces;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Logger;

public class WorldContact implements ContactListener {
    private String lastContact;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    @Override
    public void beginContact(Contact contact) {
        String userA = contact.getFixtureA().getUserData().toString()   ;
        String userB = contact.getFixtureB().getUserData().toString()   ;
        if (userA!=null && userB!=null){
            if (userA.contains("needle") && !userB.equals("stopper")){
                lastContact = userB;
            }
            if (userB.contains("needle") && !userA.equals("stopper")){
                lastContact = userA;
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

    public String getLastContact() {
        return lastContact;
    }
}
