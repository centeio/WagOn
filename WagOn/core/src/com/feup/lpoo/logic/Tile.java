package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by inesf on 11/05/2016.
 */
public class Tile {
    private Texture tile;
    private float posX;
    private boolean destroyed;

    public Tile(float x){
        tile = new Texture("tile3.png");
        posX = x;
        destroyed = false;

    }

    public float getPosX() {
        return posX;
    }

    public Texture getTexture() {
        return tile;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy(){
        destroyed = true;
    }
}
