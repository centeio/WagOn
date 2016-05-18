package com.feup.lpoo.WagonStates;

import com.feup.lpoo.logic.Wagon;

/**
 * Created by inesf on 18/05/2016.
 */
public abstract class WagonState {
    protected Wagon wagon;

    public WagonState(Wagon wagon) {
        this.wagon = wagon;
    }

    public abstract void update(float acc);
    public abstract void jump();
}
