package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;

/**
 * Class representing floor tile
 * @author Carolina Centeio e Ines Proenca
 */
public class Tile {
    private static Texture tile = null; /**Tile texture*/
    private float posX;                 /**Tile's leftmost x coordinate*/
    private boolean destroyed;          /**Is tile destroyed?*/

    /**
     * Constructor for class Tile
     * @param x leftmost x coordinate
     */
    public Tile(float x){
        if(tile == null) {
            try {
                tile = new Texture("tile3.png");
            } catch (NullPointerException e) {
                System.out.println("Image not found");
            }
        }
        posX = x;
        destroyed = false;

    }

    /**
     * Get leftmost x coordinate
     * @return leftmost x coordinate
     */
    public float getPosX() {
        return posX;
    }

    /**
     * Get image representation
     * @return image representation
     */
    public Texture getTexture() {
        return tile;
    }

    /**
     * Is tile destroyed?
     * @return whether or not tile is destroyed
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Destroys tile
     */
    public void destroy(){
        destroyed = true;
    }

    /**
     * Make tile whole again
     */
    public void reset() {
        destroyed = false;
    }
}
