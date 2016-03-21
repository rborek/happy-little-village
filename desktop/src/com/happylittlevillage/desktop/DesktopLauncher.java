package com.happylittlevillage.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.happylittlevillage.HappyLittleVillage;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.fullscreen = false;
		config.foregroundFPS = 60;
		config.backgroundFPS = 0;
		config.vSyncEnabled = true;
		config.title = "Happy Little Village";
		config.resizable = true;
		config.useGL30 = false;
		new LwjglApplication(new HappyLittleVillage(), config);
	}
}
