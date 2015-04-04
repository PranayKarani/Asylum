package Primary; // 01 Apr, 11:52 AM

import Secondary.Player;
import Secondary.RoomManager;
import Secondary.Rooms.Lobby;
import Utilities.MyContactListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {

    public static MyContactListener cl;
    public Player player;
    RoomManager roomManager;
    World world;

    public GameWorld() {

        world = new World(new Vector2 (0, -10f), true);
        player = new Player (world);
        roomManager = new RoomManager ();
        roomManager.setRoom (new Lobby (world, roomManager));
        cl = new MyContactListener ();
        world.setContactListener (cl);
    }

    public void update( float delta ) {

        world.step (delta, 3, 1);
        player.update ();
        roomManager.update (player.getBody ());

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
