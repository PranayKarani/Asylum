package Screens; // 03 Apr, 12:40 PM

import com.BotXgames.Asylum.MainGameClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class PlayScreen extends AbstractScreen {

    public PlayScreen( MainGameClass gameClass ) {
        super (gameClass);

    }

    @Override
    public void show() {

    }

    @Override
    public void render( float delta ) {

        Gdx.gl.glClearColor (0, 0, 0, 1);
        Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);

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

    }

    @Override
    public void dispose() {

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
