package com.feup.lpoo.WagonStates;

import com.feup.lpoo.logic.Floor;
import com.feup.lpoo.logic.Wagon;

/**
 * Created by Carolina on 01/06/2016.
 */
public class Falling extends WagonState{
    private static final int GRAVITY = -10;

    public Falling(Wagon wagon) {
        super(wagon);

        wagon.setVelocityY(0);
        wagon.setAccelerationX(0);
        wagon.setAccelerationY(GRAVITY);
        wagon.setVelocityX(0);
    }

    @Override
    public void update(float acc) {

    }

    @Override
    public void jump() {

    }
}