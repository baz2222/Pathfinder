package com.baz2222.gap2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.baz2222.gap2.GapGame2;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 544;
		config.width = 960;
		config.fullscreen = false;
		config.title = "GAP-II by Vasyl Velhus";
		new LwjglApplication(new GapGame2(), config);
	}
}
