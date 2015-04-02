package com.BotXgames.Asylum.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.BotXgames.Asylum.MainGameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		boolean highRes = false;

		config.width = highRes? 1280 : 800;
		config.height = highRes? 720 : 480;
		config.useGL30 = false;

		new LwjglApplication(new MainGameClass(), config);
	}
}
