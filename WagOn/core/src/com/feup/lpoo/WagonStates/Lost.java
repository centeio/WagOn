package com.feup.lpoo.WagonStates;

import com.feup.lpoo.logic.Wagon;

/**
 * Class representing state when wagon has been hit by a bomb
 * @author Carolina Centeio e Ines Proenca
 * @see WagonState
 */
public class Lost extends WagonState {
    /**
     * Constructor for Jumping state.
     * <p>Sets wagon velocity in x axis to 0</p>
     * @param wagon wagon in this state
     */
    public Lost(Wagon wagon) {
        super(wagon);
        wagon.setVelocityX(0);
    }

    @Override
    public void update(float acc) {

    }

    @Override
    public void jump() {

    }
}
