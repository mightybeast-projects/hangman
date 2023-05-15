package com.mygdx.hangman.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class GameServer extends Server {
    private Connection[] connections;
    private boolean full = false;
    private String P1WORD, P2WORD;

    public GameServer() throws IOException {
        bind(Network.tcpPort, Network.udpPort);
        Network.registerClasses(this);
        initializeListeners();
        start();
    }

    private void initializeListeners() {
        addListener(new Listener(){
            @Override
            public void connected(Connection connection) {
                if(!full) {
                    connections = getConnections();
                    if (connections.length == 2) {
                        sendToAllTCP(true);
                        full = true;
                    }
                }
            }

            @Override
            public void received(Connection connection, Object object) {
                if(object instanceof String){
                    String str = ((String) object).intern();
                    if(P1WORD == null)
                        P1WORD = str;
                    else if(P2WORD == null)
                        P2WORD = str;
                    if(P1WORD != null && P2WORD != null){
                        sendToTCP(connections[0].getID(), P2WORD);
                        sendToTCP(connections[1].getID(), P1WORD);
                        P1WORD = null;
                        P2WORD = null;
                    }
                }
            }
        });
    }
}
