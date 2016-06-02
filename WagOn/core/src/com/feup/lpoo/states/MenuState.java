package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feup.lpoo.logic.GameButton;
import com.feup.lpoo.WagOn;

/**
 * Created by inesf on 11/05/2016.
 */
public class MenuState extends State {

    private static final int PLAY_HEIGHT = 20;

    private Texture background;

    private GameButton playButton;

    private GameButton finishButton;

    private State playState;

    private Sound clickSound;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, WagOn.WIDTH, WagOn.HEIGHT);
        background = new Texture("bg.png");

        Texture playBtn = new Texture("playbtn.png");
        Texture stopBtn = new Texture("endbtn.png");
        playButton = new GameButton(playBtn, WagOn.WIDTH/3, WagOn.HEIGHT/4, cam);
        finishButton = new GameButton(stopBtn, 2*WagOn.WIDTH/3, WagOn.HEIGHT/4, cam);
        playState = new PlayState(gsm);
        clickSound = Gdx.audio.newSound(Gdx.files.internal("mouse.wav"));
    }

    @Override
    public void handleInput() {

        if(playButton.isClicked()){
            clickSound.play();
            gsm.set(playState);
        }

        if(finishButton.isClicked()){
            Gdx.app.exit();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        playButton.update(dt);
        finishButton.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0 , 0, cam.viewportWidth, cam.viewportHeight);
        playButton.render(sb);
        finishButton.render(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}
