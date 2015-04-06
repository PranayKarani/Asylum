package Screens; // 05 Apr, 09:50 PM

import Primary.GameWorld;
import Secondary.Player;
import Secondary.Room;
import Secondary.RoomManager;
import Secondary.Rooms.DayRoom;
import Secondary.Rooms.Laundry;
import Utilities.GameAssets;
import com.BotXgames.Asylum.MainGameClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static Secondary.Player.act;

public class JunctionScreen extends AbstractScreen {

    public static final byte DLS_junction = 0;
    public static final byte SSS_junction = 1;
    public static final byte SSE_junction = 2;
    private byte junctionType;

    private GameWorld world;
    private byte roomNo;
    private final byte dayroom = 1;
    private final byte laundry = 2;
    private final byte sickroom = 3;
    private final byte solitary = 4;
    private final byte seclusion = 5;
    private final byte estroom = 6;
    private Room toRoom;

    private RoomManager roomManager;
    private Player player;

    private float toRoomSpawnpoint;


    Sprite sprite;

    public JunctionScreen(MainGameClass gameClass, byte junctionType, GameWorld world) {
        super (gameClass);

        this.world = world;
        this.junctionType = junctionType;
        this.roomManager = world.roomManager;
        this.player = world.player;

        switch (junctionType) {

            case DLS_junction:
                Gdx.gl.glClearColor(1, 0, 0, 1);
                break;
            case SSS_junction:
                Gdx.gl.glClearColor(0, 1, 0, 1);
                break;
            case SSE_junction:
                Gdx.gl.glClearColor(0, 0, 1, 1);
                break;

        }

    }

    @Override
    public void show() {

        sprite = new Sprite (GameAssets.assetManager.get ("bushes.png", Texture.class));


    }

    @Override
    public void render( float delta ) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();

        switch (roomNo) {

            case laundry:
                toRoom = new Laundry(world.world, roomManager, player);
                break;
            case dayroom:
                toRoom = new DayRoom(world.world, roomManager, player);
                break;

        }

        roomNo = 0;

        if (Player.act) {

            player.getBody().setTransform(toRoomSpawnpoint, player.getBody().getPosition().y, 0);
            gameClass.setScreen(new PlayScreen(gameClass, world, player, toRoom));
            Player.act = false;
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
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose ();
    }

    @Override
    public boolean keyDown( int keycode ) {
        if (keycode == Input.Keys.X) {
            act = true;
        }
        return true;
    }

    @Override
    public boolean keyUp( int keycode ) {
        if (keycode == Input.Keys.X) {
            act = false;
        }
        return true;
    }

    @Override
    public boolean keyTyped( char character ) {
        return false;
    }

    @Override
    public boolean touchDown( int screenX, int screenY, int pointer, int button ) {
        switch (junctionType) {

            case DLS_junction:
                if (screenX > Gdx.graphics.getWidth() / 2) {
                    act = true;
                    roomNo = dayroom;
                    toRoomSpawnpoint = DayRoom.toJunction;
                }
                if (screenX < Gdx.graphics.getWidth() / 2) {
                    act = true;
                    roomNo = laundry;
                    toRoomSpawnpoint = 22.55f;//Laundry.toJunction;
                }

                break;
            case SSS_junction:
                break;
            case SSE_junction:
                break;

        }
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
