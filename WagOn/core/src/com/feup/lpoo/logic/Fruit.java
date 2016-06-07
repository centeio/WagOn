package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.feup.lpoo.WagOn;

/**
 * Class representing falling fruit
 * @author Carolina Centeio e Ines Proenca
 */
public class Fruit extends FallingObj {
    private static float TIME_TO_SPEEDUP = 20f; /**Time taken for fruit to speedup*/
    protected static int HEIGHT = 20; /**Fruit's texture height*/
    protected float speed;
    private float timeSinceSpeedUp;
    private boolean isSinglePlayer;

    /**
     * Constructor for class Fruit
     * @param x starting x coordinate
     */
    public Fruit(int x) {
        super(x, "melon.png",HEIGHT);

        isSinglePlayer = false;
        startTime = TimeUtils.millis();
        timeSinceSpeedUp = 0;
        speed = 1f;
    }

    /**
     * Constructor for class Fruit
     */
    public Fruit() {
        super(0, "melon.png",HEIGHT);

        reposition();
        timeSinceSpeedUp = 0;
        speed = 1f;
    }

    public void setSinglePlayer(){
        isSinglePlayer = true;
    }

    @Override
    protected void updateStartTime() {
        int r = MathUtils.random(0, 4);
        startTime = TimeUtils.millis() + r*250;
            }

    @Override
    public void update(float dt){
        if(isSinglePlayer) {
            timeSinceSpeedUp += dt;
            if (timeSinceSpeedUp >= TIME_TO_SPEEDUP) {
                speed += 0.3f;
                timeSinceSpeedUp = 0;
            }
        }
        if(first){
            reposition();
            first = false;
        }
        if(startTime <= TimeUtils.millis()) {
            acceleration.scl(speed);
            velocity.add(acceleration);
            velocity.scl(dt);
            position.add(velocity);
            velocity.scl(1 / dt);
            acceleration.scl(1/speed);
        }
        bounds.setPosition(position.x, position.y);

        if(position.y < 0)
            reposition();
    }

    @Override
    public boolean detectCollision(Wagon wagon) {
        if(wagon.getBounds().overlaps(bounds)){
            reposition();
            wagon.incScore();
            return true;
        }
        return false;
    }

    @Override
    public boolean detectCollision(Floor floor) {
        if (position.y + HEIGHT < 0) {
            reposition();
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        timeSinceSpeedUp = 0;
        speed = 1f;
    }
}
