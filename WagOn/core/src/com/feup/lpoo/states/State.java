package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

/**
 * Created by inesf on 11/05/2016.
 */
public abstract class State {
    public static final int font_size = 50;
    public static final int title_size = 150;
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    protected static BitmapFont font = null;
    protected static BitmapFont titleFont = null;
    protected Preferences prefs;
    protected static I18NBundle strings;

    private void initializeBundle(){
        FileHandle baseFileHandle = Gdx.files.internal("MyBundle");
        Locale locale = Locale.getDefault();
        strings = I18NBundle.createBundle(baseFileHandle,locale);
    }

    private void initializeFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("trench100free.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = font_size;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";

        font = generator.generateFont(parameter);

        parameter.size = title_size;

        titleFont = generator.generateFont(parameter);
        generator.dispose();
    }


    protected State(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();

        if(font == null || titleFont == null)
            initializeFonts();

        if(strings == null)
            initializeBundle();

    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

}
