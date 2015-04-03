package Utilities; // 03 Apr, 03:20 PM

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Primary purpose of this class is to provide assets to all game screens or anywhere needed.<br>
 * <b>NOTE: Its object must only be initialized in Splash Screen class</b>
 */
public class GameAssets {

    public static AssetManager assetManager;

    /**
     * initializes AssetManager object and loads the splash screen image
     */
    public GameAssets() {
        assetManager = new AssetManager ();
        assetManager.load ("splash.png", Texture.class);
        assetManager.finishLoading ();
    }

    /**
     * Adds the entire game assets to loading queue when splash screen becomes visible (in show() method). Actaully loading is done in splash screen render method
     */
    public void queue_Assets() {

        // play screen assets
        assetManager.load ("bushes.png", Texture.class);
        System.out.println ("queued other assets");

    }

}
