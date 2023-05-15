package com.mygdx.hangman.modules;

import com.badlogic.gdx.Gdx;
import com.mygdx.hangman.data.Audio;

//Loads all audio files

public class AudioModule {

    public AudioModule() {}

    public void loadAudio(){
        Audio.buttonSound = Gdx.audio.newSound(Gdx.files.internal("SFX\\buttonsound.ogg"));
        Audio.exitSound = Gdx.audio.newSound(Gdx.files.internal("SFX\\exitsound.ogg"));
        Audio.mainMusic = Gdx.audio.newMusic(Gdx.files.internal("SFX\\bgmusic.mp3"));
        Audio.mainMusic.setVolume(0.8f);
        Audio.mainMusic.setLooping(true);
    }

}
