package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feup.lpoo.WagOn;

/**
 * Created by inesf on 11/05/2016.
 */
public class MenuState extends State {

    private static final int PLAY_HEIGHT = 20;

    private Texture background;

    private GameButton playButton;

    private GameButton playMPButton;

    private GameButton finishButton;

    private static PlayState playState = null;

    private static MultiPlayerState multiPlayerState = null;

    private Sound clickSound;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, WagOn.WIDTH, WagOn.HEIGHT);
        background = new Texture("bg.png");

        Texture playBtn = new Texture("playbtn.png");
        Texture playMPBtn = new Texture("multi.png");
        Texture stopBtn = new Texture("endbtn.png");

        playButton = new GameButton(playBtn, WagOn.WIDTH/4, WagOn.HEIGHT/4, cam);
        playMPButton = new GameButton(playMPBtn, 2*WagOn.WIDTH/4, WagOn.HEIGHT/4, cam);
        finishButton = new GameButton(stopBtn, 3*WagOn.WIDTH/4, WagOn.HEIGHT/4, cam);

        if(playState == null) {
            playState = new PlayState(gsm);
        }
        else{
            playState.reset();
        }


       /* else{
            multiPlayerState.reset();
        }*/

        clickSound = Gdx.audio.newSound(Gdx.files.internal("mouse.wav"));

    }

    @Override
    public void handleInput() {

        if(playButton.isClicked()){
            clickSound.play();
            gsm.set(playState);
        }

        if(playMPButton.isClicked()){
            clickSound.play();
            if(multiPlayerState == null) {
                if(WagOn.isMobile)
                    multiPlayerState = new MultiPlayerState(gsm, false);
                else
                    multiPlayerState = new MultiPlayerState(gsm, true);

            }
            gsm.set(multiPlayerState);
        }

        if(finishButton.isClicked()){
            Gdx.app.exit();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        playButton.update(dt);
        playMPButton.update(dt);
        finishButton.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0 , 0, cam.viewportWidth, cam.viewportHeight);
        playButton.render(sb);
        playMPButton.render(sb);
        finishButton.render(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        playMPButton.dispose();
    }
}
