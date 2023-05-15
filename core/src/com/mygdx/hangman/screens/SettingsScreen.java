package com.mygdx.hangman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.enums.Language;
import com.mygdx.hangman.data.Settings;
import com.mygdx.hangman.data.Audio;
import com.mygdx.hangman.screens.PlayScreens.SimplePS;
import com.mygdx.hangman.screens.WordScreens.OnePlayerWS;
import com.mygdx.hangman.screens.WordScreens.SimpleWS;
import com.mygdx.hangman.sprites.Atlases;

public class SettingsScreen extends BasicScreen{
    private Button back, sound, music, englishFlag, russianFlag;
    private Table table;
    private BasicScreen prevScreen, goTo;
    private boolean langChanged = false;

    protected SettingsScreen(Hangman game, BasicScreen prevScreen) {
        super();
        viewport = new FitViewport(Hangman.WIDTH * Settings.MULTIPLIER, Hangman.HEIGHT * Settings.MULTIPLIER, cam);
        this.game = game;
        this.prevScreen = prevScreen;

        back = new Button(module.createButtonStyle(Atlases.settingsScreenAtlas.findRegion("back")));

        if(Settings.SOUND)
            sound = new Button(module.createButtonStyle(Atlases.settingsScreenAtlas.findRegion("sound")));
        else
            sound = new Button(module.createButtonStyle(Atlases.settingsScreenAtlas.findRegion("soundx")));
        if(Settings.MUSIC)
            music = new Button(module.createButtonStyle(Atlases.settingsScreenAtlas.findRegion("music")));
        else
            music = new Button(module.createButtonStyle(Atlases.settingsScreenAtlas.findRegion("musicx")));

        if(Settings.language.equals(Language.ENGLISH)){
            englishFlag = new Button(module.createFlagBS(Atlases.settingsScreenAtlas.findRegion("englishflag")));
            englishFlag.setDisabled(true);
            russianFlag = new Button(module.createFlagBS(Atlases.settingsScreenAtlas.findRegion("russianflagx")));
        }
        else{
            englishFlag = new Button(module.createFlagBS(Atlases.settingsScreenAtlas.findRegion("englishflagx")));
            russianFlag = new Button(module.createFlagBS(Atlases.settingsScreenAtlas.findRegion("russianflag")));
            russianFlag.setDisabled(true);
        }

        table = new Table();
    }

    @Override
    public void show() {
        super.show();

        back.setBounds(stage.getViewport().getWorldWidth() - 32, 0, 32, 32);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                if(langChanged) {
                    game.graphicModule.loadAtlases();
                    game.fileModule.loadStrings();
                }
                goTo = checkPrevScreen(prevScreen);
                drawBlackScreen(goTo);
                settings.setTouchable(Touchable.enabled);
            }
        });

        music.setSize(32, 32);
        music.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.MUSIC){
                    if(Settings.SOUND)
                        Audio.buttonSound.play();
                    module.updateButtonStyle(music, Atlases.settingsScreenAtlas.findRegion("musicx"));
                    Audio.mainMusic.stop();
                    Settings.MUSIC = false;
                }
                else {
                    if(Settings.SOUND)
                        Audio.buttonSound.play();
                    module.updateButtonStyle(music, Atlases.settingsScreenAtlas.findRegion("music"));
                    Audio.mainMusic.play();
                    Settings.MUSIC = true;
                }
            }
        });

        sound.setSize(32, 32);
        sound.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND) {
                    Audio.buttonSound.play();
                    module.updateButtonStyle(sound, Atlases.settingsScreenAtlas.findRegion("soundx"));
                    Settings.SOUND = false;
                }
                else {
                    module.updateButtonStyle(sound, Atlases.settingsScreenAtlas.findRegion("sound"));
                    Settings.SOUND = true;
                }
            }
        });

        englishFlag.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                Settings.language = Language.ENGLISH;
                module.updateFlagBS(englishFlag, Atlases.settingsScreenAtlas.findRegion("englishflag"));
                module.updateFlagBS(russianFlag, Atlases.settingsScreenAtlas.findRegion("russianflagx"));
                russianFlag.setDisabled(false);
                englishFlag.setDisabled(true);
                langChanged = true;
            }
        });

        russianFlag.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                Settings.language = Language.RUSSIAN;
                module.updateFlagBS(russianFlag, Atlases.settingsScreenAtlas.findRegion("russianflag"));
                module.updateFlagBS(englishFlag, Atlases.settingsScreenAtlas.findRegion("englishflagx"));
                russianFlag.setDisabled(true);
                englishFlag.setDisabled(false);
                langChanged = true;
            }
        });

        settings.setTouchable(Touchable.disabled);

        table.setFillParent(true);
        table.center();
        table.add(music).padRight(10);
        table.add(sound);
        table.row().padTop(10);
        table.add(englishFlag).padRight(10);
        table.add(russianFlag);

        stage.addActor(back);
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
        saveSettings();
    }

    public BasicScreen checkPrevScreen(BasicScreen bs){
        BasicScreen out = null;
        if(!langChanged) {
            if (bs instanceof MenuScreen || bs instanceof SimplePS) {
                out = new MenuScreen(game);
            } else if (bs instanceof DifficultyScreen){
                out = new DifficultyScreen(game);
            } else if (bs instanceof ShopScreen) {
                out = new ShopScreen(game);
            } else if (bs instanceof SimpleWS) {
                out = new SimpleWS(game);
                ((SimpleWS) out).setStr(((SimpleWS) bs).getStr());
            } else if(bs instanceof MultiplayerScreen){
                out = new MenuScreen(game);
            } else
                out = new OnePlayerWS(game);
        } else{
            if (bs instanceof MenuScreen || bs instanceof SimplePS || bs instanceof SimpleWS) {
                out = new MenuScreen(game);
            } else if (bs instanceof DifficultyScreen){
                out = new DifficultyScreen(game);
            } else if (bs instanceof ShopScreen){
                out = new ShopScreen(game);
            } else if(bs instanceof MultiplayerScreen){
                out = new MenuScreen(game);
            }
        }
        return out;
    }

    private void saveSettings(){
        Gdx.files.local("settings.txt").
                writeString(String.valueOf(Settings.language) + "\r" +
                        String.valueOf(Settings.MUSIC) + "\r" +
                        String.valueOf(Settings.SOUND), false);
    }
}
