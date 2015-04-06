package Screens; // 03 Apr, 12:40 PM

import Primary.GameWorld;
import Primary.WorldRenderer;
import Secondary.Player;
import Secondary.Room;
import com.BotXgames.Asylum.MainGameClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

public class PlayScreen extends AbstractScreen {

    public static boolean isPaused;
    public static boolean viewMap;
    public static boolean enterJuction;
    public static byte selectedJunction;

    GameWorld world;
    WorldRenderer renderer;

    public PlayScreen( MainGameClass gameClass ) {
        super (gameClass);
        isPaused = false;
        viewMap = false;
        enterJuction = false;

        world = new GameWorld();
        renderer = new WorldRenderer(world, batch);
    }

    public PlayScreen(MainGameClass gameClass, GameWorld world, Player player, Room gotoroom) {
        super(gameClass);
        isPaused = false;
        viewMap = false;
        enterJuction = false;

        this.world = new GameWorld(world, player, gotoroom);
        renderer = new WorldRenderer(this.world, batch);

    }

    @Override
    public void show() {



    }

    @Override
    public void render( float delta ) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
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

        if (enterJuction) {

            gameClass.setScreen(new JunctionScreen(gameClass, selectedJunction, world));

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
        Gdx.input.setInputProcessor(this);
        world.resume ();
    }

    @Override
    public void hide() { /* called when switching to other screen */
//        dispose ();
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
        if ( keycode == Input.Keys.X )
            Player.act = false;

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
