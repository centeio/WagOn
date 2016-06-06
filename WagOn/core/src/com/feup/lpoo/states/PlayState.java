package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
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
public class PlayState  extends State{
    private Texture sky;
    private Floor floor;
    private Wagon wagon;
    private Fruit fruit;
    private Bomb bomb;
    private Sound jumpSound;
    private Sound bombSound;
    private Sound caughtSound;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        sky = new Texture("sky.png");
        floor = new Floor(31);
        wagon = new Wagon();
        fruit = new Fruit(MathUtils.random(0, WagOn.WIDTH - FallingObj.WIDTH));
        bomb = new Bomb();
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        bombSound = Gdx.audio.newSound(Gdx.files.internal("bomb.wav"));
        caughtSound = Gdx.audio.newSound(Gdx.files.internal("bump.wav"));


    }

    @Override
    protected void handleInput() {
        if(!(wagon.getState() instanceof Falling)) {
            if (Gdx.input.justTouched()){
                jumpSound.play();
                wagon.jump();
            }
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

        if(fruit.detectCollision(wagon)){
            caughtSound.play();
        }
        fruit.detectCollision(floor);

        if(bomb.detectCollision(wagon)) {
            bombSound.play();
        }

        bomb.detectCollision(floor);

        wagon.detectFall(floor);

        if(wagon.getPosition().y <= 0)
            gsm.set(new LostState(gsm, wagon.getScore()));

        if(wagon.hasLost())
            gsm.set(new LostState(gsm, wagon.getScore()));


//        System.out.println(wagon.getScore());
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(sky, cam.position.x - (cam.viewportWidth  / 2), 0, WagOn.WIDTH, WagOn.HEIGHT);
        sb.draw(fruit.getTex(), fruit.getPosition().x, fruit.getPosition().y, fruit.getWidth(), fruit.getHeight());
        sb.draw(bomb.getTex(), bomb.getPosition().x, bomb.getPosition().y, bomb.getWidth(), bomb.getHeight());
        wagon.render(sb);
        floor.render(sb);

        String strScore = ((Integer)wagon.getScore()).toString();
        font.draw(sb,strScore,WagOn.WIDTH - strScore.length()*State.font_size, WagOn.HEIGHT - 10);
        sb.end();
    }

    public void reset(){
        bomb.reset();
        fruit.reset();
        wagon.reset();
        floor.reset();
    }

    @Override
    public void dispose() {
    }
}
