package com.feup.lpoo.logic;

import com.feup.lpoo.WagOn;
import com.feup.lpoo.WagonStates.*;

import java.io.Serializable;

/**
 * Class representing a fruit catching wagon
 * @author Carolina Centeio e Ines Proenca
 */
public class Wagon extends Piece {

    public static final int WIDTH = 75;     /**Wagon's width*/
    public static final int HEIGHT = 38;    /**Wagon's height */

    protected int score = 0;                /**Wagon's score <p>number of fruit caugth</p>*/
    WagonState state;                       /**Wagon's state*/

    /**
     * Default constructor for class Wagon.
     * <p>Initializes wagon in the bottom centre of the screen with default Texture</p>
     */
    public Wagon() {
        super(WagOn.WIDTH/2 -WIDTH/2, Floor.GROUND_HEIGHT, "wagon.png", WIDTH,  HEIGHT);

        state = new com.feup.lpoo.WagonStates.Moving(this);
    }

    /**
     * Constructor for class Wagon.
     * Initializes wagon in the bottom centre of the screen with chosen Texture
     *
     * @param texture name of texture chosen
     */
    public Wagon(String texture) {
        super(WagOn.WIDTH/2 -WIDTH/2, Floor.GROUND_HEIGHT, texture, WIDTH,  HEIGHT);

        state = new com.feup.lpoo.WagonStates.Moving(this);
    }

    @Override
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

    /**
     * Detects if floor beneath wagon is destroyed
     * @param floor floor above where wagon lives
     */
    public void detectFall(Floor floor) {
        if(position.y == Floor.GROUND_HEIGHT && floor.destroyedBetween(position.x,position.x+WIDTH)) {
            System.out.println("detetou queda");
            setState(new Falling(this));
        }
    }


    /**
     * Updates wagons acceleration based on values returned by accelerometer
     * @param acc accelerometers returned value <p>moving wagon's new acceleration</p>
     */
    public void updateOnAccelerometer(float acc){
        state.update(acc*2);
    }

    /**
     * Stop wagon
     */
    public void stop(){
        this.velocity.x = 0;
    }

    /**
     * Make wagon jump
     */
    public void jump(){
        state.jump();
    }

    /**
     * Set wagon's acceleration in x coordinate
     * @param accelerationX acceleration in x coordinate
     */
    public void setAccelerationX(float accelerationX){
        acceleration.x = accelerationX;
    }

    /**
     * Set wagon's acceleration in y coordinate
     * @param accelerationY acceleration in y coordinate
     */
    public void setAccelerationY(float accelerationY){
        acceleration.y = accelerationY;
    }

    /**
     * Set wagon's velocity in x coordinate
     * @param velocityX velocity in x coordinate
     */
    public void setVelocityX(int velocityX) {
        this.velocity.x = velocityX;
    }

    /**
     * Set wagon's velocity in y coordinate
     * @param velocityY velocity in y coordinate
     */
    public void setVelocityY(int velocityY) {
        this.velocity.y = velocityY;
    }

    /**
     * Change wagons state (Moving, Jumping or Falling)
     * @param state new WagonState to be applied
     */
    public void setState(WagonState state) {
        this.state = state;
    }

    /**
     * Increment score by one
     */
    public void incScore() {score++;}

    /**
     * Get wagon's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets wagon's state
     * @return current wagon state
     */
    public WagonState getState(){ return state;}

    @Override
    public void reset() {
        position.set(WagOn.WIDTH/2 -WIDTH/2, Floor.GROUND_HEIGHT);
        state = new Moving(this);
        score = 0;
    }
}
