package com.mygdx.hangman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Hangman.WIDTH * Settings.MULTIPLIER * 2;
        config.height = Hangman.HEIGHT * Settings.MULTIPLIER * 2;
		new LwjglApplication(new Hangman(), config);
	}
}
