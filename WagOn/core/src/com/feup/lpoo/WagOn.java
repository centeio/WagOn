package com.feup.lpoo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.feup.lpoo.states.*;

public class WagOn extends ApplicationAdapter {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 571;

	public static boolean isMobile;

	private GameStateManager gsm;
	private SpriteBatch batch;

	public WagOn(boolean isMobile) {
		this.isMobile = isMobile;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
		//gsm.push(new MultiPlayerState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void pause() {
		super.pause();
		gsm.push(new PauseState(gsm));
	}
}