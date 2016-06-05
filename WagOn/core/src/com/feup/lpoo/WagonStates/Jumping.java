package com.feup.lpoo.WagonStates;

import com.feup.lpoo.logic.Floor;
import com.feup.lpoo.logic.Wagon;

/**
 * Class representing state when wagon is jumping
 * @author Carolina Centeio e Ines Proenca
 * @see WagonState
 */
public class Jumping extends WagonState {
    private boolean jumpedAgain; /**Restricts maximum of consecutive jumps to two*/

    /**
     * Constructor for Jumping state.
     * Sets wagon velocity in y axis to 200 and acceleration to gravity
     * @param wagon wagon in this state
     */
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
