package com.mygdx.hangman.screens.WordScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.enums.Difficulty;

//Creating in one player mode in one-player mode

public class OnePlayerWS extends SimpleWS {

    public OnePlayerWS(Hangman game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        boolean pass = false;
        while (!pass) {
            performClick(random);
            pass = checkWord(getStr());
        }
        performClick(next);
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private boolean checkWord(String word){
        if(game.difficulty.equals(Difficulty.EASY) && word.length() > 1 && word.length() < 5)
            return true;
        else if(game.difficulty.equals(Difficulty.MEDIUM) && word.length() > 5 && word.length() < 10)
            return true;
        else if(game.difficulty.equals(Difficulty.HARD) && word.length() > 10 && word.length() < 15)
            return true;
        else
            return false;
    }

}
