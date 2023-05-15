package com.mygdx.hangman.data;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.hangman.data.enums.Language;

public class Settings {
    public static boolean MUSIC = true;
    public static boolean SOUND = true;
    public static int MULTIPLIER = 20;
    public static Language language;

    public static BitmapFont font;
    public static char[] chars;
    public static String[] words;
}
