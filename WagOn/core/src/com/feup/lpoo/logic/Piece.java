package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.css.Rect;

public class Piece {
    private static final int  GRAVITY = -10;
    Rectangle bounds;
    Vector2 position;
    Vector2 velocity;
    Vector2 acceleration;
    Texture tex;
    int width, height;

    public Piece(int x, int y, Texture tex, int width, int height) {
        this.position = new Vector2(x,y);
        this.tex = tex;
        this.velocity = new Vector2(0,0);
        this.acceleration = new Vector2(0, GRAVITY);
        this.width = width;
        this.height = height;

        bounds = new Rectangle(x,y, width,height);
    }

    public Vector2 getPosition() {
        return position;
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
}
