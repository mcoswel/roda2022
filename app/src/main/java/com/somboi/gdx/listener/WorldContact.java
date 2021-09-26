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

    public WorldContact() {
    }

    private String lastContact = "";

    @Override
    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        if (userDataA != null && userDataB != null) {
            lastContact = contact.getFixtureB().getUserData().toString();
        }
            //logger.debug(contact.getFixtureB().getUserData().toString()+"  "+ contact.getFixtureA().getUserData().toString());

                    /*
                    switch (contact.getFixtureA().getUserData().toString()) {
                        case "n0":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 2500");
                            break;
                        case "n1":
                        case "n4":
                        case "n24":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 300");
                            break;
                        case "n2":
                        case "n14":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 900");
                            break;
                        case "n3":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " Gift");
                            break;

                        case "n5":
                        case "n7":
                        case "n15":
                        case "25":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " Muflis");
                            break;
                        case "n6":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 5000");
                            break;
                        case "n8":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 550");
                            break;
                        case "n9":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 400");
                            break;
                        case "n10":
                        case "n13":
                        case "n21":
                        case "n23":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 500");
                            break;
                        case "n11":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 600");
                            break;
                        case "n12":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 350");
                            break;
                        case "n16":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 650");
                            break;
                        case "n17":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " Percuma");
                            break;
                        case "n18":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 700");
                            break;
                        case "n19":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " Hilang Giliran");
                            break;
                        case "n20":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 800");
                            break;
                        case "n22":
                            logger.debug(contact.getFixtureA().getUserData().toString()+ " 450");
                            break;
                    }*/
    }

    public String getLastContact() {
        return lastContact;
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
