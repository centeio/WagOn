package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.feup.lpoo.WagOn;

import java.util.Stack;

/**
 * Class representing floor made of tiles
 * @author Carolina Centeio e Ines Proenca
 */
public class Floor {
    private Array<Tile> tiles;                  /**Tiles constituting floor*/
    public static final int GROUND_HEIGHT = 30; /**Floor height*/
    private float tileWidth;                    /**Width of a tile*/
    private Stack<Integer> destroyedTiles;      /**Stack with destroyed tiles*/

    /**
     * Constructor of class Floor
     * @param numTiles number of tiles in floor
     */
    public Floor(int numTiles){
        tiles = new Array<Tile>();
        tileWidth = WagOn.WIDTH/(float)numTiles;
        destroyedTiles = new Stack<Integer>();

        for(int i = 0; i < numTiles; i++)
            tiles.add(new Tile(i*tileWidth));
    }

    /**
     * Draws every tile in sprite batch
     * @param sb sprite batch in which to draw
     */
    public void render(SpriteBatch sb){
        for(Tile tile: tiles)
            if(!tile.isDestroyed())
                sb.draw(tile.getTexture(), tile.getPosX(), 0 , tileWidth, GROUND_HEIGHT);
    }

    /**
     * Get tiles
     * @return tiles
     */
    public Array<Tile> getTiles(){
        return tiles;
    }

    /**
     * Destroys a tile at position x
     * @param x position of tile to destroy
     */
    public void destroyTile(float x){
        int index = (int)(x/tileWidth);
        tiles.get(index).destroy();
        destroyedTiles.add(index);
    }

    /**
     * Recovers the last tile to be destroyed
     */
    public void recoverTile(){
        if(destroyedTiles.empty())
            return;

        int top = destroyedTiles.pop();
        tiles.get(top).reset();
    }

    /**
     * Check if floor is destroyed between two x coordinates
     * @param minx minimum x coordinate
     * @param maxx maximum x coordinate
     * @return whether or not floor is completely destroyed between those coordinates
     */
    public boolean destroyedBetween(float minx, float maxx){
        boolean ret = true;
        for(Tile  t: tiles){
            if(t.getPosX() +  tileWidth >= minx && t.getPosX()<= maxx) {

                if (!t.isDestroyed())

                    ret = false;
            }
        }
        return ret;
    }

    /**
     * Resets tiles to their original state (not destroyed)
     */
    public void reset(){
        for(Tile tile: tiles){
            tile.reset();
        }
    }
}
