package bonsai.dev.ggj2017.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import bonsai.dev.ggj2017.WavesGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.forceExit = false;
		config.width = 1280;
		config.height = 720;

		config.fullscreen = false;

		new LwjglApplication(new WavesGame(), config);
	}
}
