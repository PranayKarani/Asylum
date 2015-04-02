package Secondary.Rooms; // 01 Apr, 12:34 PM

import Secondary.Room;
import Secondary.RoomManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.*;

public class Courtyard extends Room {

    private Body body;
    protected BodyDef bdef;
    protected FixtureDef fdef;
    protected PolygonShape shape;
    protected RoomManager rm;

    public Courtyard(World world){
        super(world);

        bdef = new BodyDef ();
        fdef = new FixtureDef ();
        shape = new PolygonShape ();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set (10,10);
        body = world.createBody (bdef);
        shape.setAsBox (70,6);
        fdef.shape = shape;
        body.createFixture (fdef);
        System.out.println ("entered courtyard");
    }

    public void update(){

        System.out.println ("in courtyard");
        rm.exitRoom (this);
        if( Gdx.input.isKeyPressed (Input.Keys.D)) {
            rm.exitRoom (this);
            rm.setRoom (new Lobby (world));
        }
    }

    public void destroy(){
        world.destroyBody (body);
    }

}
