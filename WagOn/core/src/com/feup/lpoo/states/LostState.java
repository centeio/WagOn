package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
        background = new Texture("sky.png");
        this.score = score;
        prefs = Gdx.app.getPreferences("My Preferences");

        System.out.println("Lost state init ");
        System.out.println("Lost " + prefs.getInteger("score",0));

        if(score > prefs.getInteger("score", 0)) {
            prefs.putInteger("score", score);
            System.out.println("after actual "+ prefs.getInteger("score",0));
            prefs.flush();
        }



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
        font.setColor(Color.WHITE);

        GlyphLayout layout = new GlyphLayout(titleFont, strings.get("gameOver"));

        titleFont.draw(sb, layout, (WagOn.WIDTH - layout.width) / 2, 3*WagOn.HEIGHT/ 5);

        layout = new GlyphLayout(font, strings.get("touch"));

        font.draw(sb,layout,(WagOn.WIDTH - layout.width) / 2,WagOn.HEIGHT/4);

        layout = new GlyphLayout(font, strings.format("score", score));

        font.draw(sb,layout,20, 5*WagOn.HEIGHT/6);

        layout = new GlyphLayout(font, strings.format("best", prefs.getInteger("score",0)));
        font.draw(sb,layout,20, 5*WagOn.HEIGHT/6 - (font_size +10));
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
