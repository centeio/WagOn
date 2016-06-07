package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Timer;
import com.feup.lpoo.WagOn;

import java.util.Locale;

import javafx.scene.paint.Color;

/**
 * Created by inesf on 06/06/2016.
 */
public class SplashState extends State {

    private static long SPLASH_MINIMUM_MILLIS = 3000L;
    final long splash_start_time;
    private State menuState;
    boolean ready;
    Texture bg;

    public SplashState(GameStateManager gsm1) {
        super(gsm1);
        bg = new Texture("bg.png");

        ready = false;

        splash_start_time = System.currentTimeMillis();

        Thread helper = new Thread(new Runnable() {
            @Override
            public void run() {
                cam = new OrthographicCamera();
                cam.setToOrtho(false, WagOn.WIDTH, WagOn.HEIGHT);
                menuState = new MenuState(gsm);
                ready = true;
            }
        });

        helper.start();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        if(ready && System.currentTimeMillis() - splash_start_time > SPLASH_MINIMUM_MILLIS)
            gsm.push(menuState);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bg,0,0,WagOn.WIDTH, WagOn.HEIGHT);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
    }
}
