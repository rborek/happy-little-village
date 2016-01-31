package game.ritual.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.ritual.RitualGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.useGL30 = true;
		config.fullscreen = false;
		config.resizable = false;
		new LwjglApplication(new RitualGame(), config);
	}
}
