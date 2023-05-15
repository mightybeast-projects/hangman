package com.mygdx.hangman.screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.Settings;
import com.mygdx.hangman.sprites.Atlases;

import java.util.ArrayList;

public class UpgradesScreen extends BasicScreen {

    private Image upgrades, firstLetterImg, lastLetterImg, randomLetterImg, secondChanceImg;
    private ScrollPane scrollPane;
    private Table scrollTable;
    private Group flTable;

    public UpgradesScreen(Hangman game) {
        super();
        this.game = game;
        viewport = new FitViewport(Hangman.WIDTH * Settings.MULTIPLIER, Hangman.HEIGHT * Settings.MULTIPLIER, cam);

        createScrollPane();
    }

    @Override
    public void show() {
        super.show();
        stage.addActor(flTable);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void createScrollPane(){
        scrollTable = new Table();

        flTable = createUpgrade("firstletter", game.player.getFirstLetter());
        Group llTable = createUpgrade("secondchance", game.player.getLastLetter() + 10);

        System.out.println(flTable.getX());
    }

    private Group createUpgrade(String name, int percent){
        Button upgradeName, upgradeButton;
        TextureRegion barsRegion;
        ArrayList<Image> bars;

        Group out = new Group();

        Table table = new Table();
        table.setFillParent(true);
        table.bottom();

        barsRegion = new TextureRegion(Atlases.upgradesScreenAtlas.findRegion("bar"));
        bars = new ArrayList<Image>();

        for(int i = 0; i<11; i++)
            bars.add(new Image(new TextureRegion(barsRegion, 0, 32 * i, 161, 32)));

        upgradeName = new Button(module.createButtonStyle(Atlases.upgradesScreenAtlas.findRegion(name))) ;
        Image bar = bars.get(percent / 10);
        upgradeButton = new Button(module.createButtonStyle(Atlases.upgradesScreenAtlas.findRegion("upgrade")));

        table.add(upgradeName).padBottom(10);
        table.row();
        table.add(bar).padBottom(10);
        table.row();
        table.add(upgradeButton);

        out.addActor(table);
        return out;
    }
}
