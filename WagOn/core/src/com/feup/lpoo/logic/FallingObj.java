package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.feup.lpoo.WagOn;

/**
 * Class representing a FallingObj
 * @author Carolina Centeio e Ines Proenca
 */
public abstract class FallingObj extends Piece{
    public int HEIGHT;                      /**Falling object's heigth*/
    public static final int WIDTH = 20;     /**Falling object's width*/
    private static final int GRAVITY = -2;  /**Gravity affecting FallingObj*/

    protected long startTime = 0;           /**Time to start falling in milliseconds*/

    /**
     * Constructor for class FallingObj
     * @param x
     * @param tex
     * @param h
     */
    protected FallingObj(int x, String tex, int h) {
        super(x, WagOn.HEIGHT, tex,  WIDTH,  h);
        acceleration.y = GRAVITY;
    }

    @Override
    public void update(float dt){
        if(startTime <= TimeUtils.millis()) {
            velocity.add(acceleration);
            velocity.scl(dt);
            position.add(velocity);
            velocity.scl(1 / dt);
        }
        bounds.setPosition(position.x, position.y);
    }

    /**
     * Reposition object in the top of the screen in a random x coordinate and updates startTime
     */
    protected void reposition(){
        position.set(MathUtils.random(0, WagOn.WIDTH - FallingObj.WIDTH), WagOn.HEIGHT);
        velocity.set(0,0);

        updateStartTime();
    }

    /**
     * Updates when object should start falling
     */
    protected abstract void updateStartTime();

    /**
     * Detects collision between object and wagon
     * @param wagon wagon which may have colided
     * @return wether collision occurred
     */
    public abstract boolean detectCollision(Wagon wagon);

    /**
     * Detects collision between object and floor
     * @param floor floor which may have colided
     */
    public abstract void detectCollision(Floor floor);

    @Override
    public void reset() {
       reposition();
    }
}
