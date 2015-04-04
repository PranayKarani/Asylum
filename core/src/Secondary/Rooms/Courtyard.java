package Secondary.Rooms; // 01 Apr, 12:34 PM

import Secondary.Player;
import Secondary.Room;
import Secondary.RoomManager;
import Utilities.GameAssets;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Courtyard extends Room {

    TiledMap tiledMap;
    MapLayer mapLayer;
    byte noofPoints;
    Vector2[] chainpoints;
    // Doors
    byte noofDoors; // used for creating all door related vectors
    PolygonShape[] Doors;   // store door structre points
    Vector2[] doorCenter;   // store door centers
    String[] doorName;      // store door names

    public Courtyard( World world, RoomManager roomManager ) {
        super (world, roomManager);
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
        // create array for storing door shapes
        Doors = new PolygonShape[ noofDoors ];
        // create array for storing door spawn points
        doorCenter = new Vector2[ noofDoors ];
        // create array for storing door names
        doorName = new String[ noofDoors ];
        // fill all the door related arrays
        for ( int i = 0; i < noofDoors; i++ ) {
            Doors[ i ] = new PolygonShape (); // create empty door shapes
            doorCenter[ i ] = new Vector2 (); // create empty door spawn points
            // fill door names
            doorName[ i ] = mapLayer.getObjects ().get (i).getName ().toString ();
            MapObject mapObject = mapLayer.getObjects ().get (i);
            if ( mapObject instanceof RectangleMapObject ) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle ();
                // fill door spawn points
                doorCenter[ i ].x = (rectangle.x + rectangle.width / 2) / 100;
                doorCenter[ i ].y = (rectangle.y + rectangle.height / 2) / 100;
                float width = rectangle.width / 100;
                float height = rectangle.height / 100;
                // fill door shapes
                Doors[ i ].setAsBox (width / 2, height / 2, new Vector2 (0, 0), 0);
            }

        }

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

        // create doors
        for ( int i = 0; i < noofDoors; i++ ) {
            mapLayer = tiledMap.getLayers ().get ("Doors");
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set (doorCenter[ i ]);
            body = world.createBody (bdef);
            fdef.shape = Doors[ i ];
            fdef.isSensor = true;
            body.createFixture (fdef).setUserData (doorName[ i ]);
            Doors[ i ].dispose ();
        }
        chainShape.dispose ();
    }

    public void update_room( Body playerBody ) {

        if ( Player.act ) {
            System.out.println ("ACT!!");
        }

    }

    public void destroy_room() {
        if ( canDestroyRoom ) {
            canDestroyRoom = false;
            world.destroyBody (body);
        }
    }

}
