package dev.dankins.javamon;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import dev.dankins.javamon.display.Display;

public class DesktopLauncher {
	public static void main(final String[] arg) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Javamon";
		config.width = 240 * 4;
		config.height = 160 * 4;
		config.resizable = false;
		new LwjglApplication(new Display(config.width, config.height), config);
	}
}
