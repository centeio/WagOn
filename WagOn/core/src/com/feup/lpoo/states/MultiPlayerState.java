package com.feup.lpoo.states;

/**
 * Created by inesf on 04/06/2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.feup.lpoo.WagOn;
import com.feup.lpoo.WagonStates.Falling;
import com.feup.lpoo.logic.Bomb;
import com.feup.lpoo.logic.FallingObj;
import com.feup.lpoo.logic.Floor;
import com.feup.lpoo.logic.Fruit;
import com.feup.lpoo.logic.Wagon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
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
public class MultiPlayerState  extends State{
    private Texture sky;
    private Floor floor;
    private Wagon wagon1;
    private Wagon wagon2;
    private Fruit fruit;
    private Bomb bomb;
    private Sound jumpSound;
    private Sound bombSound;
    private Sound caughtSound;

    public MultiPlayerState(GameStateManager gsm) {
        super(gsm);
        sky = new Texture("sky.png");
        floor = new Floor(31);
        wagon1 = new Wagon("wagon.png");
        wagon2 = new Wagon("wagon2.png");
        fruit = new Fruit(MathUtils.random(0, WagOn.WIDTH - FallingObj.WIDTH));
        bomb = new Bomb(MathUtils.random(0, WagOn.WIDTH - FallingObj.WIDTH));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        bombSound = Gdx.audio.newSound(Gdx.files.internal("bomb.wav"));
        caughtSound = Gdx.audio.newSound(Gdx.files.internal("bump.wav"));


    }

    @Override
    protected void handleInput() {
        if(!(wagon1.getState() instanceof Falling)) {
            if (Gdx.input.justTouched()){
                jumpSound.play();
                wagon1.jump();
            }
            if (WagOn.isMobile) {
                float acc = Gdx.input.getAccelerometerY();

                wagon1.updateOnAccelerometer(2*acc);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
                wagon1.updateOnAccelerometer(5);
            else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
                wagon1.updateOnAccelerometer(-5);
            else
                wagon1.updateOnAccelerometer(0);
        }

        if(!(wagon2.getState() instanceof Falling)) {
            if (Gdx.input.justTouched()){
                jumpSound.play();
                wagon2.jump();
            }
            if (WagOn.isMobile) {
                float acc = Gdx.input.getAccelerometerY();

                wagon2.updateOnAccelerometer(2*acc);
            } else if (Gdx.input.isKeyPressed(Input.Keys.S))
                wagon2.updateOnAccelerometer(5);
            else if (Gdx.input.isKeyPressed(Input.Keys.A))
                wagon2.updateOnAccelerometer(-5);
            else
                wagon2.updateOnAccelerometer(0);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        wagon1.update(dt);
        wagon2.update(dt);
        fruit.update(dt);
        bomb.update(dt);

        if(fruit.detectCollision(wagon1)){
            caughtSound.play();
        }
        if(fruit.detectCollision(wagon2)){
            caughtSound.play();
        }
        fruit.detectCollision(floor);

        if(bomb.detectCollision(wagon1)) {
            bombSound.play();
            gsm.set(new LostState(gsm, wagon1.getScore()));
        }
        if(bomb.detectCollision(wagon2)) {
            bombSound.play();
            gsm.set(new LostState(gsm, wagon2.getScore()));
        }
        bomb.detectCollision(floor);

        wagon1.detectFall(floor);
        wagon2.detectFall(floor);

        if(wagon1.getPosition().y <= 0)
            gsm.set(new LostState(gsm, wagon2.getScore()));
        if(wagon1.getPosition().y <= 0)
            gsm.set(new LostState(gsm, wagon2.getScore()));
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(sky, cam.position.x - (cam.viewportWidth  / 2), 0, WagOn.WIDTH, WagOn.HEIGHT);
        sb.draw(fruit.getTex(), fruit.getPosition().x, fruit.getPosition().y, fruit.getWidth(), fruit.getHeight());
        sb.draw(bomb.getTex(), bomb.getPosition().x, bomb.getPosition().y, bomb.getWidth(), bomb.getHeight());
        sb.draw(wagon1.getTex(), wagon1.getPosition().x, wagon1.getPosition().y, wagon1.getWidth(), wagon1.getHeight());
        sb.draw(wagon2.getTex(), wagon2.getPosition().x, wagon2.getPosition().y, wagon2.getWidth(), wagon2.getHeight());
        floor.render(sb);

        String strScore = ((Integer)wagon1.getScore()).toString();
        font.draw(sb,strScore,WagOn.WIDTH - strScore.length()*State.font_size, WagOn.HEIGHT - 10);

        strScore = ((Integer)wagon2.getScore()).toString();
        font.draw(sb,strScore,10, WagOn.HEIGHT - 10);

        sb.end();
    }

    @Override
    public void dispose() {
        fruit.getTex().dispose();
        bomb.getTex().dispose();
        wagon1.getTex().dispose();
        wagon2.getTex().dispose();
        sky.dispose();
    }
}

