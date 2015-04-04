package Secondary; // 03 Apr, 07:53 PM

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player {

    public static boolean isfacingRight;
    public static boolean istorch_ON;
    public static boolean act;
    public boolean canDestroy;
    // controls
    public boolean rightPressed, leftPressed, torchToggle;
    public STATE state;
    World world;
    Body body;
    BodyDef bodyDef;
    FixtureDef fixtureDef;
    PolygonShape shape;

    public Player( World world, Vector2 spawnpoint ) {
        this.world = world;

        bodyDef = new BodyDef ();
        fixtureDef = new FixtureDef ();
        shape = new PolygonShape ();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set (spawnpoint);
        body = world.createBody (bodyDef);

        shape.setAsBox (0.6f / 2, (200 / 100) / 2, body.getLocalCenter (), 0);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        body.createFixture (fixtureDef).setUserData ("player");

        // sensor..
        shape.setAsBox ((0.6f - (0.6f / 32f)) / 2, 0.05f,
                               new Vector2 (body.getLocalCenter ().x, body.getLocalCenter ().y - (200 / 100) / 2),
                               0);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        body.createFixture (fixtureDef).setUserData ("foot");

        shape.dispose ();
        canDestroy = true;
        state = STATE.standing;

    }

    public void update() {
        if ( rightPressed && !leftPressed ) {

            isfacingRight = true;
            body.applyForceToCenter (10, 0, true);

        }

        if ( leftPressed && !rightPressed ) {

            isfacingRight = false;
            body.applyForceToCenter (-10, 0, true);

        }

        if ( torchToggle ) {

            istorch_ON = !istorch_ON;

        }

        // velocity control..
        if ( (int) body.getLinearVelocity ().x > 2 ) {
            body.setLinearVelocity (2, body.getLinearVelocity ().y);
        }
        if ( (int) body.getLinearVelocity ().x < -2 ) {
            body.setLinearVelocity (-2, body.getLinearVelocity ().y);
        }
    }

    public void destroy() {

        if ( canDestroy ) {
            canDestroy = false;
            world.destroyBody (body);
        }
    }

    public Body getBody() {
        return body;
    }

    public enum STATE {running, standing, jumping}

}
