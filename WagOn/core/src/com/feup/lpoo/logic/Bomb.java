package com.feup.lpoo.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.feup.lpoo.WagOn;

/**
 * Created by inesf on 18/05/2016.
 */
public class Bomb extends FallingObj {
    protected static int HEIGHT = 30;
    public Bomb(int x) {
        super(x, "bomb.png", HEIGHT);
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
        if (position.y + HEIGHT < Floor.GROUND_HEIGHT) {
            floor.destroyTile(position.x);
            floor.destroyTile(position.x + FallingObj.WIDTH);
        if(position.y + HEIGHT <0)
            reposition();
        }
    }

    public void startFall(){
        startTime = TimeUtils.millis();
        position.x = WagOn.WIDTH/2;
    }
}
