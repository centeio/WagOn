package com.feup.lpoo.WagonStates;

import com.feup.lpoo.logic.Wagon;

/**
 * Created by inesf on 18/05/2016.
 */
public class Moving extends WagonState {

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

    public void jump(){
        wagon.setState(new Jumping(wagon));
    }
}
