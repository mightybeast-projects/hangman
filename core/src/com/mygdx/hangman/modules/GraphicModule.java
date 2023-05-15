package com.mygdx.hangman.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.hangman.data.Settings;
import com.mygdx.hangman.sprites.Atlases;

//Loads all graphic files and initialize atlases
//Can create different button styles.

public class GraphicModule {

    public void loadAtlases(){
        Atlases.basicScreenAtlas = new TextureAtlas("GFX\\" + Settings.language + "\\basicScreenAtlas.txt");
        Atlases.settingsScreenAtlas = new TextureAtlas("GFX\\" + Settings.language + "\\settingsScreenAtlas.txt");
        Atlases.menuScreenAtlas = new TextureAtlas("GFX\\" + Settings.language + "\\menuScreenAtlas.txt");
        Atlases.difficultyScreenAtlas = new TextureAtlas("GFX\\" + Settings.language + "\\difficultyScreenAtlas.txt");
        Atlases.wordScreenAtlas = new TextureAtlas("GFX\\" + Settings.language + "\\wordScreenAtlas.txt");
        Atlases.playScreenAtlas = new TextureAtlas("GFX\\" + Settings.language + "\\playScreenAtlas.txt");
        Atlases.lettersAtlas = new TextureAtlas("GFX\\" + Settings.language + "\\lettersAtlas.txt");
        Atlases.shopScreenAtlas = new TextureAtlas("GFX\\" + Settings.language + "\\shopScreenAtlas.txt");
        //Atlases.upgradesScreenAtlas = new TextureAtlas("GFX\\" + Settings.language + "\\upgradesScreenAtlas.txt");

        Settings.font = new BitmapFont(Gdx.files.internal("GFX\\font.fnt"));
    }

    public Button.ButtonStyle createButtonStyle(TextureRegion texture){
        TextureRegion up = new TextureRegion(texture, 0, 0, texture.getRegionWidth() / 2, texture.getRegionHeight());
        TextureRegion down = new TextureRegion(texture, texture.getRegionWidth() / 2, 0, texture.getRegionWidth() / 2, texture.getRegionHeight());
        Button.ButtonStyle bs = new Button.ButtonStyle();
        bs.up = new TextureRegionDrawable(up);
        bs.down = new TextureRegionDrawable(down);
        return bs;
    }

    public void updateButtonStyle(Button button, TextureRegion texture){
        TextureRegion up = new TextureRegion(texture, 0, 0, texture.getRegionWidth() / 2, texture.getRegionHeight());
        TextureRegion down = new TextureRegion(texture, texture.getRegionWidth() / 2, 0, texture.getRegionWidth() / 2, texture.getRegionHeight());
        Button.ButtonStyle bs = button.getStyle();
        bs.up = new TextureRegionDrawable(up);
        bs.down = new TextureRegionDrawable(down);
        button.setStyle(bs);
    }

    public void updateLetterBS(Button button, TextureRegion up, TextureRegion down){
        Button.ButtonStyle bs = button.getStyle();
        bs.up = new TextureRegionDrawable(up);
        bs.down = new TextureRegionDrawable(down);
        button.setStyle(bs);
    }

    public TextButton.TextButtonStyle createTextButtonStyle(TextureRegion up){
        TextButton.TextButtonStyle bs = new TextButton.TextButtonStyle();
        bs.font = Settings.font;
        bs.up = new TextureRegionDrawable(up);
        bs.down = bs.up;
        bs.fontColor = Color.BLACK;
        return bs;
    }

    public Button.ButtonStyle createFlagBS(TextureRegion upNDown){
        Button.ButtonStyle bs = new Button.ButtonStyle();
        bs.up = new TextureRegionDrawable(upNDown);
        bs.down = bs.up;
        return bs;
    }

    public void updateFlagBS(Button flag, TextureRegion texture){
        Button.ButtonStyle bs = flag.getStyle();
        bs.up = new TextureRegionDrawable(texture);
        bs.down = bs.up;
        flag.setStyle(bs);
    }

}
