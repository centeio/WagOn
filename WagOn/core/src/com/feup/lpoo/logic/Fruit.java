package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

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

    @Override
    protected void updateStartTime() {
        startTime = TimeUtils.millis();
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
    public void detectCollision(Floor floor) {
        if (position.y + HEIGHT < 0) {
            reposition();
        }
    }
}
