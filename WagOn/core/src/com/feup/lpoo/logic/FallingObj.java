package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.feup.lpoo.WagOn;

/**
 * Created by inesf on 18/05/2016.
 */
public abstract class FallingObj extends Piece{
    public int HEIGHT;
    public static final int WIDTH = 20;
    private static final int GRAVITY = -2;

    public static long lastDropTime;

    private long startTime;

    public FallingObj(int x, Texture tex, int h) {
        super(x, WagOn.HEIGHT, tex,  WIDTH,  h);
        acceleration.y = GRAVITY;
        lastDropTime = TimeUtils.nanoTime();
        startTime = TimeUtils.nanoTime();
    }

    public void update(float dt){
        if(startTime < TimeUtils.nanoTime()) {
            velocity.add(acceleration);
            velocity.scl(dt);
            position.add(velocity);
            velocity.scl(1 / dt);
        }
        bounds.setPosition(position.x, position.y);
    }

    protected void reposition(){
        position.set(MathUtils.random(0, WagOn.WIDTH - FallingObj.WIDTH), WagOn.HEIGHT);
        velocity.set(0,0);
        lastDropTime = TimeUtils.nanoTime();
        startTime = TimeUtils.nanoTime() + 1000000000;
    }

    public abstract boolean detectCollision(Wagon wagon);
    public abstract void detectCollision(Floor floor);
}
