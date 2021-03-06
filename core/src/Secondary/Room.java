package Secondary; // 01 Apr, 12:01 PM

import Screens.AbstractScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Abstract Parent Room concept which describes what a room is made of and what does it do in the world.<br>
 * It's functions:
 * <ul>
 * <li> declares & initializes common Box2D entites like body, bodyDef etc. to be shared by all its children
 * <li> needs a RoomManager object which is shared by its children
 * <li> shares a room destruction enabling boolean with its children
 * <li> all its children must create, update, destroy themselves accordingly
 * </ul>
 * <b>NOTE: body is to be initialized by children separately <u>by</u> themselves and <u>for</u> themselves only</b>
 */
public abstract class Room{

    public World world;
    public Player player;
    protected Body body;
    protected BodyDef bdef;
    protected FixtureDef fdef;
    protected ChainShape chainShape;
    protected RoomManager roomManager;
    public AbstractScreen previousScreen;
    public static final float doorLength = 75f / 100f;
    public static String message; // for debugging purposees only
    protected boolean canDestroyRoom;
    protected SpriteBatch batch;
    public OrthogonalTiledMapRenderer mapRenderer;

    /**
     * Box2D and other stuff is initialized here and are ready for use...
     * @param world
     * @param roomManager
     */
    public Room(World world, RoomManager roomManager, Player player, SpriteBatch batch) {
        this.world = world;
        this.batch = batch;
        bdef = new BodyDef ();
        fdef = new FixtureDef ();
        chainShape = new ChainShape ();
        this.roomManager = roomManager;
        this.player = player;
        canDestroyRoom = true;

    }

    /**
     *  Abstact method from parent class.<br>
     *  lets it's children to specify code for creating themselves and what happens when they are created.<br>
     *  e.g. A lobby might have different body dimensions than other rooms. This is specified here.
     */
    public abstract void create_room();

    /**
     *  Abstact method from parent class.<br>
     *  lets it's children to specify code for updating themselves and what happens when they update themselves.<br>
     *  e.g. A lobby might behave differently than other rooms. This is specified here.
     */
    public abstract void update_room();

    public abstract void render_room(OrthographicCamera camera);

    /**
     * Abstact method from parent class.<br>
     * lets it's children to specify code for their destruction and what happen when they are destroyed.<br>
     */
    public abstract void destroy_room();

}
