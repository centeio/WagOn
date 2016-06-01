package com.feup.lpoo.test;

import com.feup.lpoo.WagonStates.Falling;
import com.feup.lpoo.logic.Bomb;
import com.feup.lpoo.logic.Floor;
import com.feup.lpoo.logic.Fruit;
import com.feup.lpoo.logic.Wagon;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by inesf on 01/06/2016.
 */
public class CollisionTest {
    @Test
    public void testExplodeFloor(){

        Floor floor = new Floor(1);
        Bomb bomb = new Bomb(10);

        while(bomb.getPosition().y > Floor.GROUND_HEIGHT){
            bomb.update(10);
            bomb.detectCollision(floor);
        }

        assertEquals(true,  floor.getTiles().first().isDestroyed());
    }

    @Test
    public void testFallingCar(){
        Floor floor = new Floor(1);
        Wagon wagon = new Wagon();

        floor.destroyTile(10);
        wagon.detectFall(floor);

        assertTrue(wagon.getState() instanceof Falling);
    }

    @Test
    public void testGainPoints(){
        Wagon wagon = new Wagon();
        Fruit fruit = new Fruit((int)(wagon.getPosition().x));

        while(!fruit.detectCollision(wagon)){
            fruit.update(10);
        }
        assertEquals(1, wagon.getScore());
    }
}