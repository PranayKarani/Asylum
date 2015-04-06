package Primary; // 01 Apr, 11:52 AM

import Secondary.Player;
import Secondary.Room;
import Secondary.RoomManager;
import Secondary.Rooms.Courtyard;
import Utilities.MyContactListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {

    public static MyContactListener cl;
    public RoomManager roomManager;
    public Player player;
    public World world;

    public GameWorld() {
        world = new World(new Vector2 (0, -10f), true);
        player = new Player (world, new Vector2 (10, 10));
        roomManager = new RoomManager ();
        roomManager.setRoom(new Courtyard(world, roomManager, player));
        cl = new MyContactListener ();
        world.setContactListener (cl);
    }

    public GameWorld(GameWorld gameWorld, Player player, Room room) {
        this.world = gameWorld.world;
        this.player = player;
        this.roomManager = gameWorld.roomManager;
        this.roomManager.setRoom(room);
    }

    public void update( float delta ) {

        world.step (delta, 3, 1);
        roomManager.update ();
        player.update ();
    }

    /**
     * store player statistics somewhere.<br>
     * Called when:<br>
     * <li> player pauses the game<br>
     * <li> game loses the focus<br>
     * <li> player exits the game<br>
     */
    public void pause() {

    }

    /**
     * restore player statistics from somewhere
     */
    public void resume() {

    }

    public void dispose() {

        pause ();
        world.dispose ();

    }

}
