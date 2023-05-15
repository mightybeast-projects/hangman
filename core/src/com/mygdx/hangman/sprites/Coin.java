package com.mygdx.hangman.sprites;

import com.badlogic.gdx.math.Vector2;

public class Coin {

    private Animation coinAnimation;
    private Vector2 position;

    public Coin(Vector2 position){
        this.position = position;
        coinAnimation = new Animation(Atlases.basicScreenAtlas.findRegion("coin"), 6, 0.5f);
    }

    public void updatePosition(Vector2 position){
        this.position = position;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void update(float dt){
        coinAnimation.update(dt);
    }

    public Animation getCoinAnimation() {
        return coinAnimation;
    }
}
