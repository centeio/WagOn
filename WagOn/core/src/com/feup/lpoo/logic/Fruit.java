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
    protected static int HEIGHT = 20; /**Fruit's texture height*/

    /**
     * Constructor for class Fruit
     * @param x starting x coordinate
     */
    public Fruit(int x) {
        super(x, "melon.png",HEIGHT);

        startTime = TimeUtils.millis();
    }

    /**
     * Constructor for class Fruit
     */
    public Fruit() {
        super(0, "melon.png",HEIGHT);

        reposition();
    }

    @Override
    protected void updateStartTime() {
        int r = MathUtils.random(0, 4);
        startTime = TimeUtils.millis() + r*250;
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
}
