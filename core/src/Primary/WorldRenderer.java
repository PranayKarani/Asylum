package Primary; // 03 Apr, 04:22 PM

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {

    GameWorld world;
    SpriteBatch batch;

    public WorldRenderer( GameWorld world, SpriteBatch batch ) {
        this.world = world;
        this.batch = batch;
    }

    public void resize( int width, int height ) {

    }

    public void render() {

    }

    public void dispose() {
        batch.dispose ();
    }
}
