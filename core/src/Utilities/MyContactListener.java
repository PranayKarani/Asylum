package Utilities; // 03 Apr, 09:17 PM

import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {

    public int jumpCounter;
    private int onGround;
    private boolean isDead;

    @Override
    public void beginContact( Contact contact ) {

        Fixture fa = contact.getFixtureA ();
        Fixture fb = contact.getFixtureB ();

        if ( fa == null || fb == null ) return;

        if ( fb != null && fb.getUserData ().equals ("player") && fa != null && fa.getUserData ().equals ("toCourtyard") ) {
            System.out.println ("enter courtyard?");
        }

        if ( fa != null && fa.getUserData ().equals ("player") && fb != null && fb.getUserData ().equals ("room floor") ) {
            System.out.println ("enter garden?");
        }

    }

    @Override
    public void endContact( Contact contact ) {

    }

    @Override
    public void preSolve( Contact contact, Manifold oldManifold ) {

    }

    @Override
    public void postSolve( Contact contact, ContactImpulse impulse ) {

    }

    public boolean isOnGround() {
        return onGround > 0;
    }
}
