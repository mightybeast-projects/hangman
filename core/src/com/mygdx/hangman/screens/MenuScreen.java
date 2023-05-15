package com.mygdx.hangman.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.enums.Language;
import com.mygdx.hangman.data.enums.Mode;
import com.mygdx.hangman.data.Settings;
import com.mygdx.hangman.data.Audio;
import com.mygdx.hangman.screens.WordScreens.SimpleWS;
import com.mygdx.hangman.sprites.Atlases;

//Creating at the start of the game

public class MenuScreen extends BasicScreen{
    private Button onePlayer, twoPlayers, multiplayer;
    private Table table;

    public MenuScreen(Hangman game){
        super();
        this.game = game;
        viewport = new FitViewport(Hangman.WIDTH * Settings.MULTIPLIER,Hangman.HEIGHT * Settings.MULTIPLIER, cam);

        onePlayer = new Button(module.createButtonStyle(Atlases.menuScreenAtlas.findRegion("1p")));
        twoPlayers = new Button(module.createButtonStyle(Atlases.menuScreenAtlas.findRegion("2p")));
        multiplayer = new Button(module.createButtonStyle(new TextureRegion(new Texture("GFX/"+ Settings.language + "/multiplayer.png"))));

        table = new Table();
    }

    @Override
    public void show() {
        super.show();

        onePlayer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                game.mode = Mode.ONE_PLAYER;
                drawBlackScreen(new DifficultyScreen(game));
            }
        });

        twoPlayers.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                game.mode = Mode.TWO_PLAYERS;
                drawBlackScreen(new SimpleWS(game));
            }
        });

        multiplayer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                game.mode = Mode.MULTIPLAYER;
                drawBlackScreen(new MultiplayerScreen(game));
            }
        });

        table.setFillParent(true);
        table.columnDefaults(1);

        table.center();
        table.add(onePlayer).padBottom(10);
        table.row();
        table.add(twoPlayers).padBottom(10);
        table.row();
        table.add(multiplayer);

        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
