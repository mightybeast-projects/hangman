package com.mygdx.hangman.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    public final static int udpPort = 55555, tcpPort = 55556;

    public static void registerClasses(EndPoint ep){
        Kryo kryo = ep.getKryo();
        kryo.register(String.class);
        kryo.register(Boolean.class);
    }
}
