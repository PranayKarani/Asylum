package Secondary.Rooms; // 01 Apr, 12:34 PM

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

public class Courtyard extends Room {

    TiledMap tiledMap;
    MapLayer mapLayer;
    byte noofPoints;
    Vector2[] chainpoints;
    // Doors
    byte noofDoors; // used for creating all door related vectors
    public static float toRC;
    public static float toLobby;
    public static float toDayRoom;
    public static float toOffice;
    public static float toDrRoom;

    public Courtyard( World world, RoomManager roomManager, Player player ) {
        super (world, roomManager, player);
        //load tiledmap
        tiledMap = GameAssets.assetManager.get ("tmx files/courtyard.tmx", TiledMap.class);

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

        toRC = centers[ 0 ];
        toLobby = centers[ 1 ];
        toDayRoom = centers[ 2 ];
        toOffice = centers[ 3 ];
        toDrRoom = centers[ 4 ];

        // create actually Box2D Room Body with data gathered above
        create_room ();

        // dispose tiledmap after use
        tiledMap.dispose ();
        System.out.println ("entered lobby with " + noofDoors + " doors");

    }

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

    public void update_room() {

        // to Recreation Hall
        if ( player.getBody ().getPosition ().x > toRC ) {
            if ( Player.act ) {
                roomManager.exitRoom (this);
                roomManager.setRoom (new RCHall (world, roomManager, player));
                player.getBody ().setTransform (RCHall.toCourtyard, player.getBody ().getPosition ().y, 0);
                Player.act = false;
            } else {
                message = "go to RC Hall?";
            }
        }

        if ( player.getBody ().getPosition ().x > toLobby - doorLength && player.getBody ().getPosition ().x < toLobby + doorLength ) {
            if ( Player.act ) {
                roomManager.exitRoom (this);
                roomManager.setRoom (new Lobby (world, roomManager, player));
                player.getBody ().setTransform (Lobby.toCourtyard, player.getBody ().getPosition ().y, 0);
                Player.act = false;
            } else {
                message = "go to Lobby?";
            }
        }

        if ( player.getBody ().getPosition ().x > toDayRoom - doorLength && player.getBody ().getPosition ().x < toDayRoom + doorLength ) {
            if ( Player.act ) {
                roomManager.exitRoom (this);
                roomManager.setRoom (new DayRoom (world, roomManager, player));
                player.getBody ().setTransform (DayRoom.toCourtyard, player.getBody ().getPosition ().y, 0);
                Player.act = false;
            } else {
                message = "go to Day room?";
            }
        }

        if ( player.getBody ().getPosition ().x > toOffice - doorLength && player.getBody ().getPosition ().x < toOffice + doorLength ) {
            if ( Player.act ) {
                roomManager.exitRoom (this);
                roomManager.setRoom (new Office (world, roomManager, player));
                player.getBody ().setTransform (Office.toCourtyard, player.getBody ().getPosition ().y, 0);
                Player.act = false;
            } else {
                message = "go to Office?";
            }
        }

        if ( player.getBody ().getPosition ().x < toDrRoom ) {
            if ( Player.act ) {
                roomManager.exitRoom (this);
                roomManager.setRoom (new DrRoom (world, roomManager, player));
                player.getBody ().setTransform (DrRoom.toCourtyard, player.getBody ().getPosition ().y, 0);
                Player.act = false;
            } else {
                message = "go to Dr. room?";
            }
        }

    }

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
