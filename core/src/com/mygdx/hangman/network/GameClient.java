package com.mygdx.hangman.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.hangman.Hangman;
import com.mygdx.hangman.data.Settings;
import com.mygdx.hangman.data.enums.Language;
import com.mygdx.hangman.screens.BasicScreen;
import com.mygdx.hangman.screens.MenuScreen;
import com.mygdx.hangman.screens.MultiplayerScreen;
import com.mygdx.hangman.screens.PlayScreens.SimplePS;
import com.mygdx.hangman.screens.WordScreens.SimpleWS;

import java.util.Timer;
import java.util.TimerTask;

public class GameClient extends Client {
    private Hangman game;
    private BasicScreen screen;

    public GameClient(Hangman game, MultiplayerScreen screen){
        super();
        this.game = game;
        this.screen = screen;
        Network.registerClasses(this);
        initializeListeners();
        start();
    }

    public void setScreen(BasicScreen screen){
        this.screen = screen;
    }

    private void initializeListeners() {
        addListener(new Listener(){

            @Override
            public void received(Connection connection, Object object) {
                if(object instanceof Boolean){
                    //if boolean = true
                    if(((Boolean) object).booleanValue()) {
                        screen.outputMessage((Settings.language.equals(Language.ENGLISH))? "Starting game..." :
                                                                                        "Начинаем игру...",
                                                MultiplayerScreen.table);
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                screen.drawBlackScreen(new SimpleWS(game));
                            }
                        }, 1000);
                    }
                    //if boolean false
                    else {
                        close();
                        screen.drawBlackScreen(new MenuScreen(game));
                    }
                }
                if(object instanceof String)
                    screen.drawBlackScreen(new SimplePS(game, ((String) object).intern()));
            }
        });
    }
}
