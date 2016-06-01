package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by inesf on 18/05/2016.
 */
public class Fruit extends FallingObj {

    public Fruit(int x) {
        super(x, new Texture("melon.png"));
    }

    @Override
    public void detectCollision(Wagon wagon) {
        if(wagon.getBounds().overlaps(bounds)){

            reposition();
        }
    }

    @Override
    public void detectCollision(Floor floor) {
        if (position.y + HEIGHT < 20) {
            reposition();
        }
    }
}
