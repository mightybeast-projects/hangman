package com.mygdx.hangman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.Audio;
import com.mygdx.hangman.data.Settings;
import com.mygdx.hangman.network.GameClient;
import com.mygdx.hangman.network.GameServer;
import com.mygdx.hangman.sprites.Atlases;
import com.mygdx.hangman.sprites.Coin;
import com.mygdx.hangman.modules.GraphicModule;

public class BasicScreen implements Screen{
    protected Hangman game;
    protected Stage stage;
    protected OrthographicCamera cam;
    protected Viewport viewport;
    protected TextureRegion bg, panel;
    protected GraphicModule module;
    protected Table panelTable;
    protected Label coinLabel, message;
    protected Button settings, blackScreen, exit, menu, shop;
    protected BasicScreen currentScreen;
    private Coin coin;
    private Group group;
    private boolean scrolled;

    public BasicScreen(){
        module = new GraphicModule();
        cam = new OrthographicCamera();
        panel = new TextureRegion(Atlases.basicScreenAtlas.findRegion("panel"));
        coin = new Coin(new Vector2(0, 0));
        settings = new Button(module.createButtonStyle(Atlases.basicScreenAtlas.findRegion("settings")));
        blackScreen = new Button(module.createFlagBS(Atlases.basicScreenAtlas.findRegion("blackscreen")));
        exit = new Button(module.createButtonStyle(Atlases.basicScreenAtlas.findRegion("exit")));
        menu = new Button(module.createButtonStyle(Atlases.basicScreenAtlas.findRegion("menu")));
        shop = new Button(module.createButtonStyle(Atlases.basicScreenAtlas.findRegion("shop")));
        bg = Atlases.basicScreenAtlas.findRegion("bg");
        group = new Group();
    }

    @Override
    public void show() {
        currentScreen = this;
        coinLabel = new Label(String.valueOf(game.player.getCoins()), new Label.LabelStyle(Settings.font, Color.WHITE));
        message = new Label("Please stand by ...", new Label.LabelStyle(Settings.font, Color.WHITE));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        menu.setBounds(stage.getViewport().getWorldWidth() - 32, viewport.getWorldHeight() - 32, 32, 32);

        settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                drawBlackScreen(new SettingsScreen(game, currentScreen));
            }
        });

        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.exitSound.play();
                Gdx.app.exit();
                System.exit(0);
            }
        });

        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
                if(scrolled) {
                    group.addAction(Actions.moveBy(0, group.getHeight() + 32, 0.1f));
                    scrolled = false;
                }
                else {
                    group.addAction(Actions.moveBy(0, -1 * group.getHeight() - 32, 0.1f));
                    group.toFront();

                    menu.toFront();
                    scrolled = true;
                }
            }
        });

        shop.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.SOUND)
                    Audio.buttonSound.play();
            }
        });

        final int SPACE = 5;
        group.setSize(32, 3 * 32 + 3 * SPACE);
        group.addActor(exit);
        shop.setPosition(0, 32 + SPACE);
        group.addActor(shop);
        settings.setPosition(0, 64 + SPACE * 2);
        group.addActor(settings);
        group.setPosition(viewport.getWorldWidth() - 32, viewport.getWorldHeight());

        panelTable = new Table();
        panelTable.setFillParent(true);
        panelTable.top().right();
        panelTable.add(coinLabel).padTop(panel.getRegionHeight() / 2 + 2).padRight(36);

        stage.addActor(blackScreen);
        stage.addActor(panelTable);
        stage.addActor(menu);
        stage.addActor(group);

        blackScreen.addAction(Actions.fadeOut(0.2f));
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(1, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(blackScreen.getColor().a == 0)
            blackScreen.toBack();
        if(blackScreen.getColor().a != 0)
            blackScreen.toFront();

        coin.update(delta);
        coin.updatePosition(
                new Vector2(viewport.getWorldWidth() - (settings.getWidth() + coinLabel.getText().length * 12 + 21),
                        viewport.getWorldHeight() - (panel.getRegionHeight() / 2 + 7)));

        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        game.batch.draw(panel, 0, viewport.getWorldHeight() - 32);
        game.batch.draw(coin.getCoinAnimation().getFrame(), coin.getPosition().x, coin.getPosition().y);
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
    }

    protected void updateCoinLabel(){
        coinLabel.setText(String.valueOf(game.player.getCoins()));
    }

    public void drawBlackScreen(final BasicScreen screen){
        blackScreen.addAction(Actions.fadeIn(0.2f));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(screen);
            }
        }, 0.2f);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                dispose();
            }
        }, 0.2f);
    }

    public void outputMessage(String message, Table table) {
        this.message.setText(message);
        this.message.setAlignment(Align.center);
        table.remove();
        table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(this.message);
        stage.addActor(table);
    }

    protected void closeNetwork(GameServer server, GameClient client){
        if(client != null){
            client.close();
            client = null;
        }
        if(server != null){
            server.close();
            server = null;
        }
    }
}
