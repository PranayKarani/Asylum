package Secondary; // 01 Apr, 12:01 PM

import com.badlogic.gdx.physics.box2d.*;

public abstract class Room{

    protected World world;


    public Room( World world ) {
        this.world = world;


    }

    public abstract void update();

    public abstract void destroy();

}
