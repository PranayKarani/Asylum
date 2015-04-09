package Screens; // 03 Apr, 12:40 PM

import Primary.GameWorld;
import Primary.WorldRenderer;
import Secondary.Player;
import Secondary.Room;
import Secondary.RoomManager;
import com.BotXgames.Asylum.MainGameClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayScreen extends AbstractScreen implements InputProcessor {

    public static boolean isPaused;
    public static boolean viewMap;
    public static boolean enterJuction;
    public static byte selectedJunction;

    GameWorld world;
    WorldRenderer renderer;

    // Scene2D
    Stage stage;
    Label label;

    public PlayScreen(MainGameClass gameClass) {
        super(gameClass);
        isPaused = false;
        viewMap = false;
        enterJuction = false;

        world = new GameWorld(batch);
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

        Skin skin = new Skin(Gdx.files.internal("Scene2D/skin.json"));
        stage = new Stage(new ScreenViewport(), batch);

        Pixmap pixmap = new Pixmap(10, 10, Format.RGBA4444);
        pixmap.setColor(0, 0.5f, 0.7f, 0.5f);
        pixmap.fill();
        skin.add("pixmap", new Texture(pixmap));

        // labels
        label = new Label("Play Screen", skin, "mylabelstyle");
        label.setPosition((Gdx.graphics.getWidth() * 0.5f / 4), Gdx.graphics.getHeight() - label.getHeight());
        // buttons
        TextButton leftMovebutton = new TextButton("left", skin, "buttonStyle");
        TextButton rightMovebutton = new TextButton("right", skin, "buttonStyle");
        TextButton actbutton = new TextButton("act", skin, "buttonStyle");
        leftMovebutton.setPosition(0, 0);
        leftMovebutton.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
        leftMovebutton.setBackground(skin.getDrawable("pixmap"));
        leftMovebutton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                world.player.leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                world.player.leftPressed = false;
            }
        });
        rightMovebutton.setPosition(Gdx.graphics.getWidth() / 4, 0);
        rightMovebutton.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
        rightMovebutton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                world.player.rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                world.player.rightPressed = false;
            }
        });

        actbutton.setPosition((Gdx.graphics.getWidth() * 3) / 4, 0);
        actbutton.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
        actbutton.setBackground(skin.getDrawable("pixmap"));
        actbutton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Player.act = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Player.act = false;
            }
        });

        stage.addActor(leftMovebutton);
        stage.addActor(rightMovebutton);
        stage.addActor(actbutton);
        stage.addActor(label);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // if not paused keep udating the world else view map or show pause UI
        if (!isPaused) {
            world.update(delta);
            label.setText(RoomManager.currentRoom.getClass().getSimpleName() + "      " + Room.message);
            stage.act();
            stage.draw();
        } else {

            if (viewMap) {
                // view map
            } else {
                label.setText("paused");
                stage.act();
                stage.draw();
            }

        }

        renderer.render(); // don't pause the rendering when game is paused

        if (enterJuction) {

            gameClass.setScreen(new JunctionScreen(gameClass, selectedJunction, world));

        }

    }

    @Override
    public void resize(int width, int height) {

        renderer.resize(width, height);

    }

    @Override
    public void pause() { /* called on game lossing focus or when player pauses the game*/
        world.pause();
    }

    @Override
    public void resume() {
        world.resume();
    }

    @Override
    public void hide() { /* called when switching to other screen */
//        dispose ();
    }

    @Override
    public void dispose() { /* called when switching to other screen or exiting the game */

        world.dispose();
        renderer.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.A)
            world.player.leftPressed = true;
        if (keycode == Keys.D)
            world.player.rightPressed = true;
        if (keycode == Keys.X) {
            Player.act = true;
            world.player.rightPressed = false;
            world.player.leftPressed = false;
        }
        if (keycode == Keys.P)
            isPaused = !isPaused;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.A)
            world.player.leftPressed = false;
        if (keycode == Keys.D)
            world.player.rightPressed = false;
        if (keycode == Keys.X)
            Player.act = false;

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
