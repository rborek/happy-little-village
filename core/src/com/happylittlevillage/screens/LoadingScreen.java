package com.happylittlevillage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.happylittlevillage.objects.GameObject;
import com.happylittlevillage.HappyLittleVillage;
import com.happylittlevillage.Assets;
import com.happylittlevillage.rituals.Ritual;

public class LoadingScreen extends ScreenAdapter {
	protected HappyLittleVillage happyLittleVillage;
	protected OrthographicCamera camera;
	protected Viewport viewport;
	protected SpriteBatch batch;
	protected Vector2 screenPos = new Vector2();
	protected int lastResHeight;
	protected int lastResWidth;
	private boolean isTutorial;
	private GameObject screen;

	public LoadingScreen(HappyLittleVillage happyLittleVillage, boolean isTutorial) {
		this.happyLittleVillage = happyLittleVillage;
		this.isTutorial = isTutorial;
		camera = new OrthographicCamera();
		camera.position.set(GameScreen.WIDTH / 2f, GameScreen.HEIGHT / 2f, 0);
		camera.update();
		viewport = new StretchViewport(GameScreen.WIDTH, GameScreen.HEIGHT, camera);
		viewport.apply();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		screen = new GameObject(Assets.getTexture("menu/loading_screen.png"), 0, 0);
		Assets.load();
	}

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		screen.render(batch);
		batch.end();
		if (Assets.update()) {
			Ritual.load();
			happyLittleVillage.setGameScreen(isTutorial);
		}
	}

	@Override
	public void resize(int width, int height) {
		//Took this from GameScreen
		System.out.println("calling resize");
		if (width != lastResWidth || height != lastResHeight) {
			System.out.println("resizing");
			viewport.update(width, height);
			lastResWidth = width;
			lastResHeight = height;
		}
	}

}
