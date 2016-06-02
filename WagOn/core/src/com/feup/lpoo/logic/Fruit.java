package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by inesf on 18/05/2016.
 */
public class Fruit extends FallingObj {
    protected static int HEIGHT = 20;

    public Fruit(int x) {
        super(x, "melon.png",HEIGHT);
    }

    @Override
    public boolean detectCollision(Wagon wagon) {
        if(wagon.getBounds().overlaps(bounds)){
            reposition();
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
