package Utilities; // 03 Apr, 09:17 PM

import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {

    private boolean isDead;

    @Override
    public void beginContact( Contact contact ) {

        Fixture fa = contact.getFixtureA ();
        Fixture fb = contact.getFixtureB ();

        if ( fa == null || fb == null ) return;

    }

    @Override
    public void endContact( Contact contact ) {

        Fixture fa = contact.getFixtureA ();
        Fixture fb = contact.getFixtureB ();

        if ( fa == null || fb == null ) return;

    }

    @Override
    public void preSolve( Contact contact, Manifold oldManifold ) {

    }

    @Override
    public void postSolve( Contact contact, ContactImpulse impulse ) {

    }

    public boolean isDead() {
        return isDead;
    }

}
