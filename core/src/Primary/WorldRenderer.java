package Primary; // 03 Apr, 04:22 PM

import Secondary.Rooms.Lobby;
import Utilities.CameraManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class WorldRenderer {

    boolean debug = true;
    GameWorld world;
    SpriteBatch batch;
    CameraManager cameraManager;
    Box2DDebugRenderer b2dr;
    ShapeRenderer sr;

    public WorldRenderer( GameWorld world, SpriteBatch batch ) {
        this.world = world;
        this.batch = batch;
        cameraManager = new CameraManager ();
        b2dr = new Box2DDebugRenderer ();
        sr = new ShapeRenderer ();
    }

    public void resize( int width, int height ) {
        cameraManager.resize (width, height);
    }

    public void render() {

        if(debug){
            b2dr.render (world.world, cameraManager.gameCam.combined);
            sr.setProjectionMatrix (cameraManager.gameCam.combined);
            sr.begin (ShapeRenderer.ShapeType.Line);
            sr.setColor (Color.RED);
            sr.rect (Lobby.toCourtyard, 1f, 0.05f, 1);
            sr.setColor (Color.GREEN);
            sr.rect (Lobby.toGarden, 1f, 0.05f, 1);
            sr.end ();

        } else {

        }

        CameraManager.smartFollow (cameraManager.gameCam, world.player.getBody (), true);
        CameraManager.controlCam (cameraManager.gameCam);
    }

    public void dispose() {
        batch.dispose ();
    }
}
