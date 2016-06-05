package com.feup.lpoo.WagonStates;

import com.feup.lpoo.logic.Floor;
import com.feup.lpoo.logic.Wagon;

/**
 * Class representing state when wagon is falling
 * @author Carolina Centeio e Ines Proenca
 * @see WagonState
 */
public class Falling extends WagonState{

    /**
     * Constructor for Falling state.
     * <p>Sets wagon velocity to 0 and acceleration to gravity</p>
     * @param wagon wagon in this state
     */
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