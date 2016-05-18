package com.feup.lpoo.logic;

/**
 * Created by inesf on 18/05/2016.
 */
public abstract class WagonState {
    Wagon wagon;

    public WagonState(Wagon wagon) {
        this.wagon = wagon;
    }

    public abstract void update(float acc);
}
