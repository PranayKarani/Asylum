package Primary; // 03 Apr, 04:22 PM

import Utilities.CameraManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class WorldRenderer {

    boolean debug = true;
    GameWorld world;
    SpriteBatch batch;
    CameraManager cameraManager;
    Box2DDebugRenderer b2dr;

    public WorldRenderer( GameWorld world, SpriteBatch batch ) {
        this.world = world;
        this.batch = batch;
        cameraManager = new CameraManager ();
        b2dr = new Box2DDebugRenderer ();
    }

    public void resize( int width, int height ) {
        cameraManager.resize (width, height);
    }

    public void render() {

        if(debug){
            b2dr.render (world.world, cameraManager.gameCam.combined);
        } else {

        }


        CameraManager.controlCam (cameraManager.gameCam);
    }

    public void dispose() {
        batch.dispose ();
    }
}
