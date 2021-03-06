package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

import java.net.InetAddress;
import java.net.UnknownHostException;
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

    private Sound clickSound;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("sky.png");

        //cam.setToOrtho(false, WagOn.WIDTH, WagOn.HEIGHT);

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

            if(WagOn.isMobile)

                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String text) {
                        gsm.set(new MultiPlayerState(gsm, false, text));
                    }
                    @Override
                    public void canceled() {

                    }
                }, "Choose Server", "172.30.4.107", "IP Address");
            else{
                gsm.set(new MultiPlayerState(gsm, true, ""));
            }

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

        sb.setProjectionMatrix(cam.combined);

        sb.begin();
        sb.draw(background, 0 , 0, cam.viewportWidth, cam.viewportHeight);

        for(Fruit fruit: fruits)
            sb.draw(fruit.getTex(), fruit.getPosition().x, fruit.getPosition().y, fruit.getWidth(), fruit.getHeight());

        GlyphLayout layout = new GlyphLayout(titleFont, strings.get("game"));

        titleFont.draw(sb, layout, (WagOn.WIDTH - layout.width) / 2, 3*WagOn.HEIGHT/ 4);

        if(!WagOn.isMobile){
            try {
                GlyphLayout layout2 = new GlyphLayout(font, InetAddress.getLocalHost().getHostAddress());
                font.draw(sb, layout2, (WagOn.WIDTH - layout.width) / 2, 5*WagOn.HEIGHT/ 6);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

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
