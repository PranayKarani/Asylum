package Secondary.Rooms; // 01 Apr, 12:34 PM

import Secondary.Room;
import Secondary.RoomManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Courtyard extends Room {

    public Courtyard( World world, RoomManager roomManager ) {
        super (world, roomManager);
        create_room ();
        System.out.println ("entered courtyard");

    }

    public void create_room() {
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set (0, 0);
        body = world.createBody (bdef);
        shape.setAsBox (100, 5);
        fdef.shape = shape;
        body.createFixture (fdef);
    }

    public void update_room() {

        System.out.println ("in courtyard");
        if( Gdx.input.isKeyPressed (Input.Keys.D)) {
            roomManager.exitRoom (this);
            roomManager.setRoom (new Lobby (world, roomManager));
        }
    }

    public void destroy_room() {
        if ( canDestroyRoom ) {
            canDestroyRoom = false;
            world.destroyBody (body);
        }
    }

}
