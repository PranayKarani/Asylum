package Primary; // 03 Apr, 04:22 PM

import Secondary.Room;
import Secondary.RoomManager;
import Utilities.CameraManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class WorldRenderer {

    boolean debug = true;
    GameWorld world;
    SpriteBatch batch;
    public CameraManager cameraManager;
    Box2DDebugRenderer b2dr;
    ShapeRenderer sr;
    BitmapFont font;

    public WorldRenderer( GameWorld world, SpriteBatch batch ) {
        this.world = world;
        this.batch = batch;
        cameraManager = new CameraManager ();
        b2dr = new Box2DDebugRenderer ();
        sr = new ShapeRenderer ();
        font = new BitmapFont(Gdx.files.internal("Scene2D/fonts.fnt"), Gdx.files.internal("Scene2D/fonts.png"), false);
    }

    public void resize( int width, int height ) {
        cameraManager.resize (width, height);
    }

    public void render() {

        CameraManager.smartFollow(cameraManager.gameCam, RoomManager.currentRoom.player.getBody(), true);
        CameraManager.controlCam(cameraManager.gameCam);

        world.roomManager.render(cameraManager.gameCam);

        if(debug){
            b2dr.render (world.world, cameraManager.gameCam.combined);
//            if ( RoomManager.currentRoom instanceof Courtyard ) {
//                sr.setProjectionMatrix (cameraManager.gameCam.combined);
//                sr.begin (ShapeRenderer.ShapeType.Line);
//                sr.setColor (Color.RED);
//                sr.rect (Courtyard.toRC, 1f, 0.05f, 1);
//                sr.rect (Courtyard.toDrRoom, 1f, 0.05f, 1);
//                sr.setColor (Color.GREEN);
//                sr.rect (Courtyard.toLobby - Courtyard.doorLength, 1f, (Courtyard.doorLength * 2), 1);
//                sr.rect (Courtyard.toDayRoom - Courtyard.doorLength, 1f, (Courtyard.doorLength * 2), 1);
//                sr.rect (Courtyard.toOffice - Courtyard.doorLength, 1f, (Courtyard.doorLength * 2), 1);
//                sr.end ();
//            }

//            showHUD (batch, cameraManager.hudCam);

        }

    }

    public void dispose() {
        batch.dispose ();
    }

    private void showHUD( SpriteBatch batch, OrthographicCamera camera ) {

        batch.setProjectionMatrix (camera.combined);
        batch.setShader (null);
        batch.begin ();
        font.draw (batch, "   Current room: " + RoomManager.currentRoom.getClass ().getSimpleName (), 0, Gdx.graphics.getHeight () - 2);
        font.draw (batch, "   " + Room.message, 0, Gdx.graphics.getHeight () - 19);
        batch.end ();

    }

    // getters..
    public BitmapFont getFont() {
        return font;
    }

}
