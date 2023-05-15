package com.mygdx.hangman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.enums.Language;
import com.mygdx.hangman.network.GameClient;
import com.mygdx.hangman.network.GameServer;
import com.mygdx.hangman.network.Network;
import com.mygdx.hangman.data.Settings;
import com.mygdx.hangman.sprites.Atlases;

import java.io.IOException;
import java.net.InetAddress;

public class MultiplayerScreen extends BasicScreen {
    public static Table table;
    private Button leave;

    public MultiplayerScreen(Hangman game){
        super();
        this.game = game;
        viewport = new FitViewport(Hangman.WIDTH * Settings.MULTIPLIER,Hangman.HEIGHT * Settings.MULTIPLIER, cam);
        leave = new Button(module.createButtonStyle(Atlases.settingsScreenAtlas.findRegion("back")));
        leave.setPosition(Hangman.WIDTH * Settings.MULTIPLIER - leave.getWidth(), 0);
        leave.setVisible(false);

        table = new Table();
    }

    @Override
    public void show() {
        super.show();

        leave.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeNetwork(game.player.server, game.player.client);
                drawBlackScreen(new MenuScreen(game));
            }
        });

        stage.addActor(leave);
        outputMessage((Settings.language.equals(Language.ENGLISH))? "Connecting to "+ "\n" +"the server..." :
                                                                    "Подключение к "+ "\n" +"серверу...", table);
        stage.addActor(table);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                startClient();
            }
        }, 2);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                leave.setVisible(true);
            }
        }, 6);
    }

    private void startClient() {
        game.player.client = new GameClient(game, this);
        InetAddress host = game.player.client.discoverHost(Network.udpPort, 60);
        if(host == null){
            try {
                game.player.server = new GameServer();
                startClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                game.player.client.connect(100, host, Network.tcpPort, Network.udpPort);
                outputMessage((Settings.language.equals(Language.ENGLISH))? "Connected to "+ "\n" +"the server! " :
                                                                            "Подключено к "+ "\n" +"серверу!", table);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

}
