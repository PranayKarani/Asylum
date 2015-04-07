package Secondary.Rooms; // 07 Apr, 11:10 AM

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
import com.badlogic.gdx.physics.box2d.World;

public class SickYard extends Room {

    TiledMap tiledMap;
    MapLayer mapLayer;
    byte noofPoints;
    Vector2[] chainpoints;
    // Doors
    byte noofDoors; // used for creating all door related vectors
    public static float toSSSJunction;
    public static float toDormitory;
    public static float toDrRoom;
    public static float toDLSJunction;


    public SickYard(World world, RoomManager roomManager, Player player) {
        super(world, roomManager, player);

        //load tiledmap
        tiledMap = GameAssets.assetManager.get("tmx files/SickYard.tmx", TiledMap.class);

        // take shape layer from tiledmap
        mapLayer = tiledMap.getLayers().get("structure");
        // count structure points (edges)
        for (MapObject ignored : mapLayer.getObjects()) noofPoints++;
        // create array for chain objects from points
        chainpoints = new Vector2[noofPoints];
        // fill that chain object
        for (int i = 0; i < noofPoints; i++) {
            MapObject mapObject = mapLayer.getObjects().get(i);
            if (mapObject instanceof EllipseMapObject) {
                Ellipse ellipse = ((EllipseMapObject) mapObject).getEllipse();
                float eX = ellipse.x / 100;
                float eY = ellipse.y / 100;
                chainpoints[i] = new Vector2(eX, eY);
            }
        }

        // take Doors layer from the tiledmap
        mapLayer = tiledMap.getLayers().get("Doors");
        // count the no.of doors this room has
        noofDoors = (byte) mapLayer.getObjects().getCount();
        float[] centers = new float[noofDoors];
        for (int i = 0; i < noofDoors; i++) {
            MapObject mapObject = mapLayer.getObjects().get(i);
            System.out.println(i + "  = " + mapObject.getName());
            if (mapObject instanceof EllipseMapObject) {
                centers[i] = ((EllipseMapObject) mapObject).getEllipse().x / 100;
            }
        }

        toSSSJunction = centers[0];
        toDormitory = centers[1];
        toDrRoom = centers[2];
        toDLSJunction = centers[3];

        // create actually Box2D Room Body with data gathered above
        create_room();

        // dispose tiledmap after use
        tiledMap.dispose();
        System.out.println("entered Sick Yard with " + noofDoors + " doors");

    }

    @Override
    public void create_room() {

        // create room structure
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(0, 0);
        body = world.createBody(bdef);
        chainShape.createChain(chainpoints);
        fdef.shape = chainShape;
        body.createFixture(fdef).setUserData("room floor");
        chainShape.dispose();

    }

    @Override
    public void update_room() {

    }

    @Override
    public void destroy_room() {

    }
}
