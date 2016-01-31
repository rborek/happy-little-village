package game.ritual.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.ritual.RitualGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.fullscreen = false;
		config.resizable = false;
<<<<<<< HEAD
=======
		config.useGL30 = true;
>>>>>>> cee354a86b19cca94dc28a9511f27423696004f2
		new LwjglApplication(new RitualGame(), config);
	}
}
