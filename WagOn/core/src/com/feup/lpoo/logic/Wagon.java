package com.feup.lpoo.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.feup.lpoo.WagOn;

/**
 * Created by inesf on 14/05/2016.
 */
public class Wagon extends Piece {

    public Wagon() {
        super(10, 10, new Texture("wagon.png"), 75, 38);

        int x = WagOn.WIDTH/2 -width/2;
        int y = 30;

        this.position = new Vector2(x,y);

        bounds = new Rectangle(x,y, width, height);
    }

    public void update(float dt){
        velocity.add(acceleration);
        velocity.scl(dt);
        position.add(velocity);
        velocity.scl(1/dt);

        if(position.y < 30)
            position.y = 30;
        if(position.x < 0) {
            position.x = 0;
            velocity.x = 0;
        }
        if(position.x > WagOn.WIDTH - width) {
            position.x = WagOn.WIDTH - width;
            velocity.x = 0;
        }

        bounds.setPosition(position.x, position.y);
    }

    public void updateAccelerationX(float acc){
        if(acc == 0 || acc * acceleration.x < 0)
             velocity.x = 0;

            acceleration.x = acc;
    }
}
