package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.css.Rect;

public abstract class Piece {
    private static final int  GRAVITY = -10;
    protected Rectangle bounds;
    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 acceleration;
    protected Texture tex;
    protected int width, height;

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

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public Texture getTex() {
        return tex;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract void reset();
}
