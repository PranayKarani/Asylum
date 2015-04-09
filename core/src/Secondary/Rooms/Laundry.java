package Secondary.Rooms; // 04 Apr, 10:08 PM

import Screens.JunctionScreen;
import Screens.PlayScreen;
import Secondary.Player;
import Secondary.Room;
import Secondary.RoomManager;
import Utilities.GameAssets;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Laundry extends Room {


    TiledMap tiledMap;
    MapLayer mapLayer;
    byte noofPoints;
    Vector2[] chainpoints;
    // Doors
    byte noofDoors; // used for creating all door related vectors
    public static float toDLSJunction;
    public static float toDormitory;

    public Laundry(World world, RoomManager roomManager, Player player, SpriteBatch batch) {
        super(world, roomManager, player, batch);

        //load tiledmap
        tiledMap = GameAssets.assetManager.get("tmx files/Laundry.tmx", TiledMap.class);

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

        toDormitory = centers[0];
        toDLSJunction = centers[1];

        // create actually Box2D Room Body with data gathered above
        create_room();

        // dispose tiledmap after use
        tiledMap.dispose();
        System.out.println("entered Laundry with " + noofDoors + " doors");

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

        if (player.getBody().getPosition().x < toDormitory) {
            if (Player.act) {
                roomManager.exitRoom(this);
                roomManager.setRoom(new Dormitory(world, roomManager, player, batch));
                player.getBody().setTransform(Dormitory.toLaundry, player.getBody().getPosition().y, 0);
                player.getBody().setLinearVelocity(0, 0);
                Player.act = false;
            } else {
                message = "go to Dormitory?";
            }
        }

        if (player.getBody().getPosition().x > toDLSJunction) {

            if (Player.act) {

                PlayScreen.isPaused = true;
                PlayScreen.enterJuction = true;
                PlayScreen.selectedJunction = JunctionScreen.L_DSy;
                roomManager.exitRoom(this);
                Player.act = false;

            } else {
                message = "to DLS junction?";
            }
        }

    }

    @Override
    public void render_room(OrthographicCamera camera) {

    }

    @Override
    public void destroy_room() {

        if (canDestroyRoom) {
            canDestroyRoom = false;
            for (Fixture fixture : body.getFixtureList()) {
                body.destroyFixture(fixture);
            }
            world.destroyBody(body);
        }

    }
}
