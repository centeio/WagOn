package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.feup.lpoo.WagOn;

/**
 * Created by inesf on 11/05/2016.
 */
public class Floor {
    private Array<Tile> tiles;
    private int tileWidth;

    public Floor(int numTiles){
        tiles = new Array<Tile>();
        tileWidth = WagOn.WIDTH/numTiles;

        for(int i = 0; i < numTiles; i++)
            tiles.add(new Tile(i*tileWidth));
    }

    public void render(SpriteBatch sb){
        for(Tile tile: tiles)
            if(!tile.isDestroyed())
                sb.draw(tile.getTexture(), tile.getPosX(), 0 , tileWidth, 30);
    }

    public void destroyTile(float x){
        tiles.get((int)x/tileWidth).destroy();
    }
}
