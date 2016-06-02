package com.feup.lpoo.test;

import com.feup.lpoo.WagOn;
import com.feup.lpoo.WagonStates.*;
import com.feup.lpoo.logic.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class WagonActionTest{
    @Test
    public void testStatic(){
        Wagon wagon = new Wagon();

        assertEquals(0, wagon.getVelocity().x, 0.01);
        assertEquals(WagOn.WIDTH/2 - Wagon.WIDTH/2, wagon.getPosition().x, 0.01);
        wagon.update(100);
        assertEquals(0, wagon.getVelocity().x, 0.01);
        assertEquals(WagOn.WIDTH/2 - Wagon.WIDTH/2, wagon.getPosition().x, 0.01);

        assertTrue(wagon.getState() instanceof Moving);
    }

    @Test
    public void testMoving(){
        Wagon wagon = new Wagon();

        assertEquals(0, wagon.getVelocity().x, 0.01);
        assertEquals(WagOn.WIDTH/2 - Wagon.WIDTH/2, wagon.getPosition().x,0.01);
        wagon.updateOnAccelerometer(2);
        wagon.update(10);
        assertEquals(4, wagon.getAcceleration().x, 0.1);;
        assertEquals(4, wagon.getVelocity().x, 0.01);
        assertEquals(WagOn.WIDTH/2 - Wagon.WIDTH/2 + 40, wagon.getPosition().x, 0.01);

        assertTrue(wagon.getState() instanceof Moving);
    }

    @Test
    public void testJumping(){
        Wagon wagon = new Wagon();

        assertTrue(wagon.getState() instanceof Moving);

        assertEquals(0, wagon.getVelocity().x, 0.01);
        assertEquals(WagOn.WIDTH/2 - Wagon.WIDTH/2, wagon.getPosition().x,0.01);
        wagon.jump();
        assertTrue(wagon.getState() instanceof Jumping);
        assertTrue(wagon.getVelocity().y > 0);
        assertTrue(wagon.getAcceleration().y < 0);
        wagon.update(10);
        assertTrue(wagon.getPosition().y > Floor.GROUND_HEIGHT);
    }

}
