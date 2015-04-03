package Secondary.Rooms; // 01 Apr, 12:13 PM

import Secondary.Room;
import Secondary.RoomManager;
import Utilities.GameAssets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Lobby extends Room { // 150cm + 30cm(entrance)

    TiledMap tiledMap;
    MapLayer mapLayer;
    int noofPoints;
    int noofDoors;
    PolygonShape[] Doors;
    Vector2[] chainpoints;
    Vector2[] doorCenter;

    public Lobby( World world, RoomManager roomManager ) {
        super (world, roomManager);
        tiledMap = GameAssets.assetManager.get ("tmx files/lobby.tmx", TiledMap.class);
        mapLayer = tiledMap.getLayers ().get ("shape");
        for ( MapObject mapObject : mapLayer.getObjects () ) noofPoints++;
        chainpoints = new Vector2[noofPoints];
        for ( int i = 0; i < noofPoints; i++ ) {
            MapObject mapObject = mapLayer.getObjects ().get (i);
            if ( mapObject instanceof EllipseMapObject ) {
                Ellipse ellipse = ((EllipseMapObject) mapObject).getEllipse ();
                float eX = ellipse.x / 100;
                float eY = ellipse.y / 100;
                chainpoints[ i ] = new Vector2 (eX, eY);
            }
        }
        noofDoors = tiledMap.getLayers ().get ("Doors").getObjects ().getCount ();
        Doors = new PolygonShape[ noofDoors ];
        doorCenter = new Vector2[ noofDoors ];
        mapLayer = tiledMap.getLayers ().get ("Doors");
        for ( int i = 0; i < noofDoors; i++ ) {
            Doors[ i ] = new PolygonShape ();
            doorCenter[ i ] = new Vector2 ();
            MapObject mapObject = mapLayer.getObjects ().get (i);
            if ( mapObject instanceof RectangleMapObject ) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle ();
                doorCenter[ i ].x = (rectangle.x + rectangle.width / 2) / 100;
                doorCenter[ i ].y = (rectangle.y + rectangle.height / 2) / 100;
                float width = rectangle.width / 100;
                float height = rectangle.height / 100;
                Doors[ i ].setAsBox (width / 2, height / 2, new Vector2 (0, 0), 0);
            }

        }
        create_room ();
        System.out.println ("entered lobby with " + noofDoors + " doors");
    }

    public void create_room() {
        // create room shape
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set (0,0);
        body = world.createBody (bdef);
        chainShape.createChain (chainpoints);
        fdef.shape = chainShape;
        body.createFixture (fdef).setUserData ("room floor");

        for ( int i = 0; i < noofDoors; i++ ) {
            mapLayer = tiledMap.getLayers ().get ("Doors");
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set (doorCenter[ i ]);
            body = world.createBody (bdef);
            fdef.shape = Doors[ i ];
            fdef.isSensor = true;
            body.createFixture (fdef).setUserData (mapLayer.getObjects ().get (i).getName ().toString ());
            Doors[ i ].dispose ();
        }
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
