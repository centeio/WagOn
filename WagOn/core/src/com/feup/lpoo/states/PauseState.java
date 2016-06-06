package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feup.lpoo.WagOn;

/**
 * Created by inesf on 31/05/2016.
 */
public class PauseState extends State {

    private Texture background;

    public PauseState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, WagOn.WIDTH, WagOn.HEIGHT);
        background = new Texture("sky.png");
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            gsm.pop();
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

        GlyphLayout layout = new GlyphLayout(titleFont, strings.get("game"));

        titleFont.draw(sb, layout, (WagOn.WIDTH - layout.width) / 2, 3*WagOn.HEIGHT/ 4);

        layout = new GlyphLayout(font, strings.get("pause"));

        font.draw(sb, layout, (WagOn.WIDTH - layout.width) / 2, WagOn.HEIGHT/ 2);

        layout = new GlyphLayout(font, strings.get("touch"));

        font.draw(sb,layout,(WagOn.WIDTH - layout.width) / 2,WagOn.HEIGHT/4);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
