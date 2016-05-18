package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by inesf on 11/05/2016.
 */
public class Tile {
    private Texture tile;
    private int posX;

    public Tile(int x){
        tile = new Texture("tile.png");
        posX = x;
    }

    public int getPosX() {
        return posX;
    }

    public Texture getTexture() {
        return tile;
    }
}
