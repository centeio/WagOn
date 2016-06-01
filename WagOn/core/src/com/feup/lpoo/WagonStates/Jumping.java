package com.feup.lpoo.WagonStates;

import com.feup.lpoo.logic.Floor;
import com.feup.lpoo.logic.Wagon;

/**
 * Created by inesf on 18/05/2016.
 */
public class Jumping extends WagonState {
    private static final int GRAVITY = -10;
    private boolean jumpedAgain;

    public Jumping(Wagon wagon) {
        super(wagon);
        jumpedAgain = false;

        wagon.setVelocityY(200);
        wagon.setAccelerationY(GRAVITY);
    }

    @Override
    public void update(float acc) {
        if(wagon.getVelocity().y < 0 && wagon.getPosition().y <= Floor.GROUND_HEIGHT)
            wagon.setState(new Moving(wagon));
    }

    @Override
    public void jump() {
        if(!jumpedAgain) {
            wagon.setVelocityY(200);
            jumpedAgain = true;
        }
    }
}
