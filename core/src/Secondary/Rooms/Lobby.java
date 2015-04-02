package Secondary.Rooms; // 01 Apr, 12:13 PM

import Secondary.Room;
import Secondary.RoomManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Lobby extends Room {

    public Lobby( World world, RoomManager roomManager ) {
        super (world, roomManager);
        create_room ();
        System.out.println ("entered lobby");
    }

    public void create_room() {
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set (0,0);
        body = world.createBody (bdef);
        shape.setAsBox (50,5);
        fdef.shape = shape;
        body.createFixture (fdef);
    }

    public void update_room() {

        System.out.println ("in lobby");
        if( Gdx.input.isKeyPressed (Input.Keys.A)) {
            roomManager.exitRoom (this);
            roomManager.setRoom (new Courtyard (world, roomManager));
        }
    }

    public void destroy_room() {

        if ( canDestroyRoom ) {
            canDestroyRoom = false;
            world.destroyBody (body);
        }

    }

}
