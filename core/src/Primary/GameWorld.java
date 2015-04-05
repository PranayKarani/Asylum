package Primary; // 01 Apr, 11:52 AM

import Screens.AbstractScreen;
import Secondary.Player;
import Secondary.RoomManager;
import Secondary.Rooms.Courtyard;
import Utilities.MyContactListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {

    public static MyContactListener cl;
    RoomManager roomManager;
    public Player player;
    World world;
    AbstractScreen screen; // screen which contains world ( mostly PlayScreen )

    public GameWorld( AbstractScreen screen ) {
        this.screen = screen;
        world = new World(new Vector2 (0, -10f), true);
        player = new Player (world, new Vector2 (10, 10));
        roomManager = new RoomManager ();
        roomManager.setRoom (new Courtyard (world, roomManager, player, screen));
        cl = new MyContactListener ();
        world.setContactListener (cl);
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
