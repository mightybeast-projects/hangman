package com.mygdx.hangman.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.Audio;
import com.mygdx.hangman.data.enums.Difficulty;
import com.mygdx.hangman.data.Settings;
import com.mygdx.hangman.screens.WordScreens.OnePlayerWS;
import com.mygdx.hangman.sprites.Atlases;

public class DifficultyScreen extends BasicScreen{
    private Button easy, medium, hard, back;
    private Table table;

    public DifficultyScreen(Hangman game){
        super();
        this.game = game;
        viewport = new FitViewport(Hangman.WIDTH * Settings.MULTIPLIER,Hangman.HEIGHT * Settings.MULTIPLIER, cam);

        easy = new Button(module.createButtonStyle(Atlases.difficultyScreenAtlas.findRegion("easy")));
        medium = new Button(module.createButtonStyle(Atlases.difficultyScreenAtlas.findRegion("medium")));
        hard = new Button(module.createButtonStyle(Atlases.difficultyScreenAtlas.findRegion("hard")));
        back = new Button(module.createButtonStyle(Atlases.settingsScreenAtlas.findRegion("back")));
        table = new Table();
    }

    @Override
    public void show() {
        super.show();

        easy.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                game.difficulty = Difficulty.EASY;
                drawBlackScreen(new OnePlayerWS(game));
            }
        });

        medium.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                game.difficulty = Difficulty.MEDIUM;
                drawBlackScreen(new OnePlayerWS(game));
            }
        });

        hard.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                game.difficulty = Difficulty.HARD;
                drawBlackScreen(new OnePlayerWS(game));
            }
        });

        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                drawBlackScreen(new MenuScreen(game));
            }
        });
        back.setPosition(viewport.getWorldWidth() - 32, 0);

        table.setFillParent(true);
        table.center();
        table.add(easy).padBottom(10);
        table.row();
        table.add(medium).padBottom(10);
        table.row();
        table.add(hard);

        stage.addActor(table);
        stage.addActor(back);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
