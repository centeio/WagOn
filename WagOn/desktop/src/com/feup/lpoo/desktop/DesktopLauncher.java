package com.feup.lpoo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.feup.lpoo.WagOn;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = WagOn.WIDTH;
		config.height = WagOn.HEIGHT;
		new LwjglApplication(new WagOn(false), config);
	}
}
