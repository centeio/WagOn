package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by inesf on 18/05/2016.
 */
public class Bomb extends FallingObj {
    public Bomb(int x) {
        super(x, new Texture("bomb.png"));
    }

    public Bomb(int x, Texture tex) {
        super(x, tex);
    }

    @Override
    public void detectCollision(Wagon wagon) {
        if(wagon.getBounds().overlaps(bounds)){
            wagon.jump(); //TODO change this -> kill wagon
        }
    }

    @Override
    public void detectCollision(Floor floor) {
        if (position.y + HEIGHT < 20) {
            floor.destroyTile(position.x);
            floor.destroyTile(position.x + FallingObj.WIDTH);
            reposition();
        }
    }
}
