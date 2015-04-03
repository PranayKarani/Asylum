package Utilities; // 03 Apr, 07:00 PM

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * CameraManager maintains cameras and viewports. Everything related to them is done here
 */
public class CameraManager {

    public OrthographicCamera gameCam;
    Viewport viewport;

    public CameraManager() {
        gameCam = new OrthographicCamera ();
        gameCam.setToOrtho (false, Gdx.graphics.getWidth () / 100, Gdx.graphics.getHeight () / 100);
        gameCam.update ();
    }

    public static void controlCam( OrthographicCamera camera ) {
        if ( Gdx.input.isKeyPressed (Input.Keys.RIGHT) ) camera.position.x += 5;
        if ( Gdx.input.isKeyPressed (Input.Keys.LEFT) ) camera.position.x -= 5;
        if ( Gdx.input.isKeyPressed (Input.Keys.UP) ) camera.position.y += 5;
        if ( Gdx.input.isKeyPressed (Input.Keys.DOWN) ) camera.position.y -= 5;
        if ( Gdx.input.isKeyPressed (Input.Keys.E) ) camera.zoom += 0.5f;
        if ( Gdx.input.isKeyPressed (Input.Keys.Q) ) camera.zoom -= 0.5f;
        if ( Gdx.input.isKeyPressed (Input.Keys.R) ) camera.zoom = 1;
        camera.update ();
    }

    public static void smartFollow( OrthographicCamera camera, Body body, boolean isFacingRight ) {

        camera.position.x += (body.getPosition ().x - camera.position.x) * 0.1f;
        camera.position.y += ((body.getPosition ().y - camera.position.y) * 0.1f) + (camera.viewportHeight / 80);

    }

    public void resize( int width, int height ) {
        viewport = new FillViewport (1280 / 100, 720 / 100, gameCam);
        viewport.apply ();
        viewport.update (width, height);
    }

}