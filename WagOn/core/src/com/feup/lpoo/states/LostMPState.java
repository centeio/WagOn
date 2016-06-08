package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feup.lpoo.WagOn;

/**
 * Created by Carolina on 07/06/2016.
 */
public class LostMPState extends State {
    private Texture background;
    int score;
    int id;
    boolean isServer;
    private Sound gameoverSound;
    int winner;

    public LostMPState(GameStateManager gsm, int id, int winner, int score, boolean isServer){
        super(gsm);gameoverSound = Gdx.audio.newSound(Gdx.files.internal("gameover.wav"));
        gameoverSound.play();
        cam.setToOrtho(false, WagOn.WIDTH, WagOn.HEIGHT);
        background = new Texture("sky.png");
        this.score = score;
        this.id = id;
        this.isServer = isServer;
        this.winner = winner;

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
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0 , 0, cam.viewportWidth, cam.viewportHeight);

        if(id==winner){
            titleFont.draw(sb, "YOU WON", 0, WagOn.HEIGHT/2);
            font.draw(sb, ((Integer)score).toString(), WagOn.WIDTH/2, 4*WagOn.HEIGHT/5);

        }
        else if(isServer){
            font.draw(sb, "Player "+ winner +" won", 0, WagOn.HEIGHT/2);
            font.draw(sb, ((Integer)score).toString(), WagOn.WIDTH/2, 4*WagOn.HEIGHT/5);

        } else{
            titleFont.draw(sb, "YOU LOST", 0, WagOn.HEIGHT/2);
            font.draw(sb, ((Integer)score).toString(), WagOn.WIDTH/2, 4*WagOn.HEIGHT/5);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();

    }
}
