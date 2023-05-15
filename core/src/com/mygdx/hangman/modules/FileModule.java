package com.mygdx.hangman.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.enums.Language;
import com.mygdx.hangman.data.Player;
import com.mygdx.hangman.data.Settings;

//Loads all files and player data

public class FileModule {
    private Hangman game;

    public FileModule(Hangman game){
        this.game = game;
    }

    public void loadFiles(){
        loadPlayer();
        loadSettings();
        loadStrings();
    }

    public void loadPlayer(){

        if(Gdx.files.local("player.txt").exists()) {
            String[] data = Gdx.files.internal("player.txt").readString().split("\r");
            int[] tmp = new int[data.length];
            int index = 0;

            for(int i = 0; i<data.length; i++) {
                tmp[index] = Integer.parseInt(data[i].split(":")[1].trim());
                index++;
            }
            game.player = new Player(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4]);
        }
        else {
            game.player = new Player(0, 0, 0, 0, 0);
        }
    }

    public void loadSettings(){

        if(Gdx.files.local("settings.txt").exists()) {
            String[] settings = Gdx.files.local("settings.txt").readString().split("\r");
            Settings.language = Language.valueOf(settings[0]);
            Settings.MUSIC = Boolean.valueOf(settings[1]);
            Settings.SOUND = Boolean.valueOf(settings[2]);
        }
        else{
            Settings.language = Language.ENGLISH;
            Settings.MUSIC = true;
            Settings.SOUND = true;
        }
    }

    public void loadStrings(){
        if(Settings.language.equals(Language.ENGLISH))
            Settings.chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G',
                    'H', 'I', 'J', 'K', 'L', 'M', 'N',
                    'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                    'V', 'W', 'X', 'Y', 'Z'};
        else
            Settings.chars = new char[]{'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё',
                    'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М',
                    'Н', 'О', 'П', 'Р', 'С', 'Т', 'У',
                    'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ',
                    'Ы', 'Ь', 'Э', 'Ю', 'Я'};

        FileHandle wordsFile = Gdx.files.internal("GFX\\" + Settings.language + "\\words.txt");
        Settings.words = wordsFile.readString().split(" ");
    }

}
