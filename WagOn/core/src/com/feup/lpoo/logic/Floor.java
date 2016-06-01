package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.feup.lpoo.WagOn;

/**
 * Created by inesf on 11/05/2016.
 */
public class Floor {
    private Array<Tile> tiles;
    public static final int GROUND_HEIGHT = 30;
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
                sb.draw(tile.getTexture(), tile.getPosX(), 0 , tileWidth, GROUND_HEIGHT);
    }

    public Array<Tile> getTiles(){
        return tiles;
    }

    public void destroyTile(float x){
        tiles.get((int)x/tileWidth).destroy();
    }

    public boolean destroyedBetween(float minx, float maxx){
        boolean ret = true;
        for(Tile  t: tiles){
            System.out.println(minx + " " + maxx + " " + t.getPosX()+ " " +(t.getPosX()+ tileWidth));
            if(t.getPosX() >= minx && t.getPosX()<= maxx + tileWidth) {
                System.out.println("tile");

                if (!t.isDestroyed()){
                    ret = false;
                    System.out.println("nao esta destruido");
                }
            }
        }
        return ret;
    }

    public int getTileWidth(){return tileWidth;}
}
