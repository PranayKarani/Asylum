package com.BotXgames.Asylum.desktop;

import com.BotXgames.Asylum.MainGameClass;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		boolean highRes = true;

		config.width = highRes? 1280 : 800;
		config.height = highRes? 720 : 480;
		config.useGL30 = false;

		new LwjglApplication(new MainGameClass(), config);
	}
}
