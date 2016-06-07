package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.feup.lpoo.WagOn;
import com.feup.lpoo.logic.FallingObj;
import com.feup.lpoo.logic.Fruit;

import java.util.Random;

/**
 * Created by inesf on 11/05/2016.
 */
public class MenuState extends State {

    private Texture background;

    private GameButton playButton;

    private GameButton playMPButton;

    private GameButton finishButton;

    private Array<Fruit> fruits;

    private static PlayState playState = null;

    private static MultiPlayerState multiPlayerState = null;

    private Sound clickSound;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("sky.png");

        fruits = new Array<Fruit>();

        for(int i = 0; i < 5; i++)
            fruits.add(new Fruit());

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

        /*if(multiPlayerState == null) {
            multiPlayerState = new MultiPlayerState(gsm, false);
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

        for(Fruit fruit: fruits)
            fruit.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
        sb.draw(background, 0 , 0, WagOn.WIDTH, WagOn.HEIGHT);

        for(Fruit fruit: fruits)
            sb.draw(fruit.getTex(), fruit.getPosition().x, fruit.getPosition().y, fruit.getWidth(), fruit.getHeight());

        GlyphLayout layout = new GlyphLayout(titleFont, strings.get("game"));

        titleFont.draw(sb, layout, (WagOn.WIDTH - layout.width) / 2, 3*WagOn.HEIGHT/ 4);

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
