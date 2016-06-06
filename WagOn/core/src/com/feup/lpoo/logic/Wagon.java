package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
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

    Array<TextureRegion> frames;            /**animation frames*/
    float maxFrameTime;                     /**maximum time passed per frame*/
    float currentFrameTime;                 /**time in current passed in frame*/
    int frame;                              /**index of current frame*/
    boolean destroyed;                      /**is wagon destroyed (is animation on)?*/


    /**
     * Prepares the sprite for animation
     * @param region sprite image
     * @param frameCount number of frames in sprite
     * @param cycleTime animation duration
     */
    private void prepareAnimation(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        TextureRegion temp;
        int frameWidth = region.getRegionWidth() / frameCount;
        for(int i = 0; i < frameCount; i++){
            temp = new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight());
            frames.add(temp);
        }
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    /**
     * Updates frame to be drawn next
     * @param dt time since last update
     */
    private void updateAnimation(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frames.size)
            frame = frames.size;

    }

    /**
     * Default constructor for class Wagon.
     * <p>Initializes wagon in the bottom centre of the screen with default Texture</p>
     */
    public Wagon() {
        super(WagOn.WIDTH/2 -WIDTH/2, Floor.GROUND_HEIGHT, "wagon.png", WIDTH,  HEIGHT);

        try{
            prepareAnimation(new TextureRegion(new Texture("wagon destruction.png")), 5, 1f);
        }catch(NullPointerException e){
            System.out.println("Image not found");
        }

        state = new com.feup.lpoo.WagonStates.Moving(this);

        destroyed = false;
    }

    /**
     * Constructor for class Wagon.
     * Initializes wagon in the bottom centre of the screen with chosen Texture
     *
     * @param texture name of texture chosen
     */
    public Wagon(String texture) {
        super(WagOn.WIDTH/2 -WIDTH/2, Floor.GROUND_HEIGHT, texture, WIDTH,  HEIGHT);

        try {
            if (texture.equals("wagon.png"))
                prepareAnimation(new TextureRegion(new Texture("wagon destruction.png")), 5, 1f);
            else
                prepareAnimation(new TextureRegion(new Texture("wagon2 destruction.png")), 5, 1f);
        }catch(NullPointerException e){
            System.out.println("Image not found");
        }

        state = new com.feup.lpoo.WagonStates.Moving(this);

        destroyed = false;
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

        if(destroyed)
            updateAnimation(dt);

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
        frame = 0;
        destroyed = false;
        currentFrameTime = 0;
    }

    /**
     * Destroys wagon by changing it's state
     */
    public void destroy(){
        state = new Lost(this);
        destroyed = true;
    }

    /**
     * Checks if destruction animation is over
     * @return whether destruction animation is over
     */
    public boolean hasLost(){
        return (destroyed && frame == frames.size);
    }

    /**
     * Draws wagon in Sprite Batch
     * @param sb sprite batch
     */
    public void render(SpriteBatch sb){
        if(destroyed)
            sb.draw(frames.get(frame),position.x, position.y, width, height);
        else
            sb.draw(tex,position.x, position.y, width, height);
    }
}
