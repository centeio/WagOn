package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.feup.lpoo.WagOn;
import com.feup.lpoo.WagonStates.Falling;
import com.feup.lpoo.logic.*;

/**
 * Created by inesf on 11/05/2016.
 */
public class PlayState  extends State{
    private Texture sky;
    private Floor floor;
    private Wagon wagon;
    private Fruit fruit;
    private Bomb bomb;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        sky = new Texture("sky.png");
        floor = new Floor(31);
        wagon = new Wagon();
        fruit = new Fruit(MathUtils.random(0, WagOn.WIDTH - FallingObj.WIDTH));
        bomb = new Bomb(MathUtils.random(0, WagOn.WIDTH - FallingObj.WIDTH));
    }

    @Override
    protected void handleInput() {
        if(!(wagon.getState() instanceof Falling)) {
            if (Gdx.input.justTouched())
                wagon.jump();
            if (WagOn.isMobile) {
                float acc = Gdx.input.getAccelerometerY();

                wagon.updateOnAccelerometer(2*acc);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
                wagon.updateOnAccelerometer(5);
            else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
                wagon.updateOnAccelerometer(-5);
            else
                wagon.updateOnAccelerometer(0);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        wagon.update(dt);
        fruit.update(dt);
        bomb.update(dt);

        if(fruit.detectCollision(wagon))
            wagon.incScore();
        fruit.detectCollision(floor);

        if(bomb.detectCollision(wagon))
            gsm.set(new LostState(gsm, wagon.getScore()));
        bomb.detectCollision(floor);

        wagon.detectFall(floor);

        if(wagon.getPosition().y <= 0)
            gsm.set(new LostState(gsm, wagon.getScore()));

        System.out.println(wagon.getScore());
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(sky, cam.position.x - (cam.viewportWidth  / 2), 0, WagOn.WIDTH, WagOn.HEIGHT);
        sb.draw(fruit.getTex(), fruit.getPosition().x, fruit.getPosition().y, fruit.getWidth(), fruit.getHeight());
        sb.draw(bomb.getTex(), bomb.getPosition().x, bomb.getPosition().y, bomb.getWidth(), bomb.getHeight());
        sb.draw(wagon.getTex(), wagon.getPosition().x, wagon.getPosition().y, wagon.getWidth(), wagon.getHeight());
        floor.render(sb);

        String strScore = ((Integer)wagon.getScore()).toString();
        font.draw(sb,strScore,WagOn.WIDTH - strScore.length()*State.font_size, WagOn.HEIGHT - 10);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
