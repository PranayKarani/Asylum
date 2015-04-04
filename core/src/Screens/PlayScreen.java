package Screens; // 03 Apr, 12:40 PM

import Primary.GameWorld;
import Primary.WorldRenderer;
import Secondary.Player;
import com.BotXgames.Asylum.MainGameClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

public class PlayScreen extends AbstractScreen {

    boolean isPaused;
    boolean viewMap;

    GameWorld world;
    WorldRenderer renderer;

    public PlayScreen( MainGameClass gameClass ) {
        super (gameClass);
        isPaused = false;
        viewMap = false;
    }

    @Override
    public void show() {

        world = new GameWorld ();
        renderer = new WorldRenderer (world, batch);

    }

    @Override
    public void render( float delta ) {

        Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);

        // if not paused keep udating the world else view map or show pause UI
        if ( !isPaused ) {
            world.update (delta);
            renderer.render ();
        } else {

            if ( viewMap ) {
                // view map
            } else {
                pause ();
            }

        }

    }

    @Override
    public void resize( int width, int height ) {

        renderer.resize (width, height);

    }

    @Override
    public void pause() { /* called on game lossing focus or when player pauses the game*/
        world.pause ();
    }

    @Override
    public void resume() {
        world.resume ();
    }

    @Override
    public void hide() { /* called when switching to other screen */
        dispose ();
    }

    @Override
    public void dispose() { /* called when switching to other screen or exiting the game */

        world.dispose ();
        renderer.dispose ();

    }

    @Override
    public boolean keyDown( int keycode ) {
        if ( keycode == Input.Keys.A )
            world.player.leftPressed = true;
        if ( keycode == Input.Keys.D )
            world.player.rightPressed = true;
        if ( keycode == Input.Keys.X )
            Player.act = true;
        return true;
    }

    @Override
    public boolean keyUp( int keycode ) {
        if ( keycode == Input.Keys.A )
            world.player.leftPressed = false;
        if ( keycode == Input.Keys.D )
            world.player.rightPressed = false;
        if ( keycode == Input.Keys.X ) {
            Player.act = false;
        }
        return true;
    }

    @Override
    public boolean keyTyped( char character ) {
        return false;
    }

    @Override
    public boolean touchDown( int screenX, int screenY, int pointer, int button ) {
        return false;
    }

    @Override
    public boolean touchUp( int screenX, int screenY, int pointer, int button ) {
        return false;
    }

    @Override
    public boolean touchDragged( int screenX, int screenY, int pointer ) {
        return false;
    }

    @Override
    public boolean mouseMoved( int screenX, int screenY ) {
        return false;
    }

    @Override
    public boolean scrolled( int amount ) {
        return false;
    }
}
