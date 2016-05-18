package com.feup.lpoo.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by inesf on 14/05/2016.
 */
public class GameButton {

    // center at x, y
    private float x;
    private float y;
    private float width;
    private float height;

    private Texture tex;

    Vector3 vec;
    private OrthographicCamera cam;

    private boolean clicked;

    public GameButton(Texture tex, float x, float y, OrthographicCamera cam) {

        this.tex = tex;
        this.x = x;
        this.y = y;
        this.cam = cam;

        width = tex.getWidth();
        height = tex.getHeight();
        vec = new Vector3();

    }

    public boolean isClicked() { return clicked; }

    public void update(float dt) {

        vec.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(vec);

        if(Gdx.input.justTouched() &&
                vec.x > x - width / 2 && vec.x < x + width / 2 &&
                vec.y > y - height / 2 && vec.y < y + height / 2) {
            clicked = true;
        }
        else {
            clicked = false;
        }

    }

    public void render(SpriteBatch sb) {

        sb.draw(tex, x - width / 2, y - height / 2);

    }

    public void dispose(){
        tex.dispose();
    }

}

