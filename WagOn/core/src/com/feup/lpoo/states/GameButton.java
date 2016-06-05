package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Class representing a button
 * @author Carolina Centeio e Ines Proenca
 */
public class GameButton {

    private float x;                /**center x coordinate*/
    private float y;                /**center y coordinate*/
    private float width;            /**button's width*/
    private float height;           /**button's heigth*/

    private Texture tex;            /**button's image*/

    Vector3 lastTouch;              /**Last touched position*/
    private OrthographicCamera cam; /**Camera*/

    private boolean clicked;        /**boolean indicating wether or not button has been clicked*/

    /**
     * GameButton constructor
     * @param tex image
     * @param x center x coordinate
     * @param y center y coordinate
     * @param cam camera
     */
    public GameButton(Texture tex, float x, float y, OrthographicCamera cam) {

        this.tex = tex;
        this.x = x;
        this.y = y;
        this.cam = cam;

        width = tex.getWidth();
        height = tex.getHeight();
        lastTouch = new Vector3();

    }

    /**
     * Get button's clicked status
     * @return whether or not button has been clicked
     */
    public boolean isClicked() { return clicked; }

    /**
     * Updates button according to user actions
     * @param dt time passed since last update
     */
    public void update(float dt) {

        lastTouch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(lastTouch);

        if(Gdx.input.justTouched() &&
                lastTouch.x > x - width / 2 && lastTouch.x < x + width / 2 &&
                lastTouch.y > y - height / 2 && lastTouch.y < y + height / 2) {
            clicked = true;
        }
        else {
            clicked = false;
        }

    }

    /**
     * draw button in sprite batch
     * @param sb sprite bactch
     */
    public void render(SpriteBatch sb) {

        sb.draw(tex, x - width / 2, y - height / 2);

    }

    /**
     * Disposes of button image
     */
    public void dispose(){
        tex.dispose();
    }

}

