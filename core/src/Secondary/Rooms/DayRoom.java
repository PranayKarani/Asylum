package Secondary.Rooms; // 04 Apr, 06:52 PM

import Screens.JunctionScreen;
import Screens.PlayScreen;
import Secondary.Player;
import Secondary.Room;
import Secondary.RoomManager;
import Utilities.GameAssets;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class DayRoom extends Room {

    TiledMap tiledMap;
    MapLayer mapLayer;
    byte noofPoints;
    Vector2[] chainpoints;
    // Doors
    byte noofDoors; // used for creating all door related vectors
    public static float toCourtyard;
    public static float toLaundry;
    public static float toDiningHall;
    public static float toSickRoom;
    public static float toStoreRoom;
    public static float toDLSJunction;

    public DayRoom(World world, RoomManager roomManager, Player player) {
        super(world, roomManager, player);

        //load tiledmap
        tiledMap = GameAssets.assetManager.get ("tmx files/DayRoom.tmx", TiledMap.class);

        // take shape layer from tiledmap
        mapLayer = tiledMap.getLayers ().get ("structure");
        // count structure points (edges)
        for ( MapObject ignored : mapLayer.getObjects () ) noofPoints++;
        // create array for chain objects from points
        chainpoints = new Vector2[ noofPoints ];
        // fill that chain object
        for ( int i = 0; i < noofPoints; i++ ) {
            MapObject mapObject = mapLayer.getObjects ().get (i);
            if ( mapObject instanceof EllipseMapObject ) {
                Ellipse ellipse = ((EllipseMapObject) mapObject).getEllipse ();
                float eX = ellipse.x / 100;
                float eY = ellipse.y / 100;
                chainpoints[ i ] = new Vector2 (eX, eY);
            }
        }

        // take Doors layer from the tiledmap
        mapLayer = tiledMap.getLayers ().get ("Doors");
        // count the no.of doors this room has
        noofDoors = (byte) mapLayer.getObjects ().getCount ();
        float[] centers = new float[ noofDoors ];
        for ( int i = 0; i < noofDoors; i++ ) {
            MapObject mapObject = mapLayer.getObjects ().get (i);
            System.out.println (i + "  = " + mapObject.getName ());
            if ( mapObject instanceof EllipseMapObject ) {
                centers[ i ] = ((EllipseMapObject) mapObject).getEllipse ().x / 100;
            }
        }

        toDLSJunction = centers[0];
        toCourtyard = centers[ 1 ];
        toDiningHall = centers[ 2 ];
        toStoreRoom = centers[ 3 ];

        // create actually Box2D Room Body with data gathered above
        create_room ();

        // dispose tiledmap after use
        tiledMap.dispose ();
        System.out.println ("entered Day room with " + noofDoors + " doors");

    }

    @Override
    public void create_room() {

        // create room structure
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set (0, 0);
        body = world.createBody (bdef);
        chainShape.createChain (chainpoints);
        fdef.shape = chainShape;
        body.createFixture (fdef).setUserData ("room floor");
        chainShape.dispose ();

    }

    @Override
    public void update_room() {

        if (player.getBody().getPosition().x < toDLSJunction) {

            if ( Player.act ) {

                PlayScreen.isPaused = true;
                PlayScreen.enterJuction = true;
                PlayScreen.selectedJunction = JunctionScreen.D_LSy;
                roomManager.exitRoom(this);
                Player.act = false;

            } else {
                message = "to DLS junction?";
            }
        }

        if ( player.getBody ().getPosition ().x > toCourtyard - doorLength && player.getBody ().getPosition ().x < toCourtyard + doorLength ) {
            if ( Player.act ) {
                roomManager.exitRoom (this);
                roomManager.setRoom(new Courtyard(world, roomManager, player));
                player.getBody().setTransform(Courtyard.toDayRoom, player.getBody().getPosition().y, 0);
                player.getBody().setLinearVelocity(0, 0);
                Player.act = false;
            } else {
                message = "go to Courtyard?";
            }
        }
        if ( player.getBody ().getPosition ().x > toDiningHall - doorLength && player.getBody ().getPosition ().x < toDiningHall + doorLength ) {
            if ( Player.act ) {
                roomManager.exitRoom(this);
                roomManager.setRoom(new DiningHall(world, roomManager, player));
                player.getBody().setTransform(DiningHall.toDayRoom, player.getBody().getPosition().y, 0);
                Player.act = false;
            } else {
                message = "go to Dining hall?";
            }
        }
        if ( player.getBody ().getPosition ().x > toStoreRoom ) {
            if ( Player.act ) {
                roomManager.exitRoom(this);
                roomManager.setRoom(new StoreRoom(world, roomManager, player));
                player.getBody().setTransform(StoreRoom.toDayRoom, player.getBody().getPosition().y, 0);
                player.getBody().setLinearVelocity(0, 0);
                Player.act = false;
            } else {
                message = "go to Store room?";
            }
        }


    }

    @Override
    public void destroy_room() {

        if ( canDestroyRoom ) {
            canDestroyRoom = false;
            for ( Fixture fixture : body.getFixtureList () ) {
                body.destroyFixture (fixture);
            }
            world.destroyBody (body);
        }

    }
}
