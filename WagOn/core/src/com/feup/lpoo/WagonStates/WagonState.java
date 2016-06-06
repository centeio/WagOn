package com.feup.lpoo.WagonStates;

import com.badlogic.gdx.utils.Json;
import com.feup.lpoo.logic.Wagon;

import java.io.Serializable;

/**
 * Class generalizing wagon's state
 * @author Carolina Centeio e Ines Proenca
 */
public abstract class WagonState implements Serializable {
    protected static final int GRAVITY = -10;   /**Gravity constant*/
    protected Wagon wagon;                      /**Wagon who this state belong to*/

    /**
     * Constructor for Wagon state.
     * @param wagon wagon to assign state
     */
    public WagonState(Wagon wagon) {
        this.wagon = wagon;
    }

    /**
     * Wagon reaction to accelerometer based on the state it's in
     * @param acc value return by the accelerometer
     */
    public abstract void update(float acc);

    /**
     * Wagon reaction to jump command based on the state it's in
     */
    public abstract void jump();
}
