package Screens; // 02 Apr, 08:31 PM

import com.BotXgames.Asylum.MainGameClass;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * <li> Abstract screen that implements <b><u>Screen</u></b> and <s>InputProcessor</s> so that it's children can implement their methods.<br>
 * <li> Declares and initializes common <b><u>SpriteBatch</u></b> to be shared by its children.<br>
 * <li> Needs a <b><u>MainGameClass</u></b> object in it's constructor.
 * <li> Needs a <b><u>MainGameClass</u></b> object in it's constructor.
 */
public abstract class AbstractScreen implements Screen {

    public MainGameClass gameClass;
    protected SpriteBatch batch;

    public AbstractScreen( MainGameClass gameClass ) {
        this.gameClass = gameClass;
        batch = new SpriteBatch ();

    }

}
