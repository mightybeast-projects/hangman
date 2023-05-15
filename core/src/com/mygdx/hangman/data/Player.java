package com.mygdx.hangman.data;

import com.mygdx.hangman.network.GameClient;
import com.mygdx.hangman.network.GameServer;

//Player has its own stats, coins,
//game client and game server.
//Game server can be never used.
public class Player {
    private int coins, firstLetter, lastLetter, randomLetter, secondChance;
    public GameClient client;
    public GameServer server;

    public Player(int coins, int firstLetter, int lastLetter, int randomLetter, int secondChance) {
        this.coins = coins;
        this.firstLetter = firstLetter;
        this.lastLetter = lastLetter;
        this.randomLetter = randomLetter;
        this.secondChance = secondChance;
    }

    public int getCoins(){
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(int firstLetter) {
        this.firstLetter = firstLetter;
    }

    public int getLastLetter() {
        return lastLetter;
    }

    public void setLastLetter(int lastLetter) {
        this.lastLetter = lastLetter;
    }

    public int getRandomLetter() {
        return randomLetter;
    }

    public void setRandomLetter(int randomLetter) {
        this.randomLetter = randomLetter;
    }

    public int getSecondChance() {
        return secondChance;
    }

    public void setSecondChance(int secondChance) {
        this.secondChance = secondChance;
    }
}
