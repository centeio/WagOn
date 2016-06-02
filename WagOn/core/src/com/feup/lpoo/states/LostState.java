package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feup.lpoo.WagOn;

/**
 * Created by inesf on 01/06/2016.
 */
public class LostState extends State{
    private Texture background;
    int score;
    private Sound gameoverSound;

    public LostState(GameStateManager gsm, int score) {
        super(gsm);
        gameoverSound = Gdx.audio.newSound(Gdx.files.internal("gameover.wav"));
        gameoverSound.play();
        cam.setToOrtho(false, WagOn.WIDTH, WagOn.HEIGHT);
        background = new Texture("game_over.png");
        this.score = score;


    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            gsm.set(new MenuState(gsm));
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0 , 0, cam.viewportWidth, cam.viewportHeight);
        font.setColor(Color.WHITE);
        font.draw(sb,((Integer)score).toString(),WagOn.WIDTH/2, 5*WagOn.HEIGHT/6);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
