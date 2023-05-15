package com.mygdx.hangman.sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.hangman.modules.GraphicModule;

public class Letter extends Button{
    private TextureRegion up, down;
    private TextureRegion[] textures = new TextureRegion[4];
    private GraphicModule module;
    private char letter;

    public Letter(char letter, ButtonStyle bs){
        super(bs);
        this.letter = letter;
        TextureRegion texture = new TextureRegion(Atlases.lettersAtlas.findRegion(String.valueOf(letter)));
        module = new GraphicModule();
        int width = texture.getRegionWidth(), height = texture.getRegionHeight();
        textures[0] = new TextureRegion(texture, 0, 0, width / 4, height);
        textures[1] = new TextureRegion(texture, width / 4, 0, width / 4, height);
        textures[2] = new TextureRegion(texture, width / 2, 0, width / 4, height);
        textures[3] = new TextureRegion(texture, width - width / 4, 0, width / 4, height);
        up = textures[0];
        down = textures[1];
        module.updateLetterBS(this, up, down);
    }

    @Override
    public void setDisabled(boolean isDisabled) {
        super.setDisabled(isDisabled);
        up = textures[2];
        down = textures[3];
        module.updateLetterBS(this, up, down);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public char getLetter(){
        return letter;
    }
}
