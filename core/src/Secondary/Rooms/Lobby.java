package Secondary.Rooms; // 01 Apr, 12:13 PM

import Secondary.Room;
import Secondary.RoomManager;
import Utilities.GameAssets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Lobby extends Room { // 150cm + 30cm(entrance)

    TiledMap tiledMap;
    MapLayer mapLayer;
    int noofPoints;
    Vector2[] chainpoints;

    public Lobby( World world, RoomManager roomManager ) {
        super (world, roomManager);
        tiledMap = GameAssets.assetManager.get ("tmx files/lobby.tmx", TiledMap.class);
        mapLayer = tiledMap.getLayers ().get ("shape");
        for( MapObject mapObject : mapLayer.getObjects ()){
            noofPoints++;
        }
        chainpoints = new Vector2[noofPoints];
        for ( int i = 0; i < noofPoints; i++ ) {

            MapObject mapObject = mapLayer.getObjects ().get (i);

            if ( mapObject instanceof EllipseMapObject ) {

                Ellipse ellipse = ((EllipseMapObject) mapObject).getEllipse ();
                float eX = ellipse.x;
                float eY = ellipse.y;

                chainpoints[ i ] = new Vector2 (eX, eY);

            }

        }
        create_room ();
        System.out.println ("entered lobby");
    }

    public void create_room() {
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set (0,0);
        body = world.createBody (bdef);
        shape.setAsBox (50,5);
        chainShape.createChain (chainpoints);
        fdef.shape = chainShape;
        body.createFixture (fdef);
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
