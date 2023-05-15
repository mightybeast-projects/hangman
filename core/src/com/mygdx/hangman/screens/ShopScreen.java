package com.mygdx.hangman.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.Audio;
import com.mygdx.hangman.data.Settings;
import com.mygdx.hangman.sprites.Atlases;

public class ShopScreen extends BasicScreen{
    private Image shop;
    private Button upgrades, skins, back;
    private Table shopTable, buttonsTable;

    public ShopScreen(Hangman game) {
        super();
        this.game = game;
        viewport = new FitViewport(Hangman.WIDTH * Settings.MULTIPLIER,Hangman.HEIGHT * Settings.MULTIPLIER, cam);

        shop = new Image(Atlases.shopScreenAtlas.findRegion("shop"));
        upgrades = new Button(module.createButtonStyle(Atlases.shopScreenAtlas.findRegion("upgrades")));
        skins = new Button(module.createButtonStyle(Atlases.shopScreenAtlas.findRegion("skins")));
        back = new Button(module.createButtonStyle(Atlases.settingsScreenAtlas.findRegion("back")));
        shopTable = new Table();
        buttonsTable = new Table();
    }

    @Override
    public void show() {
        super.show();

        upgrades.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                drawBlackScreen(new UpgradesScreen(game));
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

        shopTable.setFillParent(true);
        buttonsTable.setFillParent(true);

        shopTable.center().top().padTop(42);
        shopTable.add(shop);

        buttonsTable.center();
        buttonsTable.add(upgrades).padBottom(10);
        buttonsTable.row();
        buttonsTable.add(skins);

        back.setPosition(viewport.getWorldWidth() - 32, 0);

        stage.addActor(shopTable);
        stage.addActor(buttonsTable);
        stage.addActor(back);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
