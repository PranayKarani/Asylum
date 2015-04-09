package Screens; // 05 Apr, 09:50 PM

import Primary.GameWorld;
import Secondary.Player;
import Secondary.Room;
import Secondary.RoomManager;
import Secondary.Rooms.*;
import com.BotXgames.Asylum.MainGameClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static Secondary.Player.act;

public class JunctionScreen extends AbstractScreen implements InputProcessor {

    // DLS junction
    public static final byte D_LSy = 1; // Day room, Laundry, Sick yard
    public static final byte L_DSy = 2;
    public static final byte Sy_LD = 3;
    // SSSE junction
    public static final byte Sy_ESrSc = 4; // Sy = Sick yard, T = turn (to next junction)
    public static final byte Sc_SySrE = 5;
    public static final byte Sr_SyScE = 6;// Sr = seclusion room, E = Electric shock room
    public static final byte E_SyScSr = 7;
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
        font.setColor(Color.DARK_GRAY);

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
            case Sy_ESrSc:
                Gdx.gl.glClearColor(.2f, 1, .3f, 1);
                break;
            case Sc_SySrE:
                Gdx.gl.glClearColor(.3f, 1, .2f, 1);
                break;
            case Sr_SyScE:
                Gdx.gl.glClearColor(.3f, 1, .3f, 1);
                break;
            case E_SyScSr:
                Gdx.gl.glClearColor(.2f, 1, .2f, 1);
                break;

        }

    }

    @Override
    public void show() {

//        if( junctionType == D_LSy || junctionType == Sy_LD )
//            sprite = new Sprite(GameAssets.assetManager.get("bushes.png", Texture.class));
//
//        if(junctionType == L_DSy)
//            sprite = new Sprite(GameAssets.assetManager.get("bushes.png", Texture.class));
//
//        if( junctionType >= 4 && junctionType <= 7)
//            sprite = new Sprite(GameAssets.assetManager.get("SSSE.png", Texture.class));

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
//        sprite.draw(batch);
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
            case Sy_ESrSc:
                font.draw(batch, "Seclusion", Gdx.graphics.getWidth() / 4, 240);
                font.draw(batch, "EST ", Gdx.graphics.getWidth() / 2, 240);
                font.draw(batch, "Solitary", Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 6, 240);
                break;
            case Sc_SySrE:
                font.draw(batch, "Sick yard", Gdx.graphics.getWidth() / 4, 240);
                font.draw(batch, "Seclusion", Gdx.graphics.getWidth() / 2, 240);
                font.draw(batch, "EST", Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 6, 240);
                break;
            case E_SyScSr:
                font.draw(batch, "Solitary", Gdx.graphics.getWidth() / 4, 240);
                font.draw(batch, "Sick yard ", Gdx.graphics.getWidth() / 2, 240);
                font.draw(batch, "Seclusion", Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 6, 240);
                break;
            case Sr_SyScE:
                font.draw(batch, "EST", Gdx.graphics.getWidth() / 4, 240);
                font.draw(batch, "Solitray", Gdx.graphics.getWidth() / 2, 240);
                font.draw(batch, "Sick yard", Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 6, 240);
                break;

        }
        batch.end();

        switch (roomNo) {

            case laundry:
                toRoom = new Laundry(world.world, roomManager, player, batch);
                break;
            case dayroom:
                toRoom = new DayRoom(world.world, roomManager, player, batch);
                break;
            case sickroom:
                toRoom = new Sickyard(world.world, roomManager, player, batch);
                break;
            case solitary:
                toRoom = new Solitary(world.world, roomManager, player, batch);
                break;
            case seclusion:
                toRoom = new Seclusion(world.world, roomManager, player, batch);
                break;
            case estroom:
                toRoom = new ElectroshockRoom(world.world, roomManager, player, batch);
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
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
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
            case Sy_ESrSc: // from sick yard
                if (screenX < Gdx.graphics.getWidth() / 3) {
                    act = true;
                    roomNo = seclusion;
                    toRoomSpawnpoint = 1.5f;
                }
                if (screenX > Gdx.graphics.getWidth() * 1f / 3f && screenX < Gdx.graphics.getWidth() * 2f / 3f) {
                    act = true;
                    roomNo = estroom;
                    toRoomSpawnpoint = 32.5f;
                }
                if (screenX > Gdx.graphics.getWidth() * 2f / 3f) {
                    act = true;
                    roomNo = solitary;
                    toRoomSpawnpoint = 40.5f;
                }
                break;
            case Sc_SySrE: // from solitary
                if (screenX < Gdx.graphics.getWidth() / 3) {
                    act = true;
                    roomNo = sickroom;
                    toRoomSpawnpoint = 1.5f;
                }
                if (screenX > Gdx.graphics.getWidth() * 1f / 3f && screenX < Gdx.graphics.getWidth() * 2f / 3f) {
                    act = true;
                    roomNo = seclusion;
                    toRoomSpawnpoint = 1.5f;
                }
                if (screenX > Gdx.graphics.getWidth() * 2f / 3f) {
                    act = true;
                    roomNo = estroom;
                    toRoomSpawnpoint = 32.5f;
                }
                break;
            case E_SyScSr: // from EST
                if (screenX < Gdx.graphics.getWidth() / 3) {
                    act = true;
                    roomNo = solitary;
                    toRoomSpawnpoint = 40.5f;
                }
                if (screenX > Gdx.graphics.getWidth() * 1f / 3f && screenX < Gdx.graphics.getWidth() * 2f / 3f) {
                    act = true;
                    roomNo = sickroom;
                    toRoomSpawnpoint = 1.5f;
                }
                if (screenX > Gdx.graphics.getWidth() * 2f / 3f) {
                    act = true;
                    roomNo = seclusion;
                    toRoomSpawnpoint = 1.5f;
                }
                break;
            case Sr_SyScE: // from Seclusion
                if (screenX < Gdx.graphics.getWidth() / 3) {
                    act = true;
                    roomNo = estroom;
                    toRoomSpawnpoint = 32.5f;
                }
                if (screenX > Gdx.graphics.getWidth() * 1f / 3f && screenX < Gdx.graphics.getWidth() * 2f / 3f) {
                    act = true;
                    roomNo = solitary;
                    toRoomSpawnpoint = 40.5f;
                }
                if (screenX > Gdx.graphics.getWidth() * 2f / 3f) {
                    act = true;
                    roomNo = sickroom;
                    toRoomSpawnpoint = 1.5f;
                }
                break;


        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        act = false;
        return true;
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
