package com.feup.lpoo.WagonStates;

import com.feup.lpoo.logic.Wagon;

/**
 * Created by inesf on 18/05/2016.
 */
public class Jumping extends WagonState {
    private static final int GRAVITY = -10;
    public Jumping(Wagon wagon) {
        super(wagon);
        wagon.setVelocityY(200);
        wagon.setAccelerationY(GRAVITY);
    }

    @Override
    public void update(float acc) {
        if(wagon.getPosition().y <= Wagon.GROUND_HEIGHT)
            wagon.setState(new Moving(wagon));
    }

    @Override
    public void jump() {
    }
}
