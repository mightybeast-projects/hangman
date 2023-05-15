package com.mygdx.hangman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.hangman.data.Audio;
import com.mygdx.hangman.data.enums.Difficulty;
import com.mygdx.hangman.data.enums.Mode;
import com.mygdx.hangman.data.Player;
import com.mygdx.hangman.data.Settings;
import com.mygdx.hangman.modules.AudioModule;
import com.mygdx.hangman.modules.FileModule;
import com.mygdx.hangman.screens.MenuScreen;
import com.mygdx.hangman.modules.GraphicModule;

public class Hangman extends Game {
    public static final int WIDTH = 9;
    public static final int HEIGHT = 16;

    public SpriteBatch batch;
    public Player player;
    public FileModule fileModule;
    public GraphicModule graphicModule;
    private AudioModule audioModule;
    public Mode mode;
    public Difficulty difficulty;

	@Override
	public void create () {
	    fileModule = new FileModule(this);
        graphicModule = new GraphicModule();
        audioModule = new AudioModule();
        loadData();
	    batch = new SpriteBatch();
	    if(Settings.MUSIC)
	        Audio.mainMusic.play();

        setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	    batch.dispose();
	}

    private void loadData(){
	    fileModule.loadFiles();
        graphicModule.loadAtlases();
        audioModule.loadAudio();
    }
}
