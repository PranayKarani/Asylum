package Screens; // 02 Apr, 08:47 PM

import Utilities.GameAssets;
import com.BotXgames.Asylum.MainGameClass;
import com.badlogic.gdx.graphics.Texture;

/**
 * Loads the assets of entire game behind the splash screen image. When done, switches to next screen.
 */
public class SplashScreen extends AbstractScreen {

    Texture texture;
    GameAssets gameAssets;

    public SplashScreen( MainGameClass gameClass ) {
        super (gameClass);
        gameAssets = new GameAssets (); // splash screen assets are loaded here
        texture = GameAssets.assetManager.get ("splash.png", Texture.class);
    }

    @Override
    public void show() {
        gameAssets.queue_Assets ();
    }

    @Override
    public void render( float delta ) {

        if ( GameAssets.assetManager.update () ) {
            gameClass.setScreen (new PlayScreen (gameClass));
        } else {

            batch.begin ();
            batch.draw (texture, 0, 0);
            batch.end ();

        }

    }

    @Override
    public void resize( int width, int height ) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose ();
    }

    @Override
    public void dispose() {
        batch.dispose ();
        texture.dispose ();
    }

    @Override
    public boolean keyDown( int keycode ) {
        return false;
    }

    @Override
    public boolean keyUp( int keycode ) {
        return false;
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
