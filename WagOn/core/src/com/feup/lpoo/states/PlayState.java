package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feup.lpoo.WagOn;
import com.feup.lpoo.logic.Floor;
import com.feup.lpoo.logic.Wagon;

/**
 * Created by inesf on 11/05/2016.
 */
public class PlayState  extends State{
    private Texture sky;
    private Floor floor;
    private Wagon wagon;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        sky = new Texture("sky.png");
        floor = new Floor(20);
        wagon = new Wagon();
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            wagon.jump();
        if(WagOn.isMobile){
            float acc = Gdx.input.getAccelerometerY();

            wagon.updateOnAccelerometer(acc);
        }
        else
            if(Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
                wagon.updateOnAccelerometer(5);
            else if(Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
                wagon.updateOnAccelerometer(-5);
            else
                wagon.updateOnAccelerometer(0);
    }

    @Override
    public void update(float dt) {
        handleInput();
        wagon.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(sky, cam.position.x - (cam.viewportWidth  / 2), 0, WagOn.WIDTH, WagOn.HEIGHT);
        sb.draw(wagon.getTex(), wagon.getPosition().x, wagon.getPosition().y, wagon.getWidth(), wagon.getHeight());
        floor.render(sb);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
