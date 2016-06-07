package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.css.Rect;

/**
 * Class representing game piece
 * @author Carolina Centeio e Ines Proenca
 */
public abstract class Piece {
    private static final int  GRAVITY = -10;    /**Gravity constant*/
    protected Rectangle bounds;                 /**Pieces outside bounds*/
    protected Vector2 position;                 /**Pieces position on screen*/
    protected Vector2 velocity;                 /**Piece's instant velocity*/
    protected Vector2 acceleration;             /**Piece's acceleration*/
    protected Texture tex;                      /**Piece's image representation*/
    protected int width, height;                /**Piece's image dimensions*/

    /**
     * Constructor for class Piece
     * @param x inicial x coordinate
     * @param y inicial y coordinate
     * @param tex name of image
     * @param width image width
     * @param height image heigth
     */
    public Piece(int x, int y, String tex, int width, int height) {
        this.position = new Vector2(x,y);
        try {
            this.tex = new Texture(tex);
        }catch(NullPointerException e){
            System.out.println("Image not found");
        }
        this.velocity = new Vector2(0,0);
        this.acceleration = new Vector2(0, GRAVITY);
        this.width = width;
        this.height = height;

        bounds = new Rectangle(x,y, width,height);
    }

    /**
     * Gets Pieces current position
     * @return current position
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Gets Pieces current velocity
     * @return current velocity
     */
    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * Gets Pieces current acceleration
     * @return current acceleration
     */
    public Vector2 getAcceleration() {
        return acceleration;
    }

    /**
     * Gets Pieces image representation
     * @return image representation
     */
    public Texture getTex() {
        return tex;
    }

    /**
     * Gets Pieces bounds
     * @return bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Gets Pieces image width
     * @return image width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets Pieces image height
     * @return image height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Updates piece's position and bounds
     *
     * @param dt time passed since last update
     */
    public abstract void update(float dt);

    /**
     * Resets piece to it's original state
     */
    public abstract void reset();

    public void setPosition(Vector2 p){position.x=p.x; position.y=p.y;}
}
