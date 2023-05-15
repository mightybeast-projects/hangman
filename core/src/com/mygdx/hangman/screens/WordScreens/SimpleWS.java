package com.mygdx.hangman.screens.WordScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.Audio;
import com.mygdx.hangman.data.enums.Language;
import com.mygdx.hangman.data.enums.Mode;
import com.mygdx.hangman.data.Settings;
import com.mygdx.hangman.screens.BasicScreen;
import com.mygdx.hangman.screens.MenuScreen;
import com.mygdx.hangman.screens.PlayScreens.SimplePS;
import com.mygdx.hangman.sprites.Atlases;

import java.util.Random;

//Creating WordScreen, hich can be used
//in two-player mode and multiplayer

public class SimpleWS extends BasicScreen {
    private String[] words = Settings.words;
    private Random rand = new Random();
    protected Table table;
    protected Button random, next, back;
    private String word = "Enter the word";
    private TextButton wordButton;

    public SimpleWS(Hangman game) {
        super();
        this.game = game;

        viewport = new FitViewport(Hangman.WIDTH * Settings.MULTIPLIER, Hangman.HEIGHT * Settings.MULTIPLIER);

        random = new Button(module.createButtonStyle(Atlases.wordScreenAtlas.findRegion("random")));
        next = new Button(module.createButtonStyle(Atlases.wordScreenAtlas.findRegion("next")));
        back = new Button(module.createButtonStyle(Atlases.settingsScreenAtlas.findRegion("back")));
        wordButton = new TextButton(Settings.language.equals(Language.ENGLISH) ? "Enter the word" : "Введите слово",
                module.createTextButtonStyle(Atlases.wordScreenAtlas.findRegion("wordpanel")));
        wordButton.getLabelCell().padTop(16);

        table = new Table();
    }

    @Override
    public void show() {
        super.show();

        wordButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                drawInputScreen();
            }
        });

        random.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND && !game.mode.equals(Mode.ONE_PLAYER))
                    Audio.buttonSound.play();
                boolean pass = false;
                while (!pass) {
                    word = words[rand.nextInt(Settings.words.length)];
                    if(word.length() < 15)
                        pass = true;
                }
                updateWordLabel();
            }
        });

        next.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Settings.SOUND && !game.mode.equals(Mode.ONE_PLAYER))
                    Audio.buttonSound.play();
                if(game.mode.equals(Mode.ONE_PLAYER) || game.mode.equals(Mode.TWO_PLAYERS))
                    drawBlackScreen(new SimplePS(game, word));
                if(game.mode.equals(Mode.MULTIPLAYER)) {
                    outputMessage((Settings.language.equals(Language.ENGLISH))?"Waiting for "+ "\n" +"another player..." :
                                                                                "Ждем оппонента...", table);
                    game.player.client.sendTCP(getStr());
                    game.player.client.setScreen(currentScreen);
                }
            }
        });

        back.setBounds(viewport.getWorldWidth() - 32, 0, 32, 32);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                closeNetwork(game.player.server, game.player.client);
                drawBlackScreen(new MenuScreen(game));
            }
        });

        table.setFillParent(true);
        table.center();
        table.add(wordButton).padBottom(10);
        table.row();
        table.add(random).padBottom(10);
        table.row();
        table.add(next);

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

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void updateWordLabel(){
        wordButton.setText(word);
    }

    public void setStr(String word) {
        this.word = word;
        updateWordLabel();
    }

    public String getStr() {
        return word;
    }

    protected static void performClick(Actor actor) {
        Array<EventListener> listeners = actor.getListeners();
        for(int i=0;i<listeners.size;i++)
        {
            if(listeners.get(i) instanceof ClickListener){
                ((ClickListener)listeners.get(i)).clicked(null, 0, 0);
            }
        }
    }

    protected void drawInputScreen(){
        Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input(String text) {
                word = text;
                if(word.length() < 15){
                    next.setDisabled(false);
                    updateWordLabel();
                } else {
                    performClick(wordButton);
                    next.setDisabled(true);
                }
            }

            @Override
            public void canceled() {
            }
        }, "Hangman", "", Settings.language.equals(Language.ENGLISH) ? "Max word size : 15" :
                "Max символов : 15");
    }
}