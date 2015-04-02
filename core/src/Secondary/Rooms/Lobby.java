package Secondary.Rooms; // 01 Apr, 12:13 PM

import Secondary.Room;
import Secondary.RoomManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.*;

public class Lobby extends Room {

    private Body body;
    protected BodyDef bdef;
    protected FixtureDef fdef;
    protected PolygonShape shape;
    protected RoomManager rm;

    public Lobby( World world ){
        super(world);

        bdef = new BodyDef ();
        fdef = new FixtureDef ();
        shape = new PolygonShape ();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set (0,0);
        body = world.createBody (bdef);
        shape.setAsBox (50,5);
        fdef.shape = shape;
        body.createFixture (fdef);
        System.out.println ("entered lobby");

    }

    public void update(){

        System.out.println ("in lobby");
        if( Gdx.input.isKeyPressed (Input.Keys.A)) {
            rm.exitRoom (this);
            rm.setRoom (new Courtyard (world));
        }
    }

    public void destroy(){
        world.destroyBody (body);
    }

}
