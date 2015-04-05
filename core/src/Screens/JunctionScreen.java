package Screens; // 05 Apr, 09:50 PM

import Secondary.Player;
import Secondary.RoomManager;
import Utilities.GameAssets;
import com.BotXgames.Asylum.MainGameClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class JunctionScreen extends AbstractScreen {

    public static final byte DLS_junction = 0; // Day room, Laundry, Sick room junction
    public static final byte SSS_junction = 1; // Sick room, Solitary , seclusion junction
    public static final byte SSE_junction = 2; // Sick room, Seclusion, EST room
    private byte junctionType;
    private RoomManager roomManager;
    Sprite sprite;

    public JunctionScreen( MainGameClass gameClass, byte junctionType, RoomManager roomManager ) {
        super (gameClass);

        this.junctionType = junctionType;
        this.roomManager = roomManager;

        switch (junctionType) {

            case DLS_junction:
                break;
            case SSS_junction:
                break;
            case SSE_junction:
                break;

        }

    }

    @Override
    public void show() {

        sprite = new Sprite (GameAssets.assetManager.get ("bushes.png", Texture.class));

        switch (junctionType) {

            case DLS_junction:
                break;
            case SSS_junction:
                break;
            case SSE_junction:
                break;

        }

    }

    @Override
    public void render( float delta ) {

        Gdx.gl.glClearColor (1, 1, 0, 1);
        Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);

        batch.begin ();
        sprite.draw (batch);
        batch.end ();

        switch (junctionType) {

            case DLS_junction:
                break;
            case SSS_junction:
                break;
            case SSE_junction:
                break;

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

    }

    @Override
    public void dispose() {
        batch.dispose ();
    }

    @Override
    public boolean keyDown( int keycode ) {
        if ( keycode == Input.Keys.X )
            Player.act = true;
        return true;
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
