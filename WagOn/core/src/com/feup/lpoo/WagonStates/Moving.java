package com.feup.lpoo.WagonStates;

import com.feup.lpoo.logic.Wagon;

/**
 * Class representing state when wagon is moving
 * @author Carolina Centeio e Ines Proenca
 * @see WagonState
 */
public class Moving extends WagonState {

    /**
     * Constructor for Moving state.
     * Sets wagon's acceleration in y axis to 0
     * @param wagon wagon in this state
     */
    public Moving(Wagon wagon) {
        super(wagon);
        wagon.setAccelerationY(0);
    }

    @Override
    public void update(float acc) {
        if(acc == 0 || acc * wagon.getAcceleration().x < 0)
            wagon.stop();

        wagon.setAccelerationX(acc);
    }

    @Override
    public void jump(){
        wagon.setState(new Jumping(wagon));
    }
}
