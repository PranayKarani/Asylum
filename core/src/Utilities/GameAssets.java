package Utilities; // 03 Apr, 03:20 PM

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

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
        assetManager.setLoader (TiledMap.class, new TmxMapLoader ());
        assetManager.load ("bushes.png", Texture.class);
        assetManager.load ("tmx files/lobby.tmx", TiledMap.class);
        assetManager.load ("tmx files/courtyard.tmx", TiledMap.class);
        assetManager.load ("tmx files/Office.tmx", TiledMap.class);
        assetManager.load ("tmx files/DayRoom.tmx", TiledMap.class);
        assetManager.load ("tmx files/RCHall.tmx", TiledMap.class);
        assetManager.load ("tmx files/DrRoom.tmx", TiledMap.class);
        assetManager.load ("tmx files/Laundry.tmx", TiledMap.class);
        System.out.println ("queued other assets");

    }

}
