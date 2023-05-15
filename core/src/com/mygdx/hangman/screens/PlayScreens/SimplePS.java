package com.mygdx.hangman.screens.PlayScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.Audio;
import com.mygdx.hangman.data.enums.Language;
import com.mygdx.hangman.data.enums.Mode;
import com.mygdx.hangman.data.Settings;
import com.mygdx.hangman.screens.BasicScreen;
import com.mygdx.hangman.screens.DifficultyScreen;
import com.mygdx.hangman.screens.WordScreens.OnePlayerWS;
import com.mygdx.hangman.screens.WordScreens.SimpleWS;
import com.mygdx.hangman.sprites.Atlases;
import com.mygdx.hangman.sprites.Letter;

import java.util.ArrayList;

/*
Creating in any of game modes but
not in time attack and
double trouble modes
*/

public class SimplePS extends BasicScreen {
    private String word;
    private int life = 10;
    private char[] chars, letters = Settings.chars;
    private boolean won;
    private ArrayList<Letter> lettersList;

    private Image wordPanel, hangman;
    private Label wordLabel;
    private Group wordGroup;
    private Table lettersTable, endTable, mainTable;
    private TextureRegion[] hangmansArray = new TextureRegion[11];
    private Button tryAgain, back;

    public SimplePS(Hangman game, String word) {
        this.game = game;
        this.word = word;
        viewport = new FitViewport(Hangman.WIDTH * Settings.MULTIPLIER, Hangman.HEIGHT * Settings.MULTIPLIER, cam);

        wordGroup = new Group();
        wordPanel = new Image(Atlases.wordScreenAtlas.findRegion("wordpanel"));
        wordLabel = new Label(word , new Label.LabelStyle(Settings.font, Color.BLACK));
        convertWord(String.valueOf(wordLabel.getText()).toCharArray());
        wordLabel.setSize(viewport.getWorldWidth(),14);
        wordLabel.setAlignment(Align.center);
        wordGroup.addActor(wordPanel);
        wordGroup.addActor(wordLabel);

        loadHangmans();
        hangman = new Image(hangmansArray[-1 * (life - 10)]);
        lettersTable = new Table();
        lettersList = new ArrayList<Letter>();

        mainTable = new Table();

        endTable = new Table();
        tryAgain = new Button(module.createButtonStyle(Atlases.playScreenAtlas.findRegion("tryagain")));
        back = new Button(module.createButtonStyle(Atlases.settingsScreenAtlas.findRegion("back")));
        back.setPosition(Hangman.WIDTH * Settings.MULTIPLIER - back.getWidth(),0);
    }

    @Override
    public void show() {
        super.show();

        wordGroup.setPosition(0, viewport.getWorldHeight() - panel.getRegionHeight() * 2);

        lettersTable.setFillParent(true);
        lettersTable.bottom();
        initializeLetters();
        drawLetters();

        mainTable.setFillParent(true);
        mainTable.bottom();
        mainTable.add(hangman);
        mainTable.row();
        mainTable.add(lettersTable).padTop(10).padRight(42);
        mainTable.padBottom((Settings.language.equals(Language.ENGLISH) ? 64 : 32));

        stage.addActor(mainTable);
        stage.addActor(wordGroup);

        endTable.setFillParent(true);
        endTable.center();

        tryAgain.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                if(game.mode.equals(Mode.ONE_PLAYER))
                    drawBlackScreen(new OnePlayerWS(game));
                if(game.mode.equals(Mode.TWO_PLAYERS))
                    drawBlackScreen(new SimpleWS(game));
                if(game.mode.equals(Mode.MULTIPLAYER))
                    drawBlackScreen(new SimpleWS(game));
            }
        });

        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                drawBlackScreen(new DifficultyScreen(game));
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(!won)
            updateWordLabel();
        else
            wordLabel.setText(word);
        hangman.setDrawable(new TextureRegionDrawable(hangmansArray[-1 * (life - 10)]));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    //"wordword" -> "________"
    public void convertWord(char[] str){
        chars = str;
        for(int i = 0; i<chars.length; i++)
            if(chars[i] == ' ')
                chars[i] = ' ';
            else chars[i] = '_';
        updateWordLabel();
    }

    private void updateWordLabel(){
        wordLabel.setText(String.valueOf(chars));
    }

    //Adding listener to letter
    private void initializeLetters(){
        for(char ch : letters) {
            final Letter l = new Letter(ch, new Button.ButtonStyle());
            l.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Settings.SOUND)
                        Audio.buttonSound.play();
                    if(!l.isDisabled()) {
                        l.setDisabled(true);
                        checkLetter(l.getLetter());
                        if (checkAnswer()) {
                            game.player.setCoins(game.player.getCoins() + 10);
                            updateCoinLabel();
                            drawWinScreen();
                            savePlayer();
                        }
                    }
                }
            });
            lettersList.add(l);
        }
    }

    private void savePlayer() {
        Gdx.files.local("player.txt").writeString(
                "Coins : " + String.valueOf(game.player.getCoins()) + "\r" +
                        "First letter : " + String.valueOf(game.player.getFirstLetter()) + "\r" +
                        "Last letter : " + String.valueOf(game.player.getLastLetter()) + "\r" +
                        "Random letter : " + String.valueOf(game.player.getRandomLetter()) + "\r" +
                        "Second chance : " + String.valueOf(game.player.getSecondChance()) + "\r",
                false);
    }

    //Adding letters to list with padding
    private void drawLetters() {
        for(int i = 0; i<lettersList.size(); i++){
            if(i == 7 || i == 14 || i == 21 || i == 28)
                lettersTable.row().padTop(10);
            lettersTable.add(lettersList.get(i)).expandX();
        }
    }

    //Checking letter for having that letter in word
    private void checkLetter(char letter){
        boolean have = false;

        for(int i = 0; i < chars.length; i++){
            if(word.charAt(i) == Character.toLowerCase(letter)){
                have = true;
                chars[i] = Character.toLowerCase(letter);
            }
            if(word.charAt(i) == letter){
                have = true;
                chars[i] = letter;
            }
            updateWordLabel();
        }

        if(!have){
            life -= 1;
            if(life == 0)
                drawLoseScreen();
        }
    }

    private boolean checkAnswer(){
        if(String.valueOf(chars).equals(word))
            return true;
        return false;
    }

    private void loadHangmans(){
        TextureRegion tmp = Atlases.playScreenAtlas.findRegion("hangman");
        for(int i = 0; i < tmp.getRegionWidth() / 64; i++)
            hangmansArray[i] = new TextureRegion(tmp, i * 64, 0, 64, 64);
    }

    private void drawWinScreen(){
        Image winImage = new Image(Atlases.playScreenAtlas.findRegion("winscreen"));
        createEndScreen(winImage);
    }

    private void drawLoseScreen(){
        Image loseImage = new Image(Atlases.playScreenAtlas.findRegion("losescreen"));
        createEndScreen(loseImage);
    }

    //Calling at the end of win or lose screen functions
    private void createEndScreen(Image endScreen){

        mainTable.remove();

        endTable.add(hangman);
        endTable.row();
        endTable.add(endScreen).padTop(10);
        endTable.row();
        endTable.add(tryAgain).padTop(10);

        stage.addActor(endTable);
        stage.addActor(back);

        won = true;
    }
}
