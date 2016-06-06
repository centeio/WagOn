package com.feup.lpoo.logic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.feup.lpoo.WagOn;

/**
 * Class representing a falling bomb
 * @author Carolina Centeio e Ines Proenca
 */
public class Bomb extends FallingObj {
    protected static int HEIGHT = 30;

    /**
     * Default bomb constructor
     */
    public Bomb() {
        super(0, "bomb.png", HEIGHT);

        if(startTime == 0)
            reposition();
        else
            startTime = TimeUtils.millis();
    }

    @Override
    protected void updateStartTime() {
        int r = MathUtils.random(0,3) + 4;
        startTime = TimeUtils.millis() + r * 2000;
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

    /**
     * Makes bomb fall imidiatly in the middle of the screen.
     * <p>(method implemented mostly for test purposes)</p>
     */
    public void startFall(){
        startTime = TimeUtils.millis();
        position.x = WagOn.WIDTH/2;
    }
}
