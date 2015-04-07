package Screens; // 05 Apr, 09:50 PM

import Primary.GameWorld;
import Secondary.Player;
import Secondary.Room;
import Secondary.RoomManager;
import Secondary.Rooms.*;
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
    // SSSE junction
    public static final byte Sy_ScSrE = 4; // Sy = Sick yard, T = turn (to next junction)
    public static final byte Sc_SrESy = 5;
    public static final byte Sr_EScSy = 6;// Sr = seclusion room, E = Electric shock room
    public static final byte E_SrScSy = 7;
    private byte junctionType;

    private byte roomNo;
    private final byte dayroom = 1;
    private final byte laundry = 2;
    private final byte sickroom = 3;
    private final byte solitary = 4;
    private final byte seclusion = 5;
    private final byte estroom = 6;
    private Room toRoom;
    private byte selecJunction;
    private JunctionScreen nextJunctionScreen;

    private GameWorld world;
    private RoomManager roomManager;
    private Player player;

    private float toRoomSpawnpoint;

    BitmapFont font;
    Sprite sprite;

    public JunctionScreen(MainGameClass gameClass, byte junctionType, GameWorld world) {
        super(gameClass);

        this.world = world;
        this.junctionType = junctionType;
        this.roomManager = world.roomManager;
        this.player = world.player;

        font = new BitmapFont();

        switch (junctionType) {

            // DLS
            case D_LSy:
                Gdx.gl.glClearColor(1, .2f, .3f, 1);
                break;
            case L_DSy:
                Gdx.gl.glClearColor(1, .3f, .2f, 1);
                break;
            case Sy_LD:
                Gdx.gl.glClearColor(1, 0, 0, 1);
                break;

            // SSSE
            case Sy_ScSrE:
                Gdx.gl.glClearColor(.2f, 1, .3f, 1);
                break;
            case Sc_SrESy:
                Gdx.gl.glClearColor(.3f, 1, .2f, 1);
                break;
            case Sr_EScSy:
                Gdx.gl.glClearColor(.3f, 1, .3f, 1);
                break;
            case E_SrScSy:
                Gdx.gl.glClearColor(.2f, 1, .2f, 1);
                break;

        }

    }

    @Override
    public void show() {

        sprite = new Sprite(GameAssets.assetManager.get("bushes.png", Texture.class));

    }

    @Override
    public void render(float delta) {

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
            case Sy_LD:
                font.draw(batch, "tap here to go to Laundry ", 200, 240);
                font.draw(batch, "tap here to go to Day room", 600, 240);
                break;

            // SSSE
            case Sy_ScSrE:
                font.draw(batch, "EST ", 200, 240);
                font.draw(batch, "Seclusion", 400, 240);
                font.draw(batch, "Solitary", 600, 240);
                break;
            case Sc_SrESy:
                font.draw(batch, "Sick yard ", 200, 240);
                font.draw(batch, "Seclusion", 400, 240);
                font.draw(batch, "EST", 600, 240);
                break;
            case Sr_EScSy:
                font.draw(batch, "Sick yard ", 0, 240);
                font.draw(batch, "Solitary", 200, 240);
                font.draw(batch, "EST", 400, 240);
                break;
            case E_SrScSy:
                font.draw(batch, "Sick yard ", 0, 240);
                font.draw(batch, "Solitary", 200, 240);
                font.draw(batch, "Seclusion", 400, 240);
                break;

        }

        batch.end();

        switch (roomNo) {

            case laundry:
                toRoom = new Laundry(world.world, roomManager, player);
                break;
            case dayroom:
                toRoom = new DayRoom(world.world, roomManager, player);
                break;
            case sickroom:
                toRoom = new SickYard(world.world, roomManager, player);
                break;
            case solitary:
                toRoom = new Solitary(world.world, roomManager, player);
                break;
            case seclusion:
                toRoom = new Seclusion(world.world, roomManager, player);
                break;
            case estroom:
                toRoom = new ElectroshockRoom(world.world, roomManager, player);
                break;

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
    public void resize(int width, int height) {

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
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.X) {
            act = true;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.X) {
            act = false;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (junctionType) {

            case D_LSy:
                if (screenX < Gdx.graphics.getWidth() / 2) { // touched left part of screen
                    act = true;
                    roomNo = sickroom;
                    toRoomSpawnpoint = 60.5f;
                }
                if (screenX > Gdx.graphics.getWidth() / 2) { // touched right part of screen
                    act = true;
                    roomNo = laundry;
                    toRoomSpawnpoint = 22.55f;
                }
                break;
            case L_DSy:
                if (screenX > Gdx.graphics.getWidth() / 2) { // touched right part of screen
                    act = true;
                    roomNo = sickroom;
                    toRoomSpawnpoint = 60.5f;
                }
                if (screenX < Gdx.graphics.getWidth() / 2) { // touched left part of screen
                    act = true;
                    roomNo = dayroom;
                    toRoomSpawnpoint = 1.5f;
                }
                break;
            case Sy_LD:
                if (screenX < Gdx.graphics.getWidth() / 2) { // touched left part of screen
                    act = true;
                    roomNo = laundry;
                    toRoomSpawnpoint = 22.55f;
                }
                if (screenX > Gdx.graphics.getWidth() / 2) { // touched right part of screen
                    act = true;
                    roomNo = dayroom;
                    toRoomSpawnpoint = 1.5f;
                }
                break;

            // SSSE
            case Sy_ScSrE:
                if (screenX < Gdx.graphics.getWidth() / 2) {
                    act = true;
                    roomNo = estroom;
                    toRoomSpawnpoint = 32.5f;
                }
                if (screenX > Gdx.graphics.getWidth() / 2 && screenX < Gdx.graphics.getWidth() / 1.33f) {
                    act = true;
                    roomNo = seclusion;
                    toRoomSpawnpoint = Seclusion.toSEJunction;
                }
                if (screenX > Gdx.graphics.getWidth() / 1.33f) {
                    act = true;
                    roomNo = solitary;
                    toRoomSpawnpoint = 40.5f;
                }
                break;
            case Sc_SrESy:
                if (screenX < Gdx.graphics.getWidth() / 2) { // touched left part of screen
                    act = true;
                    roomNo = sickroom;
                    toRoomSpawnpoint = 28.5f;
                }
                if (screenX > Gdx.graphics.getWidth() / 2) { // touched right part of screen
                    act = true;
                }
                break;
            case Sr_EScSy:
                break;
            case E_SrScSy:
                break;


        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
