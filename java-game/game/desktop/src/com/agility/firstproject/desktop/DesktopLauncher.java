package com.agility.firstproject.desktop;

import com.agility.firstproject.AnimationDemo;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new firstproject(), config);
		new LwjglApplication(new AnimationDemo(), config);

	}
}
