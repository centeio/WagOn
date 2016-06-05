package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.feup.lpoo.WagOn;
import com.feup.lpoo.WagonStates.*;

/**
 * Created by inesf on 14/05/2016.
 */
public class Wagon extends Piece {

    public static final int WIDTH = 75;
    public static final int HEIGHT = 38;

    protected int score = 0;
    WagonState state;

    public Wagon() {
        super(WagOn.WIDTH/2 -WIDTH/2, Floor.GROUND_HEIGHT, "wagon.png", WIDTH,  HEIGHT);

        state = new com.feup.lpoo.WagonStates.Moving(this);
    }


    public Wagon(String texture) {
        super(WagOn.WIDTH/2 -WIDTH/2, Floor.GROUND_HEIGHT, texture, WIDTH,  HEIGHT);

        state = new com.feup.lpoo.WagonStates.Moving(this);
    }

    public void update(float dt){
        velocity.add(acceleration);
        velocity.scl(dt);
        position.add(velocity);
        velocity.scl(1/dt);

        if(!(state instanceof Falling)){
        if(position.y < Floor.GROUND_HEIGHT)
            position.y = Floor.GROUND_HEIGHT;
        if(position.y > WagOn.HEIGHT-height)
            position.y = WagOn.HEIGHT-height;
        }

        if(position.x < 0) {
            position.x = 0;
            velocity.x = 0;
        }
        if(position.x > WagOn.WIDTH - width) {
            position.x = WagOn.WIDTH - width;
            velocity.x = 0;
        }

        bounds.setPosition(position.x, position.y);
    }

    public void detectFall(Floor floor) {
        if(position.y == Floor.GROUND_HEIGHT && floor.destroyedBetween(position.x,position.x+WIDTH)) {
            System.out.println("detetou queda");
            setState(new Falling(this));
        }
    }

    public void updateOnAccelerometer(float acc){
        state.update(acc*2);
    }

    public void stop(){
        this.velocity.x = 0;
    }

    public void jump(){
        state.jump();
    }

    public void setAccelerationX(float accelerationX){
        acceleration.x = accelerationX;
    }

    public void setAccelerationY(float accelerationY){
        acceleration.y = accelerationY;
    }

    public void setVelocityX(int velocityX) {
        this.velocity.x = velocityX;
    }

    public void setVelocityY(int velocityY) {
        this.velocity.y = velocityY;
    }

    public void setState(WagonState state) {
        this.state = state;
    }

    public void incScore() {score++;}

    public int getScore() {
        return score;
    }

    public WagonState getState(){ return state;}

    @Override
    public void reset() {
        position.set(WagOn.WIDTH/2 -WIDTH/2, Floor.GROUND_HEIGHT);
        score = 0;
    }
}
