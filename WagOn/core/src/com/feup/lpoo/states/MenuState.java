package com.feup.lpoo.states;

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

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, WagOn.WIDTH, WagOn.HEIGHT);
        background = new Texture("bg.png");

        Texture playBtn = new Texture("playbtn.png");
        playButton = new GameButton(playBtn, WagOn.WIDTH/2, WagOn.HEIGHT/4, cam);
    }

    @Override
    public void handleInput() {

        if(playButton.isClicked()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        playButton.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0 , 0, cam.viewportWidth, cam.viewportHeight);
        playButton.render(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}
