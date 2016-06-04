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

    protected long startTime = 0;

    public FallingObj(int x, String tex, int h) {
        super(x, WagOn.HEIGHT, tex,  WIDTH,  h);
        acceleration.y = GRAVITY;
        if(this instanceof Bomb && startTime == 0)
            reposition();
        else
          startTime = TimeUtils.millis();
    }

    public void update(float dt){
        if(startTime <= TimeUtils.millis()) {
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
        int r = MathUtils.random(0,3) + 4;

        if(this instanceof Bomb)
            startTime = TimeUtils.millis() + r*2000;
        else
            startTime = TimeUtils.millis();

    }

    public abstract boolean detectCollision(Wagon wagon);
    public abstract void detectCollision(Floor floor);
}
