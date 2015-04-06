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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static Secondary.Player.act;

public class JunctionScreen extends AbstractScreen {

    // DLS junction
    public static final byte D_LSy = 1; // Day room, Laundry, Sick yard
    public static final byte L_DSy = 2;
    public static final byte Sy_LD = 3;
    // SSS junction
    public static final byte Sy_ST = 4; // Sy = Sick yard, T = turn (to next junction)
    public static final byte S_SyT = 5;
    public static final byte T_SyS = 6;
    // SSE junction
    public static final byte T_SrE = 7; // Sr = seclusion room, E = Electric shock room
    public static final byte Sr_TE = 8;
    public static final byte E_SrT = 9;
    private byte junctionType;

    private byte roomNo;
    private final byte dayroom = 1;
    private final byte laundry = 2;
    private final byte sickroom = 3;
    private final byte solitary = 4;
    private final byte seclusion = 5;
    private final byte estroom = 6;
    private Room toRoom;

    private GameWorld world;
    private RoomManager roomManager;
    private Player player;

    private float toRoomSpawnpoint;

    BitmapFont font;
    Sprite sprite;

    public JunctionScreen(MainGameClass gameClass, byte junctionType, GameWorld world) {
        super (gameClass);

        this.world = world;
        this.junctionType = junctionType;
        this.roomManager = world.roomManager;
        this.player = world.player;

        font = new BitmapFont();

        switch (junctionType) {

            // DLS
            case D_LSy: Gdx.gl.glClearColor(1, .2f, .3f, 1);break;
            case L_DSy: Gdx.gl.glClearColor(1, .3f, .2f, 1);break;
            case Sy_LD: Gdx.gl.glClearColor(1, 0, 0, 1);break;
            // SSS
            case Sy_ST: Gdx.gl.glClearColor(.2f, 1, .3f, 1);break;
            case S_SyT: Gdx.gl.glClearColor(.3f, 1, .2f, 1);break;
            case T_SyS: Gdx.gl.glClearColor(0, 1, 0, 1);break;
            //SSE
            case T_SrE: Gdx.gl.glClearColor(.2f, .3f, 1, 1);break;
            case Sr_TE: Gdx.gl.glClearColor(.3f, .2f, 1, 1);break;
            case E_SrT: Gdx.gl.glClearColor(0, 0, 1, 1);break;

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

        switch (junctionType) {

            case D_LSy:
                font.draw(batch, "tap here to go to Sick yard", 200, 240);
                font.draw(batch, "tap here to go to Laundry", 600, 240);
                break;
            case L_DSy:
                font.draw(batch, "tap here to go to Day room", 200, 240);
                font.draw(batch, "tap here to go to Sick yard", 600, 240);
                break;
            case Sy_LD: break;
            // SSS
            case Sy_ST: break;
            case S_SyT: break;
            case T_SyS: break;
            //SSE
            case T_SrE: break;
            case Sr_TE: break;
            case E_SrT: break;

        }

        batch.end();

        switch (roomNo) {

            case laundry:toRoom = new Laundry(world.world, roomManager, player);break;
            case dayroom:toRoom = new DayRoom(world.world, roomManager, player);break;
            case sickroom: break;
            case solitary: break;
            case seclusion: break;
            case estroom: break;

        }

        roomNo = 0;

        if (Player.act) {

            player.getBody().setTransform(toRoomSpawnpoint, player.getBody().getPosition().y, 0);
            gameClass.setScreen(new PlayScreen(gameClass, world, player, toRoom));
            player.getBody().setLinearVelocity(0, 0);
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
        font.dispose();
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

            case D_LSy:
                if (screenX < Gdx.graphics.getWidth() / 2) { // touched left part of screen
                    act = true;
                    System.out.println("Sick room not available");
                }
                if (screenX > Gdx.graphics.getWidth() / 2) { // touched right part of screen
                    act = true;
                    roomNo = laundry;
                    toRoomSpawnpoint = 22.55f;//Laundry.toJunction;
                }
                break;
            case L_DSy:
                if (screenX > Gdx.graphics.getWidth() / 2) { // touched right part of screen
                    act = true;
                    System.out.println("Sick room not available");
                }
                if (screenX < Gdx.graphics.getWidth() / 2) { // touched left part of screen
                    act = true;
                    roomNo = dayroom;
                    toRoomSpawnpoint = DayRoom.toJunction;//Laundry.toJunction;
                }
                break;
            case Sy_LD:
                if (screenX < Gdx.graphics.getWidth() / 2) { // touched left part of screen
                    act = true;
                    roomNo = laundry;
                    toRoomSpawnpoint = 22.55f;//Laundry.toJunction;
                }
                if (screenX > Gdx.graphics.getWidth() / 2) { // touched right part of screen
                    act = true;
                    roomNo = dayroom;
                    toRoomSpawnpoint = DayRoom.toJunction;//Laundry.toJunction;
                }break;
            // SSS
            case Sy_ST: break;
            case S_SyT: break;
            case T_SyS: break;
            //SSE
            case T_SrE: break;
            case Sr_TE: break;
            case E_SrT: break;


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
