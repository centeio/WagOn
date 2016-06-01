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
    public boolean detectCollision(Wagon wagon) {
        if(wagon.getBounds().overlaps(bounds)){
            return true;
        }
        return false;
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
